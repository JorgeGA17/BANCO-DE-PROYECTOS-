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
@Table(name = "personal")
@NoArgsConstructor
@AllArgsConstructor
public class Personal extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_pk")
    private Long personalPk;

    @Column(name = "x_tipo_documento")
    private String xtipoDocumento;

    @Column(name = "n_numero_documento")
    private String nnumeroDocumento;

    @Column(name = "x_nombre_completo")
    private String xnombreCompleto;

    @Column(name = "x_nombre")
    private String xnombre;

    @Column(name = "x_apellido")
    private String xapellido;

    @Column(name = "x_ocupacion")
    private String xocupacion;

    @Column(name = "x_correo_institucional", nullable = false, unique = true)
    private String xcorreoInstitucional;

    @OneToMany (mappedBy = "personalfk", cascade = CascadeType.ALL)
    private List<Presidente> presidentes= new ArrayList<>();

    @OneToMany (mappedBy = "personalfk", cascade = CascadeType.ALL)
    private List<Miembro> miembros= new ArrayList<>();

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_pk")
    private  User user;

}
