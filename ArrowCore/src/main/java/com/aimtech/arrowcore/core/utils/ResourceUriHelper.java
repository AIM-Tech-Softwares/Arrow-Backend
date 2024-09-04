package com.aimtech.arrowcore.core.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@UtilityClass
public class ResourceUriHelper {
    public static void addUriInResponseHeader(Object resourceId, String pathParameter) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{" + pathParameter + "}")
                .buildAndExpand(resourceId).toUri();

        HttpServletResponse response = ((ServletRequestAttributes)
                Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();

        assert response != null;
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }

    public static void addUriInResponseHeader(Object resourceId) {
        addUriInResponseHeader(resourceId, "externalId");
    }
}
