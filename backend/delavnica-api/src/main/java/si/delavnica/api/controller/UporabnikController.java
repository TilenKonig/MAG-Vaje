package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.service.UporabnikService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/uporabniki")
@RequiredArgsConstructor
public class UporabnikController {

	private final UporabnikService uporabniki;

	@GetMapping
	List<Uporabnik> getAll() {
		return uporabniki.getAll();
	}

	@GetMapping("/{id}")
	Uporabnik getById(@PathVariable UUID id) {
		return uporabniki.getById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	Uporabnik ustvari(@RequestBody Uporabnik uporabnik) {
		return uporabniki.ustvari(uporabnik);
	}

	@PutMapping("/{id}")
	Uporabnik posodobi(@PathVariable UUID id, @RequestBody Uporabnik uporabnik) {
		return uporabniki.posodobi(id, uporabnik);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		uporabniki.izbrisi(id);
	}
}
