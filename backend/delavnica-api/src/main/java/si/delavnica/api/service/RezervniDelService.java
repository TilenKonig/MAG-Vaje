package si.delavnica.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.RezervniDel;
import si.delavnica.api.repository.RezervniDelRepository;
import si.delavnica.api.dto.PredlogNarocilaDto;
import java.util.List;
import java.util.UUID;

// Osnovni CRUD iz CrudService in naročanje delov.
@Service
public class RezervniDelService extends CrudService<RezervniDel> {

	private final RezervniDelRepository deli;

	public RezervniDelService(RezervniDelRepository deli) {
		super(deli);
		this.deli = deli;
	}

	public List<PredlogNarocilaDto> predlogNarocila() {
		return deli.findPodMinimalnoZalogo().stream()
				.map(d -> new PredlogNarocilaDto(d, d.getMinZaloga() - d.getZaloga()))
				.toList();
	}

	public void naroci(UUID id, int kolicina) {
		if (kolicina <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Količina mora biti večja od 0");
		}
		if (deli.povecajZalogo(id, kolicina) == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
}
