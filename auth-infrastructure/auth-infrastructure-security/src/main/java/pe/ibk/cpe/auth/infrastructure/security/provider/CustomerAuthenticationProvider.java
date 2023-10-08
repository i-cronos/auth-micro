package pe.ibk.cpe.auth.infrastructure.security.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.infrastructure.security.service.CustomerUserDetails;
import pe.ibk.cpe.auth.infrastructure.security.service.CustomerUserDetailsService;

@Slf4j
@AllArgsConstructor
public class CustomerAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {


            log.info("Customer authentication provide");
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();
            log.info("paso 1");
            CustomerUserDetails customerUserDetails = (CustomerUserDetails) userDetailsService.loadUserByUsername(username);
            log.info("paso 2");
            boolean isMatched = passwordEncoder.matches(password, customerUserDetails.getPassword());
            log.info("is matched : {}", isMatched);
            return new CustomerUsernamePasswordAuthenticationToken(username, password, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CustomerUsernamePasswordAuthenticationToken.class);
    }
}
