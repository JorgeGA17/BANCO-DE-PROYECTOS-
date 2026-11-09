package com.sisgestion_back.sigestion_back.model.entity;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "eje")
@NoArgsConstructor
@AllArgsConstructor
public class Eje extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eje_pk")
    private Long ejePk;

    @Column(name = "x_nombre")
    private String xnombre;

    @Column(name = "x_resumen")
    private String xresumen;

}