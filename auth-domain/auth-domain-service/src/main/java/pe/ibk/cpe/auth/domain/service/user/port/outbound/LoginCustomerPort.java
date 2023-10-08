package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;

public interface LoginCustomerPort {
    LoginCustomerResponse login(LoginCustomerRequest loginCustomerRequest);
}
