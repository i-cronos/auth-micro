package pe.ibk.cpe.auth.infrastructure.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
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
import pe.ibk.cpe.auth.infrastructure.security.filter.authentication.CustomerUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.filter.dto.CustomerLoginRequest;
import pe.ibk.cpe.dependencies.global.util.CoreJsonUtil;

import java.io.IOException;

@Slf4j
public class CustomerUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final CoreJsonUtil coreJsonUtil = new CoreJsonUtil();

    public CustomerUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public CustomerUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    public CustomerUsernamePasswordAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    public CustomerUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Customer Filter ....");
        super.doFilter(request, response, chain);
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
