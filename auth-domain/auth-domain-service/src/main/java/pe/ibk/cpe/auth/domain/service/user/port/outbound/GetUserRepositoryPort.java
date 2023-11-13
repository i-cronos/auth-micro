package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.core.user.entity.User;

import java.util.Optional;

public interface GetUserRepositoryPort {

    Optional<User> getByUsername(String username);

}
