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
@Table(name = "periodo")
@NoArgsConstructor
@AllArgsConstructor
public class Periodo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "periodo_pk")
    private Long periodoPk;

    @Column(name = "x_nombre")
    private String xnombre;

    @OneToMany (mappedBy = "periodofk", cascade = CascadeType.ALL)
    private List<Comision> comisiones= new ArrayList<>();

    @OneToMany (mappedBy = "periodofk", cascade = CascadeType.ALL)
    private List<Presidente> presidentes= new ArrayList<>();

}

