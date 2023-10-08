package pe.ibk.cpe.auth.domain.service.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCollaboratorResponse {
    private String token;
}
