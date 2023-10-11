package pe.ibk.cpe.auth.infrastructure.security.collaborator.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication.CollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetails;

@Slf4j
@AllArgsConstructor
public class CollaboratorAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService collaboratorUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CollaboratorAuthenticationProvider");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomerUserDetails customerUserDetails = (CustomerUserDetails) collaboratorUserDetailsService.loadUserByUsername(username);

        boolean isMatched = passwordEncoder.matches(password, customerUserDetails.getPassword());

        log.info("is matched : {}", isMatched);

        if (!isMatched)
            throw new BadCredentialsException("No valid password");

        return new CollaboratorUsernamePasswordAuthenticationToken(username, password, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CollaboratorUsernamePasswordAuthenticationToken.class);
    }
}
