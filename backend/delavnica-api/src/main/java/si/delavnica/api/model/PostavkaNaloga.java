package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PostavkaNaloga {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	private DelovniNalog nalog;

	@ManyToOne
	private RezervniDel del;

	private int kolicina;
	private BigDecimal cena; // cena dela ob dodajanju
}
