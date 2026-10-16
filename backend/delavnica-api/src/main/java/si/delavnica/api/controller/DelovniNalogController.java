package si.delavnica.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.model.DelovniNalog;
import si.delavnica.api.model.PostavkaNaloga;
import si.delavnica.api.model.Racun;
import si.delavnica.api.service.NalogService;
import java.util.List;
import java.util.UUID;

// Kontroler samo sprejme zahtevek in ga preda servisu, poslovna pravila so v NalogService.
@RestController
@RequestMapping("/api/nalogi")
@RequiredArgsConstructor
public class DelovniNalogController {

	record DodajDel(UUID delId, @Min(1) int kolicina) {}

	private final NalogService nalogi;

	// --- CRUD (SOA vaja) ---

	@GetMapping
	List<DelovniNalog> getAll(@AuthenticationPrincipal Jwt jwt) {
		return nalogi.getAll(jwt);
	}

	@GetMapping("/{id}")
	DelovniNalog getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.getById(id, jwt);
	}

	@GetMapping("/{id}/postavke")
	List<PostavkaNaloga> postavkeNaloga(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.postavkeNaloga(id, jwt);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	DelovniNalog ustvari(@RequestBody @Valid DelovniNalog nalog) {
		return nalogi.ustvari(nalog);
	}

	@PutMapping("/{id}")
	DelovniNalog posodobi(@PathVariable UUID id, @RequestBody DelovniNalog novi, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.posodobi(id, novi, jwt);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		nalogi.izbrisi(id, jwt);
	}

	// --- poslovna pravila ---

	@PostMapping("/{id}/postavke")
	@ResponseStatus(HttpStatus.CREATED)
	PostavkaNaloga dodajDel(@PathVariable UUID id, @RequestBody @Valid DodajDel zahtevek, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.dodajDel(id, zahtevek.delId(), zahtevek.kolicina(), jwt);
	}

	@PostMapping("/{id}/racun")
	@ResponseStatus(HttpStatus.CREATED)
	Racun izdajRacun(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.izdajRacun(id, jwt);
	}
}
