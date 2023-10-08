package pe.ibk.cpe.auth.domain.service.user.service;

import lombok.AllArgsConstructor;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorResponse;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCollaboratorUseCase;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCollaboratorPort;

@AllArgsConstructor
public class LoginCollaboratorService implements LoginCollaboratorUseCase {
    private final LoginCollaboratorPort loginCollaboratorPort;

    @Override
    public LoginCollaboratorResponse login(LoginCollaboratorRequest loginCollaboratorRequest) {
        return loginCollaboratorPort.login(loginCollaboratorRequest);
    }
}
