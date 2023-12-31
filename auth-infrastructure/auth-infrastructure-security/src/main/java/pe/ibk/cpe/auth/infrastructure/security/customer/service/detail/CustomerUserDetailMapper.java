package pe.ibk.cpe.auth.infrastructure.security.customer.service.detail;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

import java.util.Collections;

public class CustomerUserDetailMapper {

    public CustomerUserDetails map(UserEntity userEntity) {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setUserId(userEntity.getId());
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());
        customerUserDetails.setRoles(Collections.singletonList("ROLE_ADMIN"));

        return customerUserDetails;
    }

}
