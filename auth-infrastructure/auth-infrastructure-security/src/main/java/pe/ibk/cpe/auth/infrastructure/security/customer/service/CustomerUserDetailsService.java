package pe.ibk.cpe.auth.infrastructure.security.customer.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetails;
import pe.ibk.cpe.auth.infrastructure.security.customer.service.detail.CustomerUserDetailMapper;

import java.util.Optional;

@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerUserDetailMapper userDetailMapper;

    @Override
    public CustomerUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);
        if (optionalUserEntity.isPresent()) {
            UserEntity userEntity = optionalUserEntity.get();
            return userDetailMapper.map(userEntity);
        }

        throw new UsernameNotFoundException("Not found customer user : " + username);
    }

}
