package pe.ibk.cpe.auth.domain.service.user.service;

import lombok.AllArgsConstructor;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCustomerUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;

@AllArgsConstructor
public class LoginCustomerService implements LoginCustomerUseCase {
    private final LoginCustomerPort loginCustomerPort;

    @Override
    public LoginCustomerResponse login(LoginCustomerRequest loginCustomerRequest) {
        return loginCustomerPort.login(loginCustomerRequest);
    }
}
