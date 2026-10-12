package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.Vozilo;
import java.util.List;
import java.util.UUID;

public interface VoziloRepository extends JpaRepository<Vozilo, UUID> {
	List<Vozilo> findByLastnikId(UUID lastnikId);
}
