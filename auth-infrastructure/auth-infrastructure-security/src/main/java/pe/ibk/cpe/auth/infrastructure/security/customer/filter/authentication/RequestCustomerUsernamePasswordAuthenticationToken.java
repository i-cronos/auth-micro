package pe.ibk.cpe.auth.infrastructure.security.customer.filter.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


public class RequestCustomerUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public RequestCustomerUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials, null);
    }

}
