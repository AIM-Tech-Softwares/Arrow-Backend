package com.aimtech.arrowcore.infrastructure.context;

public class TenantContext {
    private static final String DEFAULT_TENANT = "public";
    private static final ThreadLocal<String> currentTenant = ThreadLocal.withInitial(() -> DEFAULT_TENANT);

    public static void setCurrentTenant(String tenant) {
        if (tenant != null && !tenant.isEmpty()) {
            currentTenant.set(tenant);
        } else {
            currentTenant.set(DEFAULT_TENANT);
        }
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
