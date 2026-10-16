package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.Racun;
import java.util.List;
import java.util.UUID;

public interface RacunRepository extends JpaRepository<Racun, UUID> {
	List<Racun> findByNalogVoziloLastnikId(UUID lastnikId);

	long countByNalogVoziloLastnikId(UUID lastnikId);
}
