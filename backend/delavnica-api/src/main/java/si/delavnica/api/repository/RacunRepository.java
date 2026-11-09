package si.delavnica.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import si.delavnica.api.dto.MesecniPrihodekDto;
import si.delavnica.api.model.Racun;
import java.util.List;
import java.util.UUID;

public interface RacunRepository extends JpaRepository<Racun, UUID> {
	List<Racun> findByNalogVoziloLastnikId(UUID lastnikId);

	long countByNalogVoziloLastnikId(UUID lastnikId);

	@Query("select new si.delavnica.api.dto.MesecniPrihodekDto(year(r.datum), month(r.datum), sum(r.znesek)) "
			+ "from Racun r group by year(r.datum), month(r.datum) order by year(r.datum), month(r.datum)")
	List<MesecniPrihodekDto> prihodekPoMesecih();
}
