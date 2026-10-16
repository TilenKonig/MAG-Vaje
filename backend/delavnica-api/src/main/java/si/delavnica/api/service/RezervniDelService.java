package si.delavnica.api.service;

import org.springframework.stereotype.Service;
import si.delavnica.api.model.RezervniDel;
import si.delavnica.api.repository.RezervniDelRepository;

// Samo osnovni CRUD iz CrudService, posebnih pravil ni.
@Service
public class RezervniDelService extends CrudService<RezervniDel> {

	public RezervniDelService(RezervniDelRepository deli) {
		super(deli);
	}
}
