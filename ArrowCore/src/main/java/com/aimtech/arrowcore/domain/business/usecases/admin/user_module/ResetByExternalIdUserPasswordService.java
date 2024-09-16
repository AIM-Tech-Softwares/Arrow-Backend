package com.aimtech.arrowcore.domain.business.usecases.admin.user_module;

import com.aimtech.arrowcore.core.utils.PasswordUtils;
import com.aimtech.arrowcore.domain.entities.User;
import com.aimtech.arrowcore.domain.repository.UserRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetByExternalIdUserPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final PasswordUtils passwordUtils;

    @Transactional
    public void execute(UUID externalUserId) {
        User user = userRepository.findByExternalId(externalUserId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"User", "externalId: " + externalUserId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        String newPassword = passwordUtils.generateRandomPassword(8);

        // #TODO: Implementar o envio da password
        System.out.println("++++++++++++++++++++++++++++");
        System.out.println(newPassword);
        System.out.println("++++++++++++++++++++++++++++");

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastPasswordChangeDate(new Date());
        userRepository.save(user);
    }
}
