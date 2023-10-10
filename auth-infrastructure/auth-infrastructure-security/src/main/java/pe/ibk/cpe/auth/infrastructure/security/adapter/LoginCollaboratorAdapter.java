package pe.ibk.cpe.auth.infrastructure.security.adapter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorResponse;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.LoginCollaboratorPort;
import pe.ibk.cpe.auth.infrastructure.security.filter.authentication.CollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.dependencies.common.jwt.JwtProvider;
import pe.ibk.cpe.dependencies.common.util.CoreJsonUtil;

@Slf4j
@AllArgsConstructor
public class LoginCollaboratorAdapter implements LoginCollaboratorPort {

    private final AuthenticationManager authenticationManager;
    private final CoreJsonUtil coreJsonUtil;
    private final JwtProvider jwtProvider;

    @Override
    public LoginCollaboratorResponse login(LoginCollaboratorRequest loginCollaboratorRequest) {
        CollaboratorUsernamePasswordAuthenticationToken request = new CollaboratorUsernamePasswordAuthenticationToken(loginCollaboratorRequest.getUsername(), loginCollaboratorRequest.getPassword());
        log.info("Login collaborator request : {}", coreJsonUtil.toJson(request));

        CollaboratorUsernamePasswordAuthenticationToken response = (CollaboratorUsernamePasswordAuthenticationToken) authenticationManager.authenticate(request);
        log.info("Login collaborator response : {}", coreJsonUtil.toJson(response));

        return LoginCollaboratorResponse.builder()
                .token(jwtProvider.token(null))
                .build();
    }
}
