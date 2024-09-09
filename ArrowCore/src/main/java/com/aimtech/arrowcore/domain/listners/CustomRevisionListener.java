package com.aimtech.arrowcore.domain.listners;

import com.aimtech.arrowcore.core.filters.IpAddressFilter;
import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.domain.entities.CustomRevisionEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements org.hibernate.envers.RevisionListener {

    private String getCurrentUsername() {
        return AuthUtils.getUsername();
    }

    private String getCurrentIpAddress() {
        System.out.println(IpAddressFilter.getClientIpAddress());
        return IpAddressFilter.getClientIpAddress();
    }

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;

        customRevisionEntity.setUsername(getCurrentUsername());
        customRevisionEntity.setIpAddress(getCurrentIpAddress());
    }
}
