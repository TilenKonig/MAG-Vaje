package si.delavnica.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;

// Skupna polja vseh entitet: id (UUID) in časovna žiga (kazalec za inkrementalni vnos v podatkovno jezero).
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@CreationTimestamp
	@Column(updatable = false) // PUT pošlje entiteto brez tega polja, ob posodobitvi ga ne smemo prepisati z null
	private Instant createdAt;

	@UpdateTimestamp
	private Instant updatedAt;
}
