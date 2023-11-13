package pe.ibk.cpe.auth.infrastructure.security.collaborator.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication.RequestCollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication.ResponseCollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.service.CollaboratorUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.service.detail.CollaboratorUserDetails;

@Slf4j
@AllArgsConstructor
public class CollaboratorAuthenticationProvider implements AuthenticationProvider {
    private final CollaboratorUserDetailsService collaboratorUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CollaboratorAuthenticationProvider");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CollaboratorUserDetails collaboratorUserDetails = (CollaboratorUserDetails) collaboratorUserDetailsService.loadUserByUsername(username);

        boolean isMatched = passwordEncoder.matches(password, collaboratorUserDetails.getPassword());

        log.info("is matched : {}", isMatched);

        if (!isMatched)
            throw new BadCredentialsException("No valid password");

        return new ResponseCollaboratorUsernamePasswordAuthenticationToken(collaboratorUserDetails.getUserId(), username, password, collaboratorUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RequestCollaboratorUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
