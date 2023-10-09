package pe.ibk.cpe.auth.infrastructure.database.user.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEntity {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
