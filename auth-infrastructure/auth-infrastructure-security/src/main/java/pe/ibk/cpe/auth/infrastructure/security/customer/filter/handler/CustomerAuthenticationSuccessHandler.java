package pe.ibk.cpe.auth.infrastructure.security.customer.filter.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.dto.CustomerLoginResponse;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;
import pe.ibk.cpe.dependencies.infrastructure.security.token.TokenCreationService;
import pe.ibk.cpe.dependencies.infrastructure.security.token.types.TokenType;

import java.io.IOException;

@Slf4j
public class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenCreationService tokenCreationService;
    private final JsonUtil jsonUtil;

    public CustomerAuthenticationSuccessHandler(TokenCreationService tokenCreationService, JsonUtil jsonUtil) {
        this.tokenCreationService = tokenCreationService;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomerAuthenticationSuccessHandler");

        TokenCreationService.TokenCreationResponse tokenCreationResponse = createToken(authentication);

        CustomerLoginResponse customerLoginResponse = new CustomerLoginResponse();
        customerLoginResponse.setToken(tokenCreationResponse.getToken());

        String json = jsonUtil.toJson(customerLoginResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private TokenCreationService.TokenCreationResponse createToken(Authentication authentication) {
        TokenCreationService.TokenCreationRequest tokenCreationRequest = TokenCreationService.TokenCreationRequest.builder()
                .tokenType(TokenType.CUSTOMER)
                .build();

        return tokenCreationService.create(tokenCreationRequest);
    }
}
