package pe.ibk.cpe.auth.application.rest.user.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCollaboratorResponse;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerRequest;
import pe.ibk.cpe.auth.domain.service.user.dto.LoginCustomerResponse;
import pe.ibk.cpe.auth.domain.service.user.port.inbound.LoginCollaboratorUseCase;

@Slf4j
@RestController
@RequestMapping("/api/auth/collaborator")
@AllArgsConstructor
public class CollaboratorLoginController {

    private final LoginCollaboratorUseCase loginCollaboratorUseCase;

    @PostMapping("/v1.0/login")
    public ResponseEntity<LoginCollaboratorResponse> login(@RequestBody LoginCollaboratorRequest loginCollaboratorRequest) {
        log.info("Login collaborator request : {}", loginCollaboratorRequest);
        LoginCollaboratorResponse loginCollaboratorResponse = loginCollaboratorUseCase.login(loginCollaboratorRequest);

        return ResponseEntity.ok(loginCollaboratorResponse);
    }
}
