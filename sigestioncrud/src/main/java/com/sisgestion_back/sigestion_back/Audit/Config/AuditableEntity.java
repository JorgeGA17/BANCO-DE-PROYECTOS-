package com.sisgestion_back.sigestion_back.Audit.Config;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
// Indica que esta clase no es una entidad, pero sus atributos se mapearán en las entidades que la hereden.
@EntityListeners(AuditingEntityListener.class) // Usamos el listener de Spring Data JPA para @CreatedBy, etc.
public abstract class AuditableEntity {

    // Campos de control (según la tabla de negocio del instructivo)
    @Column(name = "f_aud")
    protected LocalDateTime fAud; // Fecha de operación (del servidor)

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


    // Métodos para establecer los campos de auditoría automáticamente en el INSERT
    @PrePersist
    public void onPrePersist() {
        this.fAud = LocalDateTime.now();
        this.bAud = "I"; // Operación de Inserción
        if (this.bActivo == null) {
            this.bActivo = true;
        }
        // cAudUid se llenará por Spring Data JPA si configuras AuditorAware
        // Los otros campos (cAudUidred, cAudPc, nAudIp, cAudMcaddr) deben llenarse desde el servicio
        // ya que requieren información del contexto HTTP.
    }

    // Métodos para establecer los campos de auditoría automáticamente en el UPDATE
    @PreUpdate
    public void onPreUpdate() {
        this.fAud = LocalDateTime.now();
        this.bAud = "U"; // Operación de Actualización
        // cAudUid se llenará por Spring Data JPA si configuras AuditorAware
        // Los otros campos (cAudUidred, cAudPc, nAudIp, cAudMcaddr) deben llenarse desde el servicio
    }
}