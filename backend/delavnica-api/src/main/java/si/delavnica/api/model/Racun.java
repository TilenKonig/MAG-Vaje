package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Racun {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne
	private DelovniNalog nalog;

	private BigDecimal znesek;
	private LocalDate datum = LocalDate.now();
	private boolean placan;
}
