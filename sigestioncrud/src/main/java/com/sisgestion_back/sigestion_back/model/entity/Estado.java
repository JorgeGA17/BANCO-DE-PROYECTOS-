package com.sisgestion_back.sigestion_back.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "estado_proyectos")
@NoArgsConstructor
@AllArgsConstructor

public class Estado extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_proyecto_pk")
    private Long estadoPk;

    @Column(name = "x_nombre")
    private String xnombre;

    @Column(name ="x_resumen")
    private String xresumen;

    @OneToMany(mappedBy = "estadofk", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Proyecto> proyectos= new ArrayList<>();
}
