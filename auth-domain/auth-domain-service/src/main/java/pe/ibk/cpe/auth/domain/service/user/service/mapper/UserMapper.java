package pe.ibk.cpe.auth.domain.service.user.service.mapper;

import pe.ibk.cpe.auth.domain.core.user.entity.User;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;

public class UserMapper {

    public User map(LoginCustomerRequest loginCustomerRequest) {
        User systemUser = new User();
        systemUser.setUsername(loginCustomerRequest.getUsername());
        systemUser.setPassword(loginCustomerRequest.getPassword());

        return systemUser;
    }

    public LoginCustomerResponse map(User systemUser) {
        return LoginCustomerResponse.builder()
                .token("xyz")
                .build();
    }
}
