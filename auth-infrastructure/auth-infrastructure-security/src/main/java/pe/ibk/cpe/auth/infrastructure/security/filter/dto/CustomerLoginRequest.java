package pe.ibk.cpe.auth.infrastructure.security.filter.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerLoginRequest {
    private String username;
    private String password;
}
