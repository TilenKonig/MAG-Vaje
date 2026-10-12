package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class RezervniDel {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String sifra;
	private String naziv;
	private BigDecimal cena;
	private int zaloga;
	private int minZaloga;
}
