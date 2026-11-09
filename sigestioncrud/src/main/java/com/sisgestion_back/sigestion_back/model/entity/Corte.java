package com.sisgestion_back.sigestion_back.model.entity;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "corte")
@NoArgsConstructor
@AllArgsConstructor

public class Corte extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "corte_pk")
    private Long cortePk;

    @Column(name = "x_nombre")
    private String xnombre;

    @Column(name = "x_nombre_corto")
    private String xnombreCorto;


  @OneToMany (mappedBy = "cortefk", cascade = CascadeType.DETACH)
  private List<Proyecto> proyectos= new ArrayList<>();

  @OneToMany (mappedBy = "cortefk", cascade = CascadeType.DETACH)
  private List<Comision> comisiones= new ArrayList<>();

  @OneToMany (mappedBy = "cortefk", cascade = CascadeType.DETACH)
  private List<Presidente> presidentes= new ArrayList<>();
}