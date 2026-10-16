package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class PostavkaNaloga extends BaseEntity {

	@ManyToOne
	private DelovniNalog nalog;

	@ManyToOne
	private RezervniDel del;

	private int kolicina;
	private BigDecimal cena; // cena dela ob dodajanju
}
