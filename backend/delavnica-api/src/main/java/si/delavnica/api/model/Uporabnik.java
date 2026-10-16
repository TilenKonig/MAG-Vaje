package si.delavnica.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Uporabnik extends BaseEntity {

	public enum Vloga { MEHANIK, STRANKA, VODJA }

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
