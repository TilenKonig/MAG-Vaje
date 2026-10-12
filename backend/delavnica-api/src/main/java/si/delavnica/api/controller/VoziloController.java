package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.Vozilo;
import si.delavnica.api.repository.VoziloRepository;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vozila")
@RequiredArgsConstructor
public class VoziloController {

	private final VoziloRepository vozila;

	@GetMapping
	List<Vozilo> vsa(@AuthenticationPrincipal Jwt jwt) {
		return jeStranka(jwt) ? vozila.findByLastnikId(UUID.fromString(jwt.getSubject())) : vozila.findAll();
	}

	@GetMapping("/{id}")
	Vozilo eno(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		var vozilo = poisci(id);
		if (jeStranka(jwt) && !vozilo.getLastnik().getId().toString().equals(jwt.getSubject())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return vozilo;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Vozilo ustvari(@RequestBody Vozilo vozilo) {
		vozilo.setId(null);
		return vozila.save(vozilo);
	}

	@PutMapping("/{id}")
	Vozilo posodobi(@PathVariable UUID id, @RequestBody Vozilo vozilo) {
		poisci(id); // 404, če ne obstaja
		vozilo.setId(id);
		return vozila.save(vozilo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		vozila.delete(poisci(id));
	}

	private Vozilo poisci(UUID id) {
		return vozila.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private boolean jeStranka(Jwt jwt) {
		return "STRANKA".equals(jwt.getClaimAsString("vloga"));
	}
}
