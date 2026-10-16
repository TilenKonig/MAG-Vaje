package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import si.delavnica.api.service.PrijavaService;
import java.util.Map;

@RestController
@RequestMapping("/api/prijava")
@RequiredArgsConstructor
public class PrijavaController {

	record Prijava(String email, String geslo) {}

	private final PrijavaService prijave;

	@PostMapping
	Map<String, String> prijava(@RequestBody Prijava zahtevek) {
		return Map.of("token", prijave.prijava(zahtevek.email(), zahtevek.geslo()));
	}
}
