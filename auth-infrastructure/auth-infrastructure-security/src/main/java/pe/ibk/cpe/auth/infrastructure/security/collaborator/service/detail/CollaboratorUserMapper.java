package pe.ibk.cpe.auth.infrastructure.security.collaborator.service.detail;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

import java.util.Collections;

public class CollaboratorUserMapper {

    public CollaboratorUserDetails map(UserEntity userEntity) {
        CollaboratorUserDetails collaboratorUserDetails = new CollaboratorUserDetails();
        collaboratorUserDetails.setUsername(userEntity.getUsername());
        collaboratorUserDetails.setPassword(userEntity.getPassword());
        collaboratorUserDetails.setRoles(Collections.singletonList("ADMIN"));

        return collaboratorUserDetails;
    }

}
