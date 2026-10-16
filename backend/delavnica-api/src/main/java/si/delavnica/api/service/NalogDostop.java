package si.delavnica.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.DelovniNalog;
import si.delavnica.api.repository.DelovniNalogRepository;
import java.util.List;
import java.util.UUID;

// Kateri nalogi so vidni prijavljenemu uporabniku: stranka svoji, mehanik dodeljeni, vodja vsi.
@Service
@RequiredArgsConstructor
public class NalogDostop {

	private final DelovniNalogRepository nalogi;

	public List<DelovniNalog> vidni(Jwt jwt) {
		var uporabnikId = Zeton.uporabnikId(jwt);
		return switch (Zeton.vloga(jwt)) {
			case STRANKA -> nalogi.findByVoziloLastnikId(uporabnikId);
			case MEHANIK -> nalogi.findByMehanikId(uporabnikId);
			case VODJA -> nalogi.findAll();
		};
	}

	// 404 tudi za tuj nalog, da uporabnik ne izve, da obstaja
	public DelovniNalog poisci(UUID id, Jwt jwt) {
		return nalogi.findById(id)
				.filter(nalog -> smeVideti(nalog, jwt))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private boolean smeVideti(DelovniNalog nalog, Jwt jwt) {
		var uporabnikId = Zeton.uporabnikId(jwt);
		return switch (Zeton.vloga(jwt)) {
			case STRANKA -> nalog.getVozilo().getLastnik().getId().equals(uporabnikId);
			case MEHANIK -> nalog.getMehanik().getId().equals(uporabnikId);
			case VODJA -> true;
		};
	}
}
