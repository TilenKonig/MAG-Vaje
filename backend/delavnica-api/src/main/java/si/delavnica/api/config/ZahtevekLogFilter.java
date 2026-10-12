package si.delavnica.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// V terminal zapiše vsako spreminjanje podatkov (POST, PUT, DELETE) z vsebino zahtevka in rezultatom, branja (GET) ne.
@Slf4j
@Component
public class ZahtevekLogFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest zahtevek, HttpServletResponse odgovor, FilterChain veriga)
			throws ServletException, IOException {
		// ovojnica si zapomni telo, ki ga prebere kontroler (sicer ga po obdelavi ni več mogoče prebrati)
		var ovojnica = new ContentCachingRequestWrapper(zahtevek, 10_000); // daljše telo se ne zapiše v dnevnik
		veriga.doFilter(ovojnica, odgovor);

		if (!zahtevek.getMethod().equals("GET")) {
			// gesla se nikoli ne zapišejo v dnevnik
			var telo = new String(ovojnica.getContentAsByteArray(), StandardCharsets.UTF_8)
					.replaceAll("\"geslo\"\\s*:\\s*\"[^\"]*\"", "\"geslo\":\"***\"");
			log.info("{} {} {} -> {}", zahtevek.getMethod(), zahtevek.getRequestURI(), telo, odgovor.getStatus());
		}
	}
}
