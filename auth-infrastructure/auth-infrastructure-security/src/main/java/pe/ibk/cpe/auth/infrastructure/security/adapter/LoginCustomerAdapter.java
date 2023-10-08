package pe.ibk.cpe.auth.infrastructure.security.adapter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;
import pe.ibk.cpe.auth.infrastructure.security.provider.authentication.CustomerUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.dependencies.global.jwt.JwtProvider;
import pe.ibk.cpe.dependencies.global.util.CoreJsonUtil;

@Slf4j
@AllArgsConstructor
public class LoginCustomerAdapter implements LoginCustomerPort {
    private final AuthenticationManager authenticationManager;
    private final CoreJsonUtil coreJsonUtil;
    private final JwtProvider jwtProvider;

    @Override
    public LoginCustomerResponse login(LoginCustomerRequest loginCustomerRequest) {
        CustomerUsernamePasswordAuthenticationToken request = new CustomerUsernamePasswordAuthenticationToken(loginCustomerRequest.getUsername(), loginCustomerRequest.getPassword());
        log.info("Login customer request : {}", coreJsonUtil.toJson(request));

        CustomerUsernamePasswordAuthenticationToken response = (CustomerUsernamePasswordAuthenticationToken) authenticationManager.authenticate(request);
        log.info("Login customer response : {}", coreJsonUtil.toJson(response));

        return LoginCustomerResponse.builder()
                .token(jwtProvider.token(null))
                .build();
    }
}
