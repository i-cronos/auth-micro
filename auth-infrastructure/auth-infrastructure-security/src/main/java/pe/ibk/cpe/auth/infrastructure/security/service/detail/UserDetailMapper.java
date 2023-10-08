package pe.ibk.cpe.auth.infrastructure.security.service.detail;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.CustomerUserDetails;

public class UserDetailMapper {

    public CustomerUserDetails mapToCustomer(UserEntity userEntity) {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());

        return customerUserDetails;
    }

    public CollaboratorUserDetails mapToCollaborator(UserEntity userEntity) {
        CollaboratorUserDetails customerUserDetails = new CollaboratorUserDetails();
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());

        return customerUserDetails;
    }

}
