package pe.ibk.cpe.auth.domain.service.user.service;

import lombok.AllArgsConstructor;
import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCustomerUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCustomerPort;
import pe.ibk.cpe.auth.domain.service.user.service.mapper.UserMapper;

@AllArgsConstructor
public class LoginCustomerService implements LoginCustomerUseCase {
    private final LoginCustomerPort loginCustomerPort;
    private final UserMapper userMapper;

    @Override
    public LoginCustomerResponse login(LoginCustomerRequest loginCustomerRequest) {
        SystemUser request = userMapper.map(loginCustomerRequest);
        SystemUser response = loginCustomerPort.login(request);

        return userMapper.map(response);
    }
}
