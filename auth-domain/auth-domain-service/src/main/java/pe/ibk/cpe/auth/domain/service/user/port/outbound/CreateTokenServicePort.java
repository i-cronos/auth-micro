package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.core.user.entity.User;

public interface CreateTokenServicePort {
    String apply(User systemUser);
}
