package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import si.delavnica.api.model.RezervniDel;
import java.util.List;
import java.util.UUID;

public interface RezervniDelRepository extends JpaRepository<RezervniDel, UUID> {

	@Query("select d from RezervniDel d where d.zaloga < d.minZaloga")
	List<RezervniDel> findPodMinimalnoZalogo();

	@Modifying
	@Transactional
	@Query("update RezervniDel d set d.zaloga = d.zaloga + :kolicina where d.id = :id")
	int povecajZalogo(UUID id, int kolicina);
}
