package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.model.Vozilo;
import si.delavnica.api.service.VoziloService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vozila")
@RequiredArgsConstructor
public class VoziloController {

	private final VoziloService vozila;

	@GetMapping
	List<Vozilo> getAll(@AuthenticationPrincipal Jwt jwt) {
		return vozila.getAll(jwt);
	}

	@GetMapping("/{id}")
	Vozilo getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return vozila.getById(id, jwt);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Vozilo ustvari(@RequestBody Vozilo vozilo) {
		return vozila.ustvari(vozilo);
	}

	@PutMapping("/{id}")
	Vozilo posodobi(@PathVariable UUID id, @RequestBody Vozilo vozilo) {
		return vozila.posodobi(id, vozilo);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		vozila.izbrisi(id);
	}
}
