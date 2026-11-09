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
@Table(name = "cargo")
@NoArgsConstructor
@AllArgsConstructor
public class Cargo extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargo_pk")
    private Long cargoPk;

    @Column(name = "x_nombre")
    private String xnombre;

    @OneToMany (mappedBy = "cargofk", cascade = CascadeType.ALL)
    private List<Miembro> miembros= new ArrayList<>();
}
