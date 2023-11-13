package pe.ibk.cpe.auth.infrastructure.database.user.mapper;

import org.springframework.stereotype.Component;
import pe.ibk.cpe.auth.domain.core.user.entity.User;
import pe.ibk.cpe.auth.domain.core.user.valueobject.UserId;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

import java.util.UUID;

@Component
public class UserEntityMapper {

    public User map(UserEntity userEntity) {
        User systemUser = new User();
        systemUser.setId(new UserId(UUID.fromString(userEntity.getId())));
        systemUser.setFullName(userEntity.getFirstName() + " " + userEntity.getLastName());
        systemUser.setUsername(userEntity.getUsername());
        systemUser.setPassword(userEntity.getPassword());

        return systemUser;
    }
}
