package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class RezervniDel extends BaseEntity {

	private String sifra;
	private String naziv;
	private BigDecimal cena;
	private int zaloga;
	private int minZaloga;
}
