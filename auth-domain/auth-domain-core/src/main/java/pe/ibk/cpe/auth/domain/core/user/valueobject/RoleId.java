package pe.ibk.cpe.auth.domain.core.user.valueobject;

import pe.ibk.cpe.dependencies.domain.valuobject.BaseId;

import java.util.UUID;

public class RoleId extends BaseId<UUID> {

    public RoleId(UUID value) {
        super(value);
    }
}
