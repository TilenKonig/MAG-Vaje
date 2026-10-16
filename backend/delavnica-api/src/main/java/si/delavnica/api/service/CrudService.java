package si.delavnica.api.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import si.delavnica.api.model.BaseEntity;
import java.util.List;
import java.util.UUID;

// Skupni CRUD za vse entitete. Konkretni servis podeduje osnovo in doda ali prepiše samo svoja pravila.
public abstract class CrudService<T extends BaseEntity> {

	private final JpaRepository<T, UUID> repository;

	protected CrudService(JpaRepository<T, UUID> repository) {
		this.repository = repository;
	}

	public List<T> getAll() {
		return repository.findAll();
	}

	public T getById(UUID id) {
		return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public T ustvari(T entiteta) {
		entiteta.setId(null);
		return repository.save(entiteta);
	}

	public T posodobi(UUID id, T entiteta) {
		getById(id); // 404, če ne obstaja
		entiteta.setId(id);
		return repository.save(entiteta);
	}

	public void izbrisi(UUID id) {
		repository.delete(getById(id));
	}
}
