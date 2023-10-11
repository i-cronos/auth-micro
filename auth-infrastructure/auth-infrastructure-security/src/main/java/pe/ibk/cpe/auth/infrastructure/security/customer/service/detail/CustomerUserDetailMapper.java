package pe.ibk.cpe.auth.infrastructure.security.customer.service.detail;

import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;

public class CustomerUserDetailMapper {

    public CustomerUserDetails map(UserEntity userEntity) {
        CustomerUserDetails customerUserDetails = new CustomerUserDetails();
        customerUserDetails.setUsername(userEntity.getUsername());
        customerUserDetails.setPassword(userEntity.getPassword());

        return customerUserDetails;
    }

}