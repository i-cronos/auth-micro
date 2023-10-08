package pe.ibk.cpe.auth.infrastructure.database.user.repository;

import org.springframework.stereotype.Component;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

import java.util.Optional;

@Component
public class UserRepository {

    public Optional<UserEntity> findByUsername(String username) {
        UserEntity userEntity = new UserEntity();
        return Optional.empty();
    }
}
