package si.delavnica.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.Uporabnik.Vloga;
import si.delavnica.api.model.Vozilo;
import si.delavnica.api.repository.VoziloRepository;
import java.util.List;
import java.util.UUID;

// Osnovni CRUD iz CrudService, dodano je samo omejevanje strank na njihova vozila.
@Service
public class VoziloService extends CrudService<Vozilo> {

	private final VoziloRepository vozila;

	public VoziloService(VoziloRepository vozila) {
		super(vozila);
		this.vozila = vozila;
	}

	public List<Vozilo> getAll(Jwt jwt) {
		return Zeton.vloga(jwt) == Vloga.STRANKA ? vozila.findByLastnikId(Zeton.uporabnikId(jwt)) : getAll();
	}

	public Vozilo getById(UUID id, Jwt jwt) {
		var vozilo = getById(id);
		if (Zeton.vloga(jwt) == Vloga.STRANKA && !vozilo.getLastnik().getId().equals(Zeton.uporabnikId(jwt))) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return vozilo;
	}
}
