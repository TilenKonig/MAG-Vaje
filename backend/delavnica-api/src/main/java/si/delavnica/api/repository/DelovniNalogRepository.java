package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.DelovniNalog;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface DelovniNalogRepository extends JpaRepository<DelovniNalog, UUID> {
	List<DelovniNalog> findByVoziloLastnikId(UUID lastnikId);

	List<DelovniNalog> findByMehanikId(UUID mehanikId);

	@Query("select coalesce(sum(n.ureDela), 0) from DelovniNalog n where n.id in (select r.nalog.id from Racun r)")
	BigDecimal opravljeneUre();
}
