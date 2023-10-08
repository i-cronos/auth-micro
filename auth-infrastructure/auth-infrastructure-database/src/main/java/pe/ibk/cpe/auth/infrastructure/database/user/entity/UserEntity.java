package pe.ibk.cpe.auth.infrastructure.database.user.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private String username;
    private String password;
}
