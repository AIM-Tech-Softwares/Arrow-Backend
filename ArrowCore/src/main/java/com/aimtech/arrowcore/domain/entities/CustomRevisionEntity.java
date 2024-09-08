package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.domain.listners.CustomRevisionListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Getter
@Setter
@Entity
@Table(name = "tb_revision_entity")
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "ip_address")
    private String ipAddress;
}
