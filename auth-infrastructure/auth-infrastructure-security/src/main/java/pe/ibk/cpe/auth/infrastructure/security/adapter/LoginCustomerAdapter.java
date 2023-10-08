package pe.ibk.cpe.auth.infrastructure.security.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;
import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.infrastructure.security.provider.CustomerUsernamePasswordAuthenticationToken;

@Slf4j
@AllArgsConstructor
public class LoginCustomerAdapter implements LoginCustomerPort {
    private final AuthenticationManager authenticationManager;

    @Override
    public SystemUser login(SystemUser systemUser) {
        CustomerUsernamePasswordAuthenticationToken request = new CustomerUsernamePasswordAuthenticationToken(systemUser.getUsername(), systemUser.getPassword());
        try {
            log.info("Login request : {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CustomerUsernamePasswordAuthenticationToken response = (CustomerUsernamePasswordAuthenticationToken) authenticationManager.authenticate(request);
        try {
            log.info("Login response : {}", new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return systemUser;
    }
}
