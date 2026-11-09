package si.delavnica.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.repository.UporabnikRepository;
import java.util.UUID;

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
		uporabnik.setTelefon(slovenskiTelefon(uporabnik.getTelefon()));
		return super.ustvari(uporabnik);
	}

	@Override
	public Uporabnik posodobi(UUID id, Uporabnik uporabnik) {
		var obstojec = getById(id);
				uporabnik.setGeslo(uporabnik.getGeslo() == null ? obstojec.getGeslo() : encoder.encode(uporabnik.getGeslo()));
		uporabnik.setTelefon(slovenskiTelefon(uporabnik.getTelefon()));
		return super.posodobi(id, uporabnik);
	}

	// +386 41 000 000 / 00386... -> 041000000; tuje številke dobijo samo odstranjene presledke/vezaje
	static String slovenskiTelefon(String telefon) {
		return telefon == null ? null : telefon.replaceAll("[\\s\\-/]", "").replaceFirst("^(\\+|00)386", "0");
	}
}
