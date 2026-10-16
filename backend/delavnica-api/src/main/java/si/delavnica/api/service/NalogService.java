package si.delavnica.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.DelovniNalog;
import si.delavnica.api.model.DelovniNalog.Stanje;
import si.delavnica.api.model.PostavkaNaloga;
import si.delavnica.api.model.Racun;
import si.delavnica.api.model.Uporabnik.Vloga;
import si.delavnica.api.repository.DelovniNalogRepository;
import si.delavnica.api.repository.PostavkaNalogaRepository;
import si.delavnica.api.repository.RacunRepository;
import si.delavnica.api.repository.RezervniDelRepository;
import si.delavnica.api.repository.UporabnikRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NalogService {

	private static final BigDecimal URNA_POSTAVKA = new BigDecimal("50");

	private final DelovniNalogRepository nalogi;
	private final RezervniDelRepository deli;
	private final PostavkaNalogaRepository postavke;
	private final RacunRepository racuni;
	private final UporabnikRepository uporabniki;
	private final NalogDostop dostop;
	private final PopustService popusti;

	public List<DelovniNalog> getAll(Jwt jwt) {
		return dostop.vidni(jwt);
	}

	public DelovniNalog getById(UUID id, Jwt jwt) {
		return dostop.poisci(id, jwt);
	}

	public List<PostavkaNaloga> postavkeNaloga(UUID id, Jwt jwt) {
		dostop.poisci(id, jwt); // 404, če nalog ni vidan
		return postavke.findByNalogId(id);
	}

	// dodeliti ga je mogoče samo uporabniku z vlogo mehanik
	public DelovniNalog ustvari(DelovniNalog nalog) {
		var mehanik = uporabniki.findById(nalog.getMehanik().getId())
				.filter(u -> u.getVloga() == Vloga.MEHANIK)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nalog je mogoče dodeliti samo mehaniku"));
		nalog.setMehanik(mehanik);
		nalog.setId(null);
		nalog.setStanje(Stanje.ODPRT);
		return nalogi.save(nalog);
	}

	public DelovniNalog posodobi(UUID id, DelovniNalog novi, Jwt jwt) {
		var nalog = dostop.poisci(id, jwt); // mehanik lahko ureja samo svoje naloge
		nalog.setOpisDela(novi.getOpisDela());
		nalog.setStanje(novi.getStanje());
		nalog.setUreDela(novi.getUreDela());
		return nalogi.save(nalog);
	}

	public void izbrisi(UUID id, Jwt jwt) {
		nalogi.delete(dostop.poisci(id, jwt));
	}

	// F5: zaloga se zmanjša, če je ni dovolj, dodajanje zavrnemo
	@Transactional
	public PostavkaNaloga dodajDel(UUID id, UUID delId, int kolicina, Jwt jwt) {
		var nalog = dostop.poisci(id, jwt); // mehanik lahko dodaja dele samo na svoje naloge
		var del = deli.findById(delId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Del ne obstaja"));
		if (del.getZaloga() < kolicina) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Premalo zaloge");
		}
		del.setZaloga(del.getZaloga() - kolicina);

		var postavka = new PostavkaNaloga();
		postavka.setNalog(nalog);
		postavka.setDel(del);
		postavka.setKolicina(kolicina);
		postavka.setCena(del.getCena());
		return postavke.save(postavka);
	}

	// F6: račun = vsota postavk + ure dela * urna postavka, redna stranka dobi popust
	@Transactional
	public Racun izdajRacun(UUID id, Jwt jwt) {
		var nalog = dostop.poisci(id, jwt);
		if (nalog.getStanje() != Stanje.ZAKLJUCEN) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Račun lahko izdamo samo za zaključen nalog");
		}
		var znesek = nalog.getUreDela().multiply(URNA_POSTAVKA);
		for (var postavka : postavke.findByNalogId(id)) {
			znesek = znesek.add(postavka.getCena().multiply(BigDecimal.valueOf(postavka.getKolicina())));
		}
		znesek = popusti.uporabi(nalog.getVozilo().getLastnik(), znesek);
		nalog.setStanje(Stanje.OBRACUNAN);
		nalog.setDatumZaprtja(LocalDate.now());

		var racun = new Racun();
		racun.setNalog(nalog);
		racun.setZnesek(znesek);
		return racuni.save(racun);
	}
}
