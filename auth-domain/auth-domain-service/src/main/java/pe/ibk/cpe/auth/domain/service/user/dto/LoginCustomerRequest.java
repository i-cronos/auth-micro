package pe.ibk.cpe.auth.domain.service.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginCustomerRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
