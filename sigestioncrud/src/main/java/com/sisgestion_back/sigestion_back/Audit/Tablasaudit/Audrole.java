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
@Table(name = "aud_role")
@NoArgsConstructor
@AllArgsConstructor
public class Audrole extends AuditableTrailEntity {


    @Column(name = "role_pk")
    private Long rolePk;

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
