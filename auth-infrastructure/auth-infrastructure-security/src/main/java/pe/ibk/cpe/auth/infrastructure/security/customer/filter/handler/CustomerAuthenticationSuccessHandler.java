package pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.dto.CustomerLoginResponse;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JsonUtil jsonUtil;

    public CustomerAuthenticationSuccessHandler(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomerAuthenticationSuccessHandler");
        CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
        customerLoginResponse.setToken(UUID.randomUUID().toString());

        String json = jsonUtil.toJson(customerLoginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
