package si.delavnica.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import si.delavnica.api.dto.PorociloDto;
import si.delavnica.api.repository.DelovniNalogRepository;
import si.delavnica.api.repository.PostavkaNalogaRepository;
import si.delavnica.api.repository.RacunRepository;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PorociloService {

	private static final BigDecimal NABAVNI_DELEZ = new BigDecimal("0.7");

	private final RacunRepository racuni;
	private final PostavkaNalogaRepository postavke;
	private final DelovniNalogRepository nalogi;

	public PorociloDto pripravi() {
		var prihodekPoMesecih = racuni.prihodekPoMesecih();
		var prihodek = prihodekPoMesecih.stream().map(m -> m.prihodek()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
		var profit = prihodek.subtract(postavke.vrednostDelov().multiply(NABAVNI_DELEZ));
		return new PorociloDto(prihodekPoMesecih, postavke.prodaniDeli(), nalogi.opravljeneUre(), profit);
	}
}
