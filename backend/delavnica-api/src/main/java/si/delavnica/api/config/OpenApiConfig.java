package si.delavnica.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

// V Swagger UI doda gumb Authorize: vanj prilepiš žeton iz POST /api/prijava, nato ga pošlje pri vsakem klicu.
@Configuration
@OpenAPIDefinition(
		info = @Info(title = "Mehanična delavnica API", version = "1.0"),
		security = @SecurityRequirement(name = "jwt"))
@SecurityScheme(name = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
}
