package si.delavnica.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vozilo extends BaseEntity {

	@ManyToOne
	private Uporabnik lastnik;

	private String znamka;
	private String model;
	private int letnik;
	private String registrska;
}
