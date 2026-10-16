package si.delavnica.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.repository.UporabnikRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PrijavaService {

	private final UporabnikRepository uporabniki;
	private final PasswordEncoder encoder;
	private final JwtEncoder jwtEncoder;

	// vrne JWT z ID-jem uporabnika in njegovo vlogo
	public String prijava(String email, String geslo) {
		var uporabnik = uporabniki.findByEmail(email)
				.filter(najden -> encoder.matches(geslo, najden.getGeslo()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Napačen email ali geslo"));

		var zahtevki = JwtClaimsSet.builder()
				.subject(uporabnik.getId().toString())
				.claim("vloga", uporabnik.getVloga().name())
				.expiresAt(Instant.now().plus(8, ChronoUnit.HOURS))
				.build();
		var glava = JwsHeader.with(MacAlgorithm.HS256).build();
		return jwtEncoder.encode(JwtEncoderParameters.from(glava, zahtevki)).getTokenValue();
	}
}
