package pe.ibk.cpe.auth.infrastructure.security.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.ibk.cpe.auth.infrastructure.security.provider.authentication.CollaboratorUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.CustomerUserDetails;
import pe.ibk.cpe.dependencies.global.exception.DomainException;

@Slf4j
@AllArgsConstructor
public class CollaboratorAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService collaboratorUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            log.info("CollaboratorAuthenticationProvider");
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            CustomerUserDetails customerUserDetails = (CustomerUserDetails) collaboratorUserDetailsService.loadUserByUsername(username);

            boolean isMatched = passwordEncoder.matches(password, customerUserDetails.getPassword());

            log.info("is matched : {}", isMatched);

            if (!isMatched)
                throw new DomainException(DomainException.Error.builder()
                        .systemMessage("Not valid password")
                        .userMessage("Not valid")
                        .build());

            return new CollaboratorUsernamePasswordAuthenticationToken(username, password, null);

        } catch (UsernameNotFoundException ex) {
            throw new DomainException(DomainException.Error.builder()
                    .systemMessage(ex.getMessage())
                    .userMessage("Not valid")
                    .build());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CollaboratorUsernamePasswordAuthenticationToken.class);
    }
}
