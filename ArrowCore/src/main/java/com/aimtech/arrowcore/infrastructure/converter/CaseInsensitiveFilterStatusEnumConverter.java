package com.aimtech.arrowcore.infrastructure.converter;

import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidEnumValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class CaseInsensitiveFilterStatusEnumConverter implements Converter<String, FilterStatusEnum> {
    private MessageSource messageSource;

    @Override
    public FilterStatusEnum convert(String source) {
        try {
            return FilterStatusEnum.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.InvalidEnumValueException",
                            new Object[]{"status"},
                            LocaleContextHolder.getLocale()
                    )
            );
        }
    }
}