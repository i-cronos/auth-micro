package pe.ibk.cpe.auth.infrastructure.security.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("app.security.authentication")
public class AppSecurityConfiguration {
    private String collaboratorPath;
    private String customerPath;
}
