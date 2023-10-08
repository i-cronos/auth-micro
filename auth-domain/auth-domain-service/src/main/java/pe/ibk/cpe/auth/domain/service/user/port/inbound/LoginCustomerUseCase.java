package pe.ibk.cpe.auth.domain.service.user.port.inbound;

import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;

public interface LoginCustomerUseCase {

    LoginCustomerResponse login(LoginCustomerRequest loginCustomerRequest);
}
