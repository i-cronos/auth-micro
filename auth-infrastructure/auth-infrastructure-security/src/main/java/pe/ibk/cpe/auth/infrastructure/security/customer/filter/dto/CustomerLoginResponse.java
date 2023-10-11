package pe.ibk.cpe.auth.infrastructure.security.customer.filter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLoginResponse {
    private String token;
}
