package pe.ibk.cpe.auth.infrastructure.security.collaborator.filter.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class ResponseCollaboratorUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String userId;

    public ResponseCollaboratorUsernamePasswordAuthenticationToken(String userId, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
