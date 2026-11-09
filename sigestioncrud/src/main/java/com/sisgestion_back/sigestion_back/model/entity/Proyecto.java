package com.sisgestion_back.sigestion_back.model.entity;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "proyecto")
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proyecto_pk")
    private Long proyectoPk;

    @Column(name = "x_nombre_proyecto",length = 500)
    private String xnombreProyecto;

    @Column(name = "x_problematica", columnDefinition = "TEXT")
    private String xproblematica;

    @Column(name = "x_resumen", columnDefinition = "TEXT")
    private String xresumen;

    @Column(name = "x_objetivo_general", columnDefinition = "TEXT")
    private String xobjetivoGeneral;

    @Column(name = "x_innovacion")
    private Integer xinnovacion;

    @Column(name = "x_impacto")
    private Integer ximpacto;

    @Column(name = "x_sostenibilidad")
    private Integer xsostenibilidad;

    @Column(name = "x_replicabilidad")
    private Integer xreplicabilidad;

    @Column(name = "n_peso")
    private Integer npeso;

    @Column(name = "x_concepto_eval", columnDefinition = "TEXT")
    private String xconceptoEval;

    @Column(name = "x_descrip_eval", columnDefinition = "TEXT")
    private String xdescripEval;

    @ManyToOne
    @JoinColumn(name = "corte_fk")
    private Corte cortefk;

    @ManyToOne
    @JoinColumn(name = "estado_proyecto_fk")
    private Estado estadofk;

   @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(
            name = "especialidad_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_fk"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_fk")
    )
    private List<Especialidad> especialidades;

    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(
            name = "jerarquia_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_fk"),
            inverseJoinColumns = @JoinColumn(name = "jerarquia_fk")
    )
    private List<Jerarquia> jerarquias;


    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(
            name = "eje_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_fk"),
            inverseJoinColumns = @JoinColumn(name = "eje_fk")
    )
    private List<Eje> ejes;

    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(
            name = "etiqueta_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_fk"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_fk")
    )
    private List<Etiqueta> etiquetas;

    @ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinTable(
            name = "personal_proyectos",
            joinColumns = @JoinColumn(name = "proyecto_fk"),
            inverseJoinColumns = @JoinColumn(name = "personal_fk")
    )
    private List<Personal> personal;
}