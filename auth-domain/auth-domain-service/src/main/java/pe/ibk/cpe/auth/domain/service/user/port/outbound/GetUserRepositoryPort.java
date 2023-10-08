package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;

import java.util.Optional;

public interface GetUserRepositoryPort {

    Optional<SystemUser> getByUsername(String username);

}
