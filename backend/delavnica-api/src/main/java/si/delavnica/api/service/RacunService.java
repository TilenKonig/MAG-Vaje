package si.delavnica.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.Racun;
import si.delavnica.api.model.Uporabnik.Vloga;
import si.delavnica.api.repository.RacunRepository;
import java.util.List;
import java.util.UUID;

// Računi nastanejo samo z izdajo iz naloga (NalogService.izdajRacun), zato se ustvari() in posodobi() iz CrudService ne uporabljata.
@Service
public class RacunService extends CrudService<Racun> {

	private final RacunRepository racuni;

	public RacunService(RacunRepository racuni) {
		super(racuni);
		this.racuni = racuni;
	}

	public List<Racun> getAll(Jwt jwt) {
		return Zeton.vloga(jwt) == Vloga.STRANKA ? racuni.findByNalogVoziloLastnikId(Zeton.uporabnikId(jwt)) : getAll();
	}

	public Racun getById(UUID id, Jwt jwt) {
		var racun = getById(id);
		if (Zeton.vloga(jwt) == Vloga.STRANKA && !racun.getNalog().getVozilo().getLastnik().getId().equals(Zeton.uporabnikId(jwt))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return racun;
	}

	// vodja lahko spremeni samo plačano (F11)
	public Racun nastaviPlacan(UUID id, boolean placan) {
		var racun = getById(id);
		racun.setPlacan(placan);
		return racuni.save(racun);
	}
}
