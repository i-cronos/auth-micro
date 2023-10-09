package pe.ibk.cpe.auth.infrastructure.database.user.repository;

import org.springframework.stereotype.Component;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepository {

    public Optional<UserEntity> findByUsername(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID().toString());
        userEntity.setFirstName("Benito");
        userEntity.setLastName("Flores");
        userEntity.setUsername(username);
        userEntity.setPassword("$2a$10$MgLBcpJbl1l9fRwSXugvb.YC1xmnveecGOlyUnvvQu7N0HBXwb0My");
        return Optional.of(userEntity);
    }
}
