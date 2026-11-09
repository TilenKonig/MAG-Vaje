package si.delavnica.api.dto;

import java.math.BigDecimal;

public record MesecniPrihodekDto(int leto, int mesec, BigDecimal prihodek) {}
