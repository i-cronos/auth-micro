package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCollaboratorUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCustomerUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCollaboratorPort;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;
import pe.ibk.cpe.auth.domain.service.user.service.LoginCollaboratorService;
import pe.ibk.cpe.auth.domain.service.user.service.LoginCustomerService;
import pe.ibk.cpe.auth.infrastructure.security.adapter.LoginCollaboratorAdapter;
import pe.ibk.cpe.auth.infrastructure.security.adapter.LoginCustomerAdapter;
import pe.ibk.cpe.dependencies.global.jwt.JwtProvider;
import pe.ibk.cpe.dependencies.global.util.JsonLogUtil;

@Configuration
public class AuthLoginConfiguration {

    @Bean
    public LoginCustomerPort loginCustomerPort(AuthenticationManager authenticationManager, JsonLogUtil jsonLogUtil, JwtProvider jwtProvider) {
        return new LoginCustomerAdapter(authenticationManager, jsonLogUtil, jwtProvider);
    }

    @Bean
    public LoginCustomerUseCase loginCustomerUseCase(LoginCustomerPort loginCustomerPort) {
        return new LoginCustomerService(loginCustomerPort);
    }

    @Bean
    public LoginCollaboratorPort loginCollaboratorPort(AuthenticationManager authenticationManager, JsonLogUtil jsonLogUtil, JwtProvider jwtProvider) {
        return new LoginCollaboratorAdapter(authenticationManager, jsonLogUtil, jwtProvider);
    }

    @Bean
    public LoginCollaboratorUseCase loginCollaboratorUseCase(LoginCollaboratorPort loginCollaboratorPort) {
        return new LoginCollaboratorService(loginCollaboratorPort);
    }

}
