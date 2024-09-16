package com.aimtech.arrowcore.domain.business.usecases.admin.systemparameter_module;

import com.aimtech.arrowcore.domain.entities.SystemParameter;
import com.aimtech.arrowcore.domain.enums.ParameterType;
import com.aimtech.arrowcore.domain.repository.SystemParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSystemParameterService {
    private final SystemParameterRepository parameterRepository;
    private final MessageSource messageSource;

    public Object getParameterValue(String key) {
        SystemParameter parameter = parameterRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Parâmetro não encontrado: " + key));

        return convertParameterValue(parameter);
    }

    public Boolean getBooleanValue(String key) {
        SystemParameter parameter = parameterRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Parâmetro não encontrado: " + key));

        if (parameter.getType() != ParameterType.BOOLEAN) {
            throw new IllegalArgumentException("Tipo de parâmetro incorreto para " + key);
        }
        return Boolean.parseBoolean(parameter.getValue());
    }

    public Integer getNumberValue(String key) {
        SystemParameter parameter = parameterRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Parâmetro não encontrado: " + key));

        if (parameter.getType() != ParameterType.NUMBER) {
            throw new IllegalArgumentException("Tipo de parâmetro incorreto para " + key);
        }
        return Integer.parseInt(parameter.getValue());
    }

    private Object convertParameterValue(SystemParameter parameter) {
        return switch (parameter.getType()) {
            case BOOLEAN -> Boolean.parseBoolean(parameter.getValue());
            case NUMBER -> Integer.parseInt(parameter.getValue());
            default -> parameter.getValue();
        };
    }
}
