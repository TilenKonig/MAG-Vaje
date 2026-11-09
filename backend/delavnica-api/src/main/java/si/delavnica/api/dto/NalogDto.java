package si.delavnica.api.dto;

import si.delavnica.api.model.DelovniNalog.Stanje;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NalogDto(UUID id, String registrska, String vozilo, String mehanik, String opis, String opisDela,
		Stanje stanje, BigDecimal ureDela, LocalDate datumOdprtja, List<PostavkaDto> postavke) {}
