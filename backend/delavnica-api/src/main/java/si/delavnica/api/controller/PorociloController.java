package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import si.delavnica.api.dto.PorociloDto;
import si.delavnica.api.service.PorociloService;

@RestController
@RequestMapping("/api/porocilo")
@RequiredArgsConstructor
public class PorociloController {

	private final PorociloService porocilo;

	@GetMapping
	PorociloDto get() {
		return porocilo.pripravi();
	}
}
