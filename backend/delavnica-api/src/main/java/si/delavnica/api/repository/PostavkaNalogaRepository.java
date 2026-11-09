package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import si.delavnica.api.dto.ProdanDelDto;
import si.delavnica.api.model.PostavkaNaloga;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PostavkaNalogaRepository extends JpaRepository<PostavkaNaloga, UUID> {
	List<PostavkaNaloga> findByNalogId(UUID nalogId);

	// samo postavke obračunanih nalogov, da se vsote ujemajo z računi
	@Query("select new si.delavnica.api.dto.ProdanDelDto(p.del.naziv, sum(p.kolicina)) from PostavkaNaloga p "
			+ "where p.nalog.id in (select r.nalog.id from Racun r) group by p.del.id, p.del.naziv order by sum(p.kolicina) desc")
	List<ProdanDelDto> prodaniDeli();

	@Query("select coalesce(sum(p.cena * p.kolicina), 0) from PostavkaNaloga p where p.nalog.id in (select r.nalog.id from Racun r)")
	BigDecimal vrednostDelov();
}
