package si.delavnica.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class DelovniNalog {

	public enum Stanje { ODPRT, V_DELU, ZAKLJUCEN, OBRACUNAN }

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotNull
	@ManyToOne
	private Vozilo vozilo;

	@NotBlank
	private String opis; // težava, ki jo navede stranka

	private String opisDela;

	@Enumerated(EnumType.STRING)
	private Stanje stanje = Stanje.ODPRT;

	private BigDecimal ureDela = BigDecimal.ZERO;
	private LocalDate datumOdprtja = LocalDate.now();
	private LocalDate datumZaprtja;
}
