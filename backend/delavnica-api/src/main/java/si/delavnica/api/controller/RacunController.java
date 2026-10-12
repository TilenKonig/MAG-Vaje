package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.Racun;
import si.delavnica.api.repository.RacunRepository;
import java.util.List;
import java.util.UUID;

// Računi nastanejo samo z izdajo iz naloga (POST /api/nalogi/{id}/racun), zato tukaj ni POST.
@RestController
@RequestMapping("/api/racuni")
@RequiredArgsConstructor
public class RacunController {

	private final RacunRepository racuni;

	@GetMapping
	List<Racun> vsi(@AuthenticationPrincipal Jwt jwt) {
		return jeStranka(jwt) ? racuni.findByNalogVoziloLastnikId(UUID.fromString(jwt.getSubject())) : racuni.findAll();
	}

	@GetMapping("/{id}")
	Racun en(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		var racun = poisci(id);
		if (jeStranka(jwt) && !racun.getNalog().getVozilo().getLastnik().getId().toString().equals(jwt.getSubject())) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return racun;
	}

	// vodja lahko spremeni samo plačano (F11)
	@PutMapping("/{id}")
	Racun posodobi(@PathVariable UUID id, @RequestBody Racun novi) {
		var racun = poisci(id);
		racun.setPlacan(novi.isPlacan());
		return racuni.save(racun);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		racuni.delete(poisci(id));
	}

	private Racun poisci(UUID id) {
		return racuni.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private boolean jeStranka(Jwt jwt) {
		return "STRANKA".equals(jwt.getClaimAsString("vloga"));
	}
}
