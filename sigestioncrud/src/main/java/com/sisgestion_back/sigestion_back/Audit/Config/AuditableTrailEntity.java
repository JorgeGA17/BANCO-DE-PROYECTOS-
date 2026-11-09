package com.sisgestion_back.sigestion_back.Audit.Config;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
// Indica que esta clase no es una entidad, pero sus atributos se mapearán en las entidades que la hereden.
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableTrailEntity {

    // PK de la tabla de auditoría
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_trns_id")
    protected Long nTrnsId; // Correlativo de registro (Identity)

    // Campos de trazabilidad (según la tabla de auditoría del instructivo)
    @Column(name = "f_trns")
    protected LocalDateTime fTrns; // Fecha de operación (del servidor)

    @Column(name = "b_trns", length = 1)
    protected String bTrns; // Tipo de operación (I=Insert, U=Update, D=Delete)

    @Column(name = "c_trns_uid", length = 30)
    protected String cTrnsUid; // Usuario de la Base de Datos

    @Column(name = "c_trns_pc", length = 30)
    protected String cTrnsPc; // Nombre de la PC

    @Column(name = "n_trns_ip", length = 15)
    protected String nTrnsIp; // Dirección IP

    @Column(name = "c_trns_mcaddr", length = 17)
    protected String cTrnsMcaddr; // Dirección Mac
}