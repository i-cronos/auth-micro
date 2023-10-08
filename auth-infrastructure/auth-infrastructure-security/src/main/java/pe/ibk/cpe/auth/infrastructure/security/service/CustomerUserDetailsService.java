package pe.ibk.cpe.auth.infrastructure.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.ibk.cpe.auth.domain.core.user.entity.SystemUser;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;

import java.util.Optional;

@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDetailMapper userDetailMapper;

    @Override
    public CustomerUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return userDetailMapper.map(userEntity);
        }

        throw new UsernameNotFoundException("Not found user");
    }

}
