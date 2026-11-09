package com.sisgestion_back.sigestion_back.model.entity;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "etiqueta")
@NoArgsConstructor
@AllArgsConstructor

public class Etiqueta extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etiqueta_pk")
    private Long etiquetaPk;

    @Column(name ="x_nombre")
    private String xnombre;

    @Column(name ="x_campo")
    private String xcampo;

}
