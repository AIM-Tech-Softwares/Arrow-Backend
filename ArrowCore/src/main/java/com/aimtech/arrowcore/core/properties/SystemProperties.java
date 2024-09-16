package com.aimtech.arrowcore.core.properties;

import com.aimtech.arrowcore.domain.enums.ParameterType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "arrowcore.system")
public class SystemProperties {

    private final List<Parameter> parameters = new ArrayList<>();

    @PostConstruct
    public void init() {
        System.out.println("Parâmetros carregados: " + parameters);
    }

    @Getter
    @Setter
    public static class Parameter {
        private String key;
        private String value;
        private ParameterType type;
    }

}
