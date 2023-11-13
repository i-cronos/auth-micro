package pe.ibk.cpe.auth.infrastructure.security.collaborator.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.collaborator.service.detail.CollaboratorUserMapper;

import java.util.Optional;

@AllArgsConstructor
public class CollaboratorUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CollaboratorUserMapper collaboratorUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return collaboratorUserMapper.map(userEntity);
        }

        throw new UsernameNotFoundException("Not found collaborator user : " + username);
    }
}
