package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.model.RezervniDel;
import si.delavnica.api.service.RezervniDelService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deli")
@RequiredArgsConstructor
public class RezervniDelController {

	private final RezervniDelService deli;

	@GetMapping
	List<RezervniDel> getAll() {
		return deli.getAll();
	}

	@GetMapping("/{id}")
	RezervniDel getById(@PathVariable UUID id) {
		return deli.getById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	RezervniDel ustvari(@RequestBody RezervniDel rezervniDel) {
		return deli.ustvari(rezervniDel);
	}

	@PutMapping("/{id}")
	RezervniDel posodobi(@PathVariable UUID id, @RequestBody RezervniDel rezervniDel) {
		return deli.posodobi(id, rezervniDel);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		deli.izbrisi(id);
	}
}
