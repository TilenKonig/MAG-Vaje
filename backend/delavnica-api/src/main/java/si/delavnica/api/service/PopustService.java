package si.delavnica.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.repository.RacunRepository;
import java.math.BigDecimal;

// Poslovno pravilo: redna stranka (od 5. računa dalje) dobi 10 % popusta.
@Service
@RequiredArgsConstructor
public class PopustService {

	private static final long RACUNOV_ZA_POPUST = 4;
	private static final BigDecimal DELEZ_PLACILA = new BigDecimal("0.90");

	private final RacunRepository racuni;

	public BigDecimal uporabi(Uporabnik stranka, BigDecimal znesek) {
		boolean redna = racuni.countByNalogVoziloLastnikId(stranka.getId()) >= RACUNOV_ZA_POPUST;
		return redna ? znesek.multiply(DELEZ_PLACILA) : znesek;
	}
}
