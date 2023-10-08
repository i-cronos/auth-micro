package pe.ibk.cpe.auth.infrastructure.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.UserDetailMapper;

import java.util.Optional;

@AllArgsConstructor
public class CollaboratorUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDetailMapper userDetailMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return userDetailMapper.mapToCollaborator(userEntity);
        }

        throw new UsernameNotFoundException("Not found collaborator user : " + username);
    }
}
