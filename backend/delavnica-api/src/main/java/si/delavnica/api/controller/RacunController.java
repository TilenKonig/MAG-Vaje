package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.model.Racun;
import si.delavnica.api.service.RacunService;
import java.util.List;
import java.util.UUID;

// Računi nastanejo samo z izdajo iz naloga (POST /api/nalogi/{id}/racun), zato tukaj ni POST.
@RestController
@RequestMapping("/api/racuni")
@RequiredArgsConstructor
public class RacunController {

	private final RacunService racuni;

	@GetMapping
	List<Racun> getAll(@AuthenticationPrincipal Jwt jwt) {
		return racuni.getAll(jwt);
	}

	@GetMapping("/{id}")
	Racun getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return racuni.getById(id, jwt);
	}

	// vodja lahko spremeni samo plačano (F11)
	@PutMapping("/{id}")
	Racun posodobi(@PathVariable UUID id, @RequestBody Racun novi) {
		return racuni.nastaviPlacan(id, novi.isPlacan());
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		racuni.izbrisi(id);
	}
}
