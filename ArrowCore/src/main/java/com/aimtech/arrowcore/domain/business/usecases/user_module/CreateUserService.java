package com.aimtech.arrowcore.domain.business.usecases.user_module;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.core.utils.IdGenerator;
import com.aimtech.arrowcore.core.utils.PasswordUtils;
import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.mappers.UserMapper;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.ProfileRepository;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import com.aimtech.arrowcore.infrastructure.exceptions.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthUtils authUtils;
    private final PasswordUtils passwordUtils;

    @Transactional
    public UserRegisterResponse execute(UserRegisterRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    String.format("Username with e-mail: '%s', already exists", dto.getUsername())
            );
        }
        User user = userMapper.toUser(dto);
        user.setProfiles(new HashSet<>());

        User currentUser = authUtils.getCurrentUser();
        user.setBusinessGroup(currentUser.getBusinessGroup());

        List<Profile> profileList = new ArrayList<>();
        for (Long profileId : dto.getProfileIds()) {
            Profile profile = profileRepository.findById(profileId).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            "Profile", "id", profileId.toString()
                    )
            );
            profileList.add(profile);
        }
        profileList.forEach(profile -> {
            user.getProfiles().add(profile);
        });

        String tempPassword = passwordUtils.generateRandomPassword(8);

        user.setExternalId(IdGenerator.generateExternalId());
        user.setPassword(passwordEncoder.encode(tempPassword));

        // # TODO: Implementar o envio de email.
        System.out.println("==========================================");
        System.out.println("Your temp password is: " + tempPassword);
        System.out.println("==========================================");

        User inserted = userRepository.save(user);

        return userMapper.toResponse(inserted);
    }
}
