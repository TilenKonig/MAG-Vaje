package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.Uporabnik;
import java.util.Optional;
import java.util.UUID;

public interface UporabnikRepository extends JpaRepository<Uporabnik, UUID> {
	Optional<Uporabnik> findByEmail(String email);
}
