package si.delavnica.api.dto;

import jakarta.validation.constraints.Min;
import java.util.UUID;

public record DodajDelDto(UUID delId, @Min(1) int kolicina) {}
