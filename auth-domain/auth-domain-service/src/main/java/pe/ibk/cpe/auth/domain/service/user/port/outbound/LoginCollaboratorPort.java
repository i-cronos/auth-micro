package pe.ibk.cpe.auth.domain.service.user.port.outbound;

import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorResponse;

public interface LoginCollaboratorPort {
    LoginCollaboratorResponse login(LoginCollaboratorRequest loginCollaboratorRequest);
}
