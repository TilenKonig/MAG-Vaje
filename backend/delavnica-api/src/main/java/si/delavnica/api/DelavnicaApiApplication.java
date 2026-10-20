package si.delavnica.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import si.delavnica.api.model.Uporabnik;
import si.delavnica.api.model.Uporabnik.Vloga;
import si.delavnica.api.repository.UporabnikRepository;

@SpringBootApplication
public class DelavnicaApiApplication {

	public static void main(String[] args) {
		System.out.println("PRISTANICNIK dela: api se zaganja");
		SpringApplication.run(DelavnicaApiApplication.class, args);
	}

	// Prvi vodja, da se je mogoče prijaviti (samo razvoj, geslo je javno)
	@Bean
	CommandLineRunner zacetniVodja(UporabnikRepository uporabniki, PasswordEncoder encoder) {
		return args -> {
			if (uporabniki.findByEmail("vodja@delavnica.si").isEmpty()) {
				var uporabnik = new Uporabnik();
				uporabnik.setEmail("vodja@delavnica.si");
				uporabnik.setGeslo(encoder.encode("vodja123"));
				uporabnik.setIme("Vodja");
				uporabnik.setPriimek("Delavnice");
				uporabnik.setVloga(Vloga.VODJA);
				uporabniki.save(uporabnik);
			}
		};
	}

}
