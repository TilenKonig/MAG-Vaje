package si.delavnica.api.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class NapakeHandler {

	// npr. brisanje vozila, ki ima še naloge, ali podvojen email
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	Map<String, String> konflikt() {
		return Map.of("napaka", "Zapisa ni mogoče shraniti ali izbrisati, ker je povezan z drugimi zapisi ali podvojen");
	}
}
