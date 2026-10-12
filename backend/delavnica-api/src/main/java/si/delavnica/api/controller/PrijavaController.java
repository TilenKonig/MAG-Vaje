package si.delavnica.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.repository.UporabnikRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@RestController
@RequestMapping("/api/prijava")
@RequiredArgsConstructor
public class PrijavaController {

	record Prijava(String email, String geslo) {}

	private final UporabnikRepository uporabniki;
	private final PasswordEncoder encoder;
	private final JwtEncoder jwtEncoder;

	@PostMapping
	Map<String, String> prijava(@RequestBody Prijava zahtevek) {
		var uporabnik = uporabniki.findByEmail(zahtevek.email())
				.filter(najden -> encoder.matches(zahtevek.geslo(), najden.getGeslo()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Napačen email ali geslo"));

		var zahtevki = JwtClaimsSet.builder()
				.subject(uporabnik.getId().toString())
				.claim("vloga", uporabnik.getVloga().name())
				.expiresAt(Instant.now().plus(8, ChronoUnit.HOURS))
				.build();
		var glava = JwsHeader.with(MacAlgorithm.HS256).build();
		return Map.of("token", jwtEncoder.encode(JwtEncoderParameters.from(glava, zahtevki)).getTokenValue());
	}
}
