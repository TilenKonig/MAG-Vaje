package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.DelovniNalog;
import java.util.List;
import java.util.UUID;

public interface DelovniNalogRepository extends JpaRepository<DelovniNalog, UUID> {
	List<DelovniNalog> findByVoziloLastnikId(UUID lastnikId);
}
