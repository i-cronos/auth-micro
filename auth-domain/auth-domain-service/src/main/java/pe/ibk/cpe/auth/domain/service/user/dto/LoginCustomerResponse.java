package pe.ibk.cpe.auth.domain.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCustomerResponse {
    private String token;
}
