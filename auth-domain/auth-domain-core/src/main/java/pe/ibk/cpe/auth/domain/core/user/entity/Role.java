package pe.ibk.cpe.auth.domain.core.user.entity;

import pe.ibk.cpe.auth.domain.core.user.valueobject.RoleId;
import pe.ibk.cpe.dependencies.domain.valuobject.BaseId;

public class Role extends BaseId<RoleId> {
    private String name;

    public Role(RoleId value) {
        super(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
