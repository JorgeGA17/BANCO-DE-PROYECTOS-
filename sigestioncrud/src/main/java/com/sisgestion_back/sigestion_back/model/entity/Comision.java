package com.sisgestion_back.sigestion_back.model.entity;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "comision")
@NoArgsConstructor
@AllArgsConstructor
public class Comision extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comision_pk", nullable = false)
    private Long comisionPk;

    @Column(name = "x_descripcion")
    private String xdescripcion;

    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "corte_fk")
    private Corte cortefk;

    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_fk")
    private Periodo periodofk;

    @OneToMany (mappedBy = "comisionfk", cascade = CascadeType.ALL)
    private List<Miembro> miembros;

}
