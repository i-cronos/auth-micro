package pe.ibk.cpe.auth.infrastructure.security.customer.provider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.authentication.RequestCustomerUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.customer.filter.authentication.ResponseCustomerUsernamePasswordAuthenticationToken;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.CustomerUserDetailsService;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetails;

@Slf4j
@AllArgsConstructor
public class CustomerAuthenticationProvider implements AuthenticationProvider {
    private final CustomerUserDetailsService customerUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("CustomerAuthenticationProvider");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomerUserDetails customerUserDetails = customerUserDetailsService.loadUserByUsername(username);

        boolean isMatched = passwordEncoder.matches(password, customerUserDetails.getPassword());

        log.info("is matched : {}", isMatched);

        if (!isMatched)
            throw new BadCredentialsException("No valid password");

        return new ResponseCustomerUsernamePasswordAuthenticationToken(customerUserDetails.getUserId(), username, null, customerUserDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RequestCustomerUsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
