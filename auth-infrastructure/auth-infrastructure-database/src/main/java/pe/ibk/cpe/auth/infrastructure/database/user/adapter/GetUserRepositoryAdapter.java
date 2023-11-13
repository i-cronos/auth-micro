package pe.ibk.cpe.auth.infrastructure.database.user.adapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pe.ibk.cpe.auth.domain.service.user.port.outbound.GetUserRepositoryPort;
import pe.ibk.cpe.auth.domain.core.user.entity.User;
import pe.ibk.cpe.auth.infrastructure.database.user.entity.UserEntity;
import pe.ibk.cpe.auth.infrastructure.database.user.mapper.UserEntityMapper;
import pe.ibk.cpe.auth.infrastructure.database.user.repository.UserRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class GetUserRepositoryAdapter implements GetUserRepositoryPort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> getByUsername(String username) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsername(username);

        if (optionalUserEntity.isPresent()) {
            User systemUser = userEntityMapper.map(optionalUserEntity.get());

            return Optional.of(systemUser);
        }

        return Optional.empty();

    }
}
