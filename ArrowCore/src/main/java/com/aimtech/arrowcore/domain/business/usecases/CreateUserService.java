package com.aimtech.arrowcore.domain.business.usecases;

import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.mappers.UserMapper;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserRegisterResponse execute(UserRegisterRequest dto) {
        User user = userMapper.toUser(dto);

        return null;
    }
}
