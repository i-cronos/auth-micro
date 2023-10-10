package pe.ibk.cpe.auth.infrastructure.security.filter.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.filter.dto.CustomerLoginResponse;
import pe.ibk.cpe.dependencies.global.util.CoreJsonUtil;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final CoreJsonUtil coreJsonUtil = new CoreJsonUtil();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomerAuthenticationSuccessHandler");
        CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
        customerLoginResponse.setToken(UUID.randomUUID().toString());

        String json = coreJsonUtil.toJson(customerLoginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}