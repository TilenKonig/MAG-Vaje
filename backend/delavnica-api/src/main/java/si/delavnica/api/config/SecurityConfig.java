package si.delavnica.api.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// vloga je v žetonu v zahtevku "vloga" -> ROLE_<vloga>
		var vloge = new JwtGrantedAuthoritiesConverter();
		vloge.setAuthoritiesClaimName("vloga");
		vloge.setAuthorityPrefix("ROLE_");
		var pretvornik = new JwtAuthenticationConverter();
		pretvornik.setJwtGrantedAuthoritiesConverter(vloge);

		return http
				.csrf(csrf -> csrf.disable()) // žeton v glavi, brez piškotkov
				.sessionManagement(seja -> seja.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(zahteve -> zahteve
						.requestMatchers("/api/prijava", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/uporabniki/**").hasRole("VODJA")
						.requestMatchers(HttpMethod.GET, "/api/deli/**").hasAnyRole("MEHANIK", "VODJA")
						.requestMatchers(HttpMethod.GET, "/api/**").authenticated() // stranka je v kontrolerjih omejena na svoje
						.requestMatchers(HttpMethod.PUT, "/api/nalogi/*").hasAnyRole("MEHANIK", "VODJA")
						.requestMatchers(HttpMethod.POST, "/api/nalogi/*/postavke").hasAnyRole("MEHANIK", "VODJA")
						.anyRequest().hasRole("VODJA"))
				.oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(pretvornik)))
				.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecretKey jwtKljuc(@Value("${jwt.secret}") String skrivnost) {
		return new SecretKeySpec(skrivnost.getBytes(), "HmacSHA256");
	}

	@Bean
	JwtDecoder jwtDecoder(SecretKey kljuc) {
		return NimbusJwtDecoder.withSecretKey(kljuc).build();
	}

	@Bean
	JwtEncoder jwtEncoder(SecretKey kljuc) {
		return new NimbusJwtEncoder(new ImmutableSecret<>(kljuc));
	}
}
