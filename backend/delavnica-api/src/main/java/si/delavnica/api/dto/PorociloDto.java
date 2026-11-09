package si.delavnica.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record PorociloDto(List<MesecniPrihodekDto> prihodekPoMesecih, List<ProdanDelDto> prodaniDeli,
		BigDecimal opravljeneUre, BigDecimal profit) {}
