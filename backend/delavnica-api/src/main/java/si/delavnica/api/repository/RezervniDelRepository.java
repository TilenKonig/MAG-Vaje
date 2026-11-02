package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import si.delavnica.api.model.RezervniDel;
import java.util.UUID;

public interface RezervniDelRepository extends JpaRepository<RezervniDel, UUID> {

	@Modifying
	@Transactional
	@Query("update RezervniDel d set d.zaloga = d.zaloga + :kolicina where d.id = :id")
	int povecajZalogo(UUID id, int kolicina);
}
