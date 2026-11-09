package com.sisgestion_back.sigestion_back.model.entity;


import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import com.sisgestion_back.sigestion_back.Enum.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_pk")
    private Long rolePk;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private ERole name;
}
