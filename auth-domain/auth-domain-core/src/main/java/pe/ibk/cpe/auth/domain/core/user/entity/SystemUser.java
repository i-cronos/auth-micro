package pe.ibk.cpe.auth.domain.core.user.entity;

import pe.ibk.cpe.auth.domain.core.user.valueobject.UserId;
import pe.ibk.cpe.dependencies.domain.entity.BaseEntity;

public class SystemUser extends BaseEntity<UserId> {
    private UserId id;
    private String fullName;
    private String username;
    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
