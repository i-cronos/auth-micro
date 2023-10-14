package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.dto.CollaboratorLoginResponse;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;
import pe.ibk.cpe.dependencies.infrastructure.security.token.TokenCreationService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CollaboratorAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenCreationService tokenCreationService;
    private final JsonUtil jsonUtil;

    public CollaboratorAuthenticationSuccessHandler(TokenCreationService tokenCreationService, JsonUtil jsonUtil) {
        this.tokenCreationService = tokenCreationService;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CollaboratorAuthenticationSuccessHandler");
        CollaboratorLoginResponse collaboratorLoginResponse = new CollaboratorLoginResponse();
        collaboratorLoginResponse.setToken(UUID.randomUUID().toString());

        String json = jsonUtil.toJson(collaboratorLoginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
