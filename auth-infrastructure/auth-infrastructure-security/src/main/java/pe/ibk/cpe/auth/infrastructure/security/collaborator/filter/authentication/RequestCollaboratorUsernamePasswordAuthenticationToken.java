package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class RequestCollaboratorUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public RequestCollaboratorUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
