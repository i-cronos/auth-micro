package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import pe.ibk.cpe.dependencies.common.exception.error.UserError;
import pe.ibk.cpe.dependencies.common.util.CoreJsonUtil;

import java.io.IOException;

@Slf4j
public class CollaboratorFailureAuthenticationSuccessHandler implements AuthenticationFailureHandler {
    private final CoreJsonUtil coreJsonUtil = new CoreJsonUtil();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("CollaboratorFailureAuthenticationSuccessHandler, error : {} ", exception.getMessage());

        UserError userError = UserError.builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .message("Not allowed")
                .errorCode("0000")
                .build();

        String json = coreJsonUtil.toJson(userError);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
