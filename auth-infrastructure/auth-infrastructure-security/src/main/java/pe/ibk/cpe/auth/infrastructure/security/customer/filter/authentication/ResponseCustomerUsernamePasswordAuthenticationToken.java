package pe.ibk.cpe.auth.infrastructure.security.customer.filter.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class ResponseCustomerUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String userId;

    public ResponseCustomerUsernamePasswordAuthenticationToken(String userId, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
