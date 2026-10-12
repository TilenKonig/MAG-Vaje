package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.RezervniDel;
import java.util.UUID;

public interface RezervniDelRepository extends JpaRepository<RezervniDel, UUID> {
}
