package pe.ibk.cpe.auth.domain.service.user.port.inbound;

import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorResponse;

public interface LoginCollaboratorUseCase {
    LoginCollaboratorResponse login(LoginCollaboratorRequest loginCollaboratorRequest);
}
