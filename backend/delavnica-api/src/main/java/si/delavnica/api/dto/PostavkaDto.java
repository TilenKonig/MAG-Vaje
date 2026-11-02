package si.delavnica.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PostavkaDto(UUID id, String del, int kolicina, BigDecimal cena) {}
