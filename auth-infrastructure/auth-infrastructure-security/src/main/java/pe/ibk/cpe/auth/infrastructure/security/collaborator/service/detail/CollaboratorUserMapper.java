package pe.ibk.cpe.auth.infrastructure.security.collaborator.service.detail;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

public class CollaboratorUserMapper {

    public CollaboratorUserDetails map(UserEntity userEntity) {
        CollaboratorUserDetails customerUserDetails = new CollaboratorUserDetails();
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());

        return customerUserDetails;
    }

}
