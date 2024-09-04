package com.aimtech.arrowcore.domain.business.usecases.user_module;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.mappers.UserMapper;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.BusinessGroupRepository;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final BusinessGroupRepository businessGroupRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserRegisterResponse execute(UserRegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    String.format("Username with e-mail: '%s', already exists", dto.getUsername())
            );
        }
        User user = userMapper.toUser(dto);
        Profile profile = profileRepository.findById(2L).orElseThrow(
                () -> new RuntimeException("Entity not found")
        );
        BusinessGroup businessGroup = businessGroupRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("Entity not found")
        );

        user.setExternalId(IdGenerator.generateExternalId());
        user.setPassword(passwordEncoder.encode("123456789"));

        user.setProfile(profile);
        user.setBusinessGroup(businessGroup);
        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }
}
