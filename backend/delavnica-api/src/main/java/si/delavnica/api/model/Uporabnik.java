package si.delavnica.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Uporabnik {

	public enum Vloga { MEHANIK, STRANKA, VODJA }

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // geslo se nikoli ne vrne
	private String geslo;

	private String ime;
	private String priimek;
	private String telefon;

	@Enumerated(EnumType.STRING)
	private Vloga vloga;
}
