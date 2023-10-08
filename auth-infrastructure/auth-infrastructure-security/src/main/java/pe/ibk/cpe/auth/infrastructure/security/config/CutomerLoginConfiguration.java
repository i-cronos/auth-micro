package pe.ibk.cpe.auth.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCustomerUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;
import pe.ibk.cpe.auth.domain.service.user.service.LoginCustomerService;
import pe.ibk.cpe.auth.domain.service.user.service.mapper.UserMapper;
import pe.ibk.cpe.auth.infrastructure.security.adapter.LoginCustomerAdapter;

@Configuration
public class CutomerLoginConfiguration {

    @Bean
    public LoginCustomerPort loginCustomerPort(AuthenticationManager authenticationManager) {
        return new LoginCustomerAdapter(authenticationManager);
    }

    @Bean
    public LoginCustomerUseCase loginCustomerUseCase(LoginCustomerPort loginCustomerPort) {
        return new LoginCustomerService(loginCustomerPort, new UserMapper());
    }

}
