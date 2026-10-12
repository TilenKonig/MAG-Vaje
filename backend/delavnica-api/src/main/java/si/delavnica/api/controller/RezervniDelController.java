package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.RezervniDel;
import si.delavnica.api.repository.RezervniDelRepository;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deli")
@RequiredArgsConstructor
public class RezervniDelController {

	private final RezervniDelRepository deli;

	@GetMapping
	List<RezervniDel> vsi() {
		return deli.findAll();
	}

	@GetMapping("/{id}")
	RezervniDel en(@PathVariable UUID id) {
		return deli.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	RezervniDel ustvari(@RequestBody RezervniDel rezervniDel) {
		rezervniDel.setId(null);
		return deli.save(rezervniDel);
	}

	@PutMapping("/{id}")
	RezervniDel posodobi(@PathVariable UUID id, @RequestBody RezervniDel rezervniDel) {
		en(id); // 404, če ne obstaja
		rezervniDel.setId(id);
		return deli.save(rezervniDel);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void izbrisi(@PathVariable UUID id) {
		deli.delete(en(id));
	}
}
