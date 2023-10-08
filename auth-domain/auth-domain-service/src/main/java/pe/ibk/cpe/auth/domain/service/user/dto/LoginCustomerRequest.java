package pe.ibk.cpe.auth.domain.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginCustomerRequest {
    private String username;
    private String password;
}
