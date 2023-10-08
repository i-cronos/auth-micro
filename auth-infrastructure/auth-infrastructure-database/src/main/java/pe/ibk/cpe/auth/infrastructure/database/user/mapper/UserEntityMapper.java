package pe.ibk.cpe.auth.infrastructure.database.user.mapper;

import org.springframework.stereotype.Component;
import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

@Component
public class UserEntityMapper {

    public SystemUser map(UserEntity userEntity) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(userEntity.getUsername());
        systemUser.setPassword(userEntity.getPassword());

        return systemUser;
    }
}
