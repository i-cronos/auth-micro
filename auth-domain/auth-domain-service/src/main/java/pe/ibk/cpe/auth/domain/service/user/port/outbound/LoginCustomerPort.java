package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;

public interface LoginCustomerPort {
    SystemUser login(SystemUser systemUser);
}
