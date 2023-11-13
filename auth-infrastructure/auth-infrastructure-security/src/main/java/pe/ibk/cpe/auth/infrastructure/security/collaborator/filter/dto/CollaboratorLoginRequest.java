package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboratorLoginRequest {
    private String username;
    private String password;
}
