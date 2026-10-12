package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.repository.UporabnikRepository;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/uporabniki")
@RequiredArgsConstructor
public class UporabnikController {

	private final UporabnikRepository uporabniki;
	private final PasswordEncoder encoder;

	@GetMapping
	List<Uporabnik> vsi() {
		return uporabniki.findAll();
	}

	@GetMapping("/{id}")
	Uporabnik en(@PathVariable UUID id) {
		return uporabniki.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Uporabnik ustvari(@RequestBody Uporabnik uporabnik) {
		uporabnik.setId(null);
		uporabnik.setGeslo(encoder.encode(uporabnik.getGeslo()));
		return uporabniki.save(uporabnik);
	}

	@PutMapping("/{id}")
	Uporabnik posodobi(@PathVariable UUID id, @RequestBody Uporabnik uporabnik) {
		var obstojec = en(id);
		uporabnik.setId(id);
		// brez novega gesla ostane staro
		uporabnik.setGeslo(uporabnik.getGeslo() == null ? obstojec.getGeslo() : encoder.encode(uporabnik.getGeslo()));
		return uporabniki.save(uporabnik);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		uporabniki.delete(en(id));
	}
}
