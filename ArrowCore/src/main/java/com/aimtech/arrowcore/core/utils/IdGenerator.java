package com.aimtech.arrowcore.core.utils;

import java.util.UUID;

public class IdGenerator {

    public static String generateExternalId() {
        return UUID.randomUUID().toString();
    }
}
