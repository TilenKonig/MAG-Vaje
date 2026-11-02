package si.delavnica.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.dto.DodajDelDto;
import si.delavnica.api.dto.NalogDto;
import si.delavnica.api.model.DelovniNalog;
import si.delavnica.api.model.DelovniNalog.Stanje;
import si.delavnica.api.model.PostavkaNaloga;
import si.delavnica.api.model.Racun;
import si.delavnica.api.service.NalogService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/nalogi")
@RequiredArgsConstructor
public class DelovniNalogController {

	private final NalogService nalogi;

	@GetMapping
	List<NalogDto> getAll(@RequestParam(required = false) Stanje stanje, @RequestParam(required = false) String registrska,
			@RequestParam(required = false) UUID strankaId, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.getAll(stanje, registrska, strankaId, jwt);
	}

	@GetMapping("/{id}")
	NalogDto getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.getById(id, jwt);
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
	PostavkaNaloga dodajDel(@PathVariable UUID id, @RequestBody @Valid DodajDelDto zahtevek, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.dodajDel(id, zahtevek.delId(), zahtevek.kolicina(), jwt);
	}

	@PostMapping("/{id}/racun")
	@ResponseStatus(HttpStatus.CREATED)
	Racun izdajRacun(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
		return nalogi.izdajRacun(id, jwt);
	}
}
