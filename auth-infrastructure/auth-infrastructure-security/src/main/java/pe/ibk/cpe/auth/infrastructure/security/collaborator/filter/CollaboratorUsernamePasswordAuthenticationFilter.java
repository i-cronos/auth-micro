package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter;

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
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication.RequestCollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.dto.CustomerLoginRequest;
import pe.ibk.cpe.dependencies.common.util.JsonUtil;

@Slf4j
public class CollaboratorUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final JsonUtil jsonUtil;

    public CollaboratorUsernamePasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher,
                                                            AuthenticationManager authenticationManager,
                                                            JsonUtil jsonUtil) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
        this.jsonUtil = jsonUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Collaborator auth Filter ....");
        RequestCollaboratorUsernamePasswordAuthenticationToken authenticationRequest = getAuthenticationRequest(request);

        this.setDetails(request, authenticationRequest);

        Authentication authenticationResponse = this.getAuthenticationManager().authenticate(authenticationRequest);

        log.info("authenticationResponse : {}", authenticationResponse.getPrincipal());
        return authenticationResponse;

    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private RequestCollaboratorUsernamePasswordAuthenticationToken getAuthenticationRequest(HttpServletRequest request) {
        try {
            CustomerLoginRequest customerLoginRequest = jsonUtil.fromInputStream(request.getInputStream(), CustomerLoginRequest.class);

            return new RequestCollaboratorUsernamePasswordAuthenticationToken(customerLoginRequest.getUsername(), customerLoginRequest.getPassword());
        } catch (Exception ex) {
            log.error("Not get request body");
            throw new UsernameNotFoundException("Cannot ready the request body");
        }
    }

}
