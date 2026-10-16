package si.delavnica.api.service;

import org.springframework.security.oauth2.jwt.Jwt;
import si.delavnica.api.model.Uporabnik.Vloga;
import java.util.UUID;

// Podatki o prijavljenem uporabniku iz JWT: ID je "subject", vloga je zahtevek "vloga".
public final class Zeton {

	private Zeton() {}

	public static UUID uporabnikId(Jwt jwt) {
		return UUID.fromString(jwt.getSubject());
	}

	public static Vloga vloga(Jwt jwt) {
		return Vloga.valueOf(jwt.getClaimAsString("vloga"));
	}
}
