package si.delavnica.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.DelovniNalog;
import si.delavnica.api.model.DelovniNalog.Stanje;
import si.delavnica.api.model.PostavkaNaloga;
import si.delavnica.api.model.Racun;
import si.delavnica.api.repository.RezervniDelRepository;
import si.delavnica.api.repository.DelovniNalogRepository;
import si.delavnica.api.repository.PostavkaNalogaRepository;
import si.delavnica.api.repository.RacunRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nalogi")
@RequiredArgsConstructor
public class DelovniNalogController {

	record DodajDel(UUID delId, @Min(1) int kolicina) {}

	private static final BigDecimal URNA_POSTAVKA = new BigDecimal("50");

	private final DelovniNalogRepository nalogi;
	private final RezervniDelRepository deli;
	private final PostavkaNalogaRepository postavke;
	private final RacunRepository racuni;

	// --- CRUD (SOA vaja) ---

	@GetMapping
	List<DelovniNalog> vsi(@AuthenticationPrincipal Jwt jwt) {
		return jeStranka(jwt) ? nalogi.findByVoziloLastnikId(UUID.fromString(jwt.getSubject())) : nalogi.findAll();
	}

	@GetMapping("/{id}")
	DelovniNalog en(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		var nalog = poisci(id);
		if (jeStranka(jwt) && !nalog.getVozilo().getLastnik().getId().toString().equals(jwt.getSubject())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return nalog;
	}

	@GetMapping("/{id}/postavke")
	List<PostavkaNaloga> postavkeNaloga(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		en(id, jwt); // 404, če nalog ne obstaja ali ni od te stranke
		return postavke.findByNalogId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	DelovniNalog ustvari(@RequestBody @Valid DelovniNalog nalog) {
		nalog.setId(null);
		nalog.setStanje(Stanje.ODPRT);
		return nalogi.save(nalog);
	}

	@PutMapping("/{id}")
	DelovniNalog posodobi(@PathVariable UUID id, @RequestBody DelovniNalog novi) {
		var nalog = poisci(id);
		nalog.setOpisDela(novi.getOpisDela());
		nalog.setStanje(novi.getStanje());
		nalog.setUreDela(novi.getUreDela());
		return nalogi.save(nalog);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		nalogi.delete(poisci(id));
	}

	// --- poslovna pravila ---

	// F5: zaloga se zmanjša, če je ni dovolj, dodajanje zavrnemo
	@PostMapping("/{id}/postavke")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	PostavkaNaloga dodajDel(@PathVariable UUID id, @RequestBody @Valid DodajDel zahtevek) {
		var nalog = poisci(id);
		var del = deli.findById(zahtevek.delId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Del ne obstaja"));
		if (del.getZaloga() < zahtevek.kolicina()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Premalo zaloge");
		}
		del.setZaloga(del.getZaloga() - zahtevek.kolicina());

		var postavka = new PostavkaNaloga();
		postavka.setNalog(nalog);
		postavka.setDel(del);
		postavka.setKolicina(zahtevek.kolicina());
		postavka.setCena(del.getCena());
		return postavke.save(postavka);
	}

	// F6: račun = vsota postavk + ure dela * urna postavka
	@PostMapping("/{id}/racun")
	@Transactional
	@ResponseStatus(HttpStatus.CREATED)
	Racun izdajRacun(@PathVariable UUID id) {
		var nalog = poisci(id);
		if (nalog.getStanje() != Stanje.ZAKLJUCEN) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Račun lahko izdamo samo za zaključen nalog");
		}
		var znesek = nalog.getUreDela().multiply(URNA_POSTAVKA);
		for (var postavka : postavke.findByNalogId(id)) {
			znesek = znesek.add(postavka.getCena().multiply(BigDecimal.valueOf(postavka.getKolicina())));
		}
		nalog.setStanje(Stanje.OBRACUNAN);
		nalog.setDatumZaprtja(LocalDate.now());

		var racun = new Racun();
		racun.setNalog(nalog);
		racun.setZnesek(znesek);
		return racuni.save(racun);
	}

	private DelovniNalog poisci(UUID id) {
		return nalogi.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private boolean jeStranka(Jwt jwt) {
		return "STRANKA".equals(jwt.getClaimAsString("vloga"));
	}
}
