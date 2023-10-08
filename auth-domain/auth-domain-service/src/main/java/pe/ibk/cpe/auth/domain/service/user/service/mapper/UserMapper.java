package pe.ibk.cpe.auth.domain.service.user.service.mapper;

import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;

public class UserMapper {

    public SystemUser map(LoginCustomerRequest loginCustomerRequest) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(loginCustomerRequest.getUsername());
        systemUser.setPassword(loginCustomerRequest.getPassword());

        return systemUser;
    }

    public LoginCustomerResponse map(SystemUser systemUser) {
        return LoginCustomerResponse.builder()
                .token("xyz")
                .build();
    }
}
