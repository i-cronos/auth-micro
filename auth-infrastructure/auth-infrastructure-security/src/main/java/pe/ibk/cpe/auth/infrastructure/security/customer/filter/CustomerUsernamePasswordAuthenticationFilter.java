package pe.ibk.cpe.auth.infrastructure.security.customer.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.authentication.CustomerUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.dto.CustomerLoginRequest;
import pe.ibk.cpe.dependencies.common.util.CoreJsonUtil;

@Slf4j
public class CustomerUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final CoreJsonUtil coreJsonUtil = new CoreJsonUtil();

    public CustomerUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                                        AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Customer auth Filter ....");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getAuthRequest(request);

        this.setDetails(request, usernamePasswordAuthenticationToken);

        Authentication authenticationResponse = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        log.info("authenticationResponse : {}", authenticationResponse.getPrincipal());
        return authenticationResponse;

    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
        try {
            CustomerLoginRequest customerLoginRequest = coreJsonUtil.fromInputStream(request.getInputStream(), CustomerLoginRequest.class);

            return new CustomerUsernamePasswordAuthenticationToken(customerLoginRequest.getUsername(), customerLoginRequest.getPassword());
        } catch (Exception ex) {
            log.error("Not get request body");
            throw new UsernameNotFoundException("Cannot ready the request body");
        }

    }

}
