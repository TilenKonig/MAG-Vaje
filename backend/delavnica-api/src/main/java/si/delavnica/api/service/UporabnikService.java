package si.delavnica.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.repository.UporabnikRepository;
import java.util.UUID;

// Osnovni CRUD iz CrudService, dodano je samo zgoščevanje gesla.
@Service
public class UporabnikService extends CrudService<Uporabnik> {

	private final PasswordEncoder encoder;

	public UporabnikService(UporabnikRepository uporabniki, PasswordEncoder encoder) {
		super(uporabniki);
		this.encoder = encoder;
	}

	@Override
	public Uporabnik ustvari(Uporabnik uporabnik) {
		uporabnik.setGeslo(encoder.encode(uporabnik.getGeslo()));
		return super.ustvari(uporabnik);
	}

	@Override
	public Uporabnik posodobi(UUID id, Uporabnik uporabnik) {
		var obstojec = getById(id);
		// brez novega gesla ostane staro
		uporabnik.setGeslo(uporabnik.getGeslo() == null ? obstojec.getGeslo() : encoder.encode(uporabnik.getGeslo()));
		return super.posodobi(id, uporabnik);
	}
}
