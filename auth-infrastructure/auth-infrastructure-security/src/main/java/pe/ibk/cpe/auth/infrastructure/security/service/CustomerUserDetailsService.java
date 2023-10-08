package pe.ibk.cpe.auth.infrastructure.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.CustomerUserDetails;
import pe.ibk.cpe.auth.infrastructure.security.service.detail.UserDetailMapper;

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
            return userDetailMapper.mapToCustomer(userEntity);
        }

        throw new UsernameNotFoundException("Not found customer user : " + username);
    }

}
