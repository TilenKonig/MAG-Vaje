package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Racun extends BaseEntity {

	@OneToOne
	private DelovniNalog nalog;

	private BigDecimal znesek;
	private LocalDate datum = LocalDate.now();
	private boolean placan;
}
