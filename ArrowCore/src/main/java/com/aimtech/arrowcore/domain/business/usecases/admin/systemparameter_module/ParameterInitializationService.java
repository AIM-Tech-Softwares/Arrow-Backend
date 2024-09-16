package com.aimtech.arrowcore.domain.business.usecases.admin.systemparameter_module;

import com.aimtech.arrowcore.core.properties.SystemProperties;
import com.aimtech.arrowcore.domain.entities.SystemParameter;
import com.aimtech.arrowcore.domain.repository.SystemParameterRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParameterInitializationService {

    private static final Logger log = LoggerFactory.getLogger(ParameterInitializationService.class);

    private final SystemProperties systemProperties;
    private final SystemParameterRepository systemParameterRepository;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void initializeParameters() {
        List<SystemProperties.Parameter> defaultParameters = systemProperties.getParameters();

        for (SystemProperties.Parameter defaultParam : defaultParameters) {
            if (!parameterExists(defaultParam.getKey())) {
                SystemParameter newParam = new SystemParameter();
                newParam.setKey(defaultParam.getKey());
                newParam.setValue(defaultParam.getValue());
                newParam.setType(defaultParam.getType());

                systemParameterRepository.save(newParam);
                log.info("Parameter {} inserted with default value.", defaultParam.getKey());
            }
        }
    }

    private boolean parameterExists(String key) {
        return systemParameterRepository.findByKey(key).isPresent();
    }
}
