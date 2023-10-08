package pe.ibk.cpe.auth.domain.core.user.valueobject;

import pe.ibk.cpe.dependencies.domain.valuobject.BaseId;

import java.util.UUID;

public class UserId extends BaseId<UUID> {
    public UserId(UUID value) {
        super(value);
    }
}
