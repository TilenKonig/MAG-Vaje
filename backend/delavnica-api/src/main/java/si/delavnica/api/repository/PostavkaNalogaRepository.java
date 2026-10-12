package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import si.delavnica.api.model.PostavkaNaloga;
import java.util.List;
import java.util.UUID;

public interface PostavkaNalogaRepository extends JpaRepository<PostavkaNaloga, UUID> {
	List<PostavkaNaloga> findByNalogId(UUID nalogId);
}
