package pe.ibk.cpe.auth.infrastructure.security.service;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

public class UserDetailMapper {

    CustomerUserDetails map(UserEntity userEntity) {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());

        return customerUserDetails;
    }
}
