package com.sisgestion_back.sigestion_back.Audit.Tablasaudit;

import com.sisgestion_back.sigestion_back.Audit.Config.AuditableTrailEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "aud_personal")
@NoArgsConstructor
@AllArgsConstructor

public class Audpersonal extends AuditableTrailEntity {

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


    //Datos de Control

    @Column(name = "f_aud")
    protected LocalDateTime fAud;

    @Column(name = "b_aud", length = 1)
    protected String bAud; // Tipo de operación (I=Insert, U=Update, D=Delete)

    @CreatedBy // Anotación de Spring Data JPA para el usuario que creó/modificó
    @Column(name = "c_aud_uid", length = 30)
    protected String cAudUid; // Usuario de la Base de Datos (del token o contexto de seguridad)

    @Column(name = "c_aud_uidred", length = 30)
    protected String cAudUidred; // Usuario de la PC o RED (Deberá ser proporcionado por la capa de negocio/frontend)

    @Column(name = "c_aud_pc", length = 30)
    protected String cAudPc; // Nombre de la PC (Deberá ser proporcionado por la capa de negocio/frontend)

    @Column(name = "n_aud_ip", length = 15)
    protected String nAudIp; // Dirección IP (Deberá ser proporcionado por la capa de negocio/frontend)

    @Column(name = "c_aud_mcaddr", length = 17)
    protected String cAudMcaddr; // Dirección Mac (Deberá ser proporcionado por la capa de negocio/frontend)

    // Nuevo campo para eliminación lógica
    @Column(name = "b_activo")
    protected Boolean bActivo = true; // Por defecto activo, para eliminación lógica

}
