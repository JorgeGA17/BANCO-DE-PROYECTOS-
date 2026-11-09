-- =============================================================================
-- 1. TABLA: users  -->  aud_user
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_user()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_user (
            user_pk, x_correo_institucional, password,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.user_pk, NEW.x_correo_institucional, NEW.password,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_user (
            user_pk, x_correo_institucional, password,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.user_pk, NEW.x_correo_institucional, NEW.password,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_user (
            user_pk, x_correo_institucional, password,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.user_pk, OLD.x_correo_institucional, OLD.password,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_user ON users;
CREATE TRIGGER trg_aud_user
AFTER INSERT OR UPDATE OR DELETE ON users
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_user();


-- =============================================================================
-- 2. TABLA: personal  -->  aud_personal
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_personal()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_personal (
            personal_pk, x_tipo_documento, n_numero_documento, x_nombre_completo, x_nombre, x_apellido, x_ocupacion, x_correo_institucional,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.personal_pk, NEW.x_tipo_documento, NEW.n_numero_documento, NEW.x_nombre_completo, NEW.x_nombre, NEW.x_apellido, NEW.x_ocupacion, NEW.x_correo_institucional,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_personal (
            personal_pk, x_tipo_documento, n_numero_documento, x_nombre_completo, x_nombre, x_apellido, x_ocupacion, x_correo_institucional,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.personal_pk, NEW.x_tipo_documento, NEW.n_numero_documento, NEW.x_nombre_completo, NEW.x_nombre, NEW.x_apellido, NEW.x_ocupacion, NEW.x_correo_institucional,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_personal (
            personal_pk, x_tipo_documento, n_numero_documento, x_nombre_completo, x_nombre, x_apellido, x_ocupacion, x_correo_institucional,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.personal_pk, OLD.x_tipo_documento, OLD.n_numero_documento, OLD.x_nombre_completo, OLD.x_nombre, OLD.x_apellido, OLD.x_ocupacion, OLD.x_correo_institucional,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_personal ON personal;
CREATE TRIGGER trg_aud_personal
AFTER INSERT OR UPDATE OR DELETE ON personal
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_personal();


-- =============================================================================
-- 3. TABLA: proyecto  -->  aud_proyecto
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_proyecto()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_proyecto (
            proyecto_pk, x_nombre_proyecto, x_problematica, x_resumen, x_objetivo_general,
            x_innovacion, x_impacto, x_sostenibilidad, x_replicabilidad, n_peso, x_concepto_eval, x_descrip_eval,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.proyecto_pk, NEW.x_nombre_proyecto, NEW.x_problematica, NEW.x_resumen, NEW.x_objetivo_general,
            NEW.x_innovacion, NEW.x_impacto, NEW.x_sostenibilidad, NEW.x_replicabilidad, NEW.n_peso, NEW.x_concepto_eval, NEW.x_descrip_eval,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_proyecto (
            proyecto_pk, x_nombre_proyecto, x_problematica, x_resumen, x_objetivo_general,
            x_innovacion, x_impacto, x_sostenibilidad, x_replicabilidad, n_peso, x_concepto_eval, x_descrip_eval,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.proyecto_pk, NEW.x_nombre_proyecto, NEW.x_problematica, NEW.x_resumen, NEW.x_objetivo_general,
            NEW.x_innovacion, NEW.x_impacto, NEW.x_sostenibilidad, NEW.x_replicabilidad, NEW.n_peso, NEW.x_concepto_eval, NEW.x_descrip_eval,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_proyecto (
            proyecto_pk, x_nombre_proyecto, x_problematica, x_resumen, x_objetivo_general,
            x_innovacion, x_impacto, x_sostenibilidad, x_replicabilidad, n_peso, x_concepto_eval, x_descrip_eval,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.proyecto_pk, OLD.x_nombre_proyecto, OLD.x_problematica, OLD.x_resumen, OLD.x_objetivo_general,
            OLD.x_innovacion, OLD.x_impacto, OLD.x_sostenibilidad, OLD.x_replicabilidad, OLD.n_peso, OLD.x_concepto_eval, OLD.x_descrip_eval,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_proyecto ON proyecto;
CREATE TRIGGER trg_aud_proyecto
AFTER INSERT OR UPDATE OR DELETE ON proyecto
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_proyecto();


-- =============================================================================
-- 4. TABLA: corte  -->  aud_corte
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_corte()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_corte (
            corte_pk, x_nombre, x_nombre_corto,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.corte_pk, NEW.x_nombre, NEW.x_nombre_corto,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_corte (
            corte_pk, x_nombre, x_nombre_corto,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.corte_pk, NEW.x_nombre, NEW.x_nombre_corto,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_corte (
            corte_pk, x_nombre, x_nombre_corto,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.corte_pk, OLD.x_nombre, OLD.x_nombre_corto,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_corte ON corte;
CREATE TRIGGER trg_aud_corte
AFTER INSERT OR UPDATE OR DELETE ON corte
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_corte();


-- =============================================================================
-- 5. TABLA: roles  -->  aud_role
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_role()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_role (
            role_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.role_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_role (
            role_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.role_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_role (
            role_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.role_pk,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_role ON roles;
CREATE TRIGGER trg_aud_role
AFTER INSERT OR UPDATE OR DELETE ON roles
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_role();


-- =============================================================================
-- 6. TABLA: eje  -->  aud_eje
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_eje()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_eje (
            eje_pk, x_nombre, x_resumen,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.eje_pk, NEW.x_nombre, NEW.x_resumen,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_eje (
            eje_pk, x_nombre, x_resumen,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.eje_pk, NEW.x_nombre, NEW.x_resumen,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_eje (
            eje_pk, x_nombre, x_resumen,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.eje_pk, OLD.x_nombre, OLD.x_resumen,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_eje ON eje;
CREATE TRIGGER trg_aud_eje
AFTER INSERT OR UPDATE OR DELETE ON eje
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_eje();


-- =============================================================================
-- 7. TABLA: etiqueta  -->  aud_etiqueta
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_etiqueta()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_etiqueta (
            etiqueta_pk, x_nombre, x_campo,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.etiqueta_pk, NEW.x_nombre, NEW.x_campo,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_etiqueta (
            etiqueta_pk, x_nombre, x_campo,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.etiqueta_pk, NEW.x_nombre, NEW.x_campo,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_etiqueta (
            etiqueta_pk, x_nombre, x_campo,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.etiqueta_pk, OLD.x_nombre, OLD.x_campo,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_etiqueta ON etiqueta;
CREATE TRIGGER trg_aud_etiqueta
AFTER INSERT OR UPDATE OR DELETE ON etiqueta
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_etiqueta();


-- =============================================================================
-- 8. TABLA: jerarquia  -->  aud_jerarquia
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_jerarquia()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_jerarquia (
            jerarquia_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.jerarquia_pk, NEW.x_nombre,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_jerarquia (
            jerarquia_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.jerarquia_pk, NEW.x_nombre,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_jerarquia (
            jerarquia_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.jerarquia_pk, OLD.x_nombre,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_jerarquia ON jerarquia;
CREATE TRIGGER trg_aud_jerarquia
AFTER INSERT OR UPDATE OR DELETE ON jerarquia
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_jerarquia();


-- =============================================================================
-- 9. TABLA: especialidad  -->  aud_especialidad
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_especialidad()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_especialidad (
            especialidad_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.especialidad_pk, NEW.x_nombre,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_especialidad (
            especialidad_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.especialidad_pk, NEW.x_nombre,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_especialidad (
            especialidad_pk, x_nombre,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.especialidad_pk, OLD.x_nombre,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_especialidad ON especialidad;
CREATE TRIGGER trg_aud_especialidad
AFTER INSERT OR UPDATE OR DELETE ON especialidad
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_especialidad();


-- =============================================================================
-- 10. TABLA: presidente  -->  aud_presidente
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_presidente()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_presidente (
            presidente_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.presidente_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_presidente (
            presidente_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.presidente_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_presidente (
            presidente_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.presidente_pk,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_presidente ON presidente;
CREATE TRIGGER trg_aud_presidente
AFTER INSERT OR UPDATE OR DELETE ON presidente
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_presidente();


-- =============================================================================
-- 11. TABLA: miembro  -->  aud_miembro
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_trg_aud_miembro()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO aud_miembro (
            miembro_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.miembro_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'I', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO aud_miembro (
            miembro_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            NEW.miembro_pk,
            NEW.f_aud, NEW.b_aud, NEW.c_aud_uid, NEW.c_aud_uidred, NEW.c_aud_pc, NEW.n_aud_ip, NEW.c_aud_mcaddr, NEW.b_activo,
            NOW(), 'U', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN NEW;

    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO aud_miembro (
            miembro_pk,
            f_aud, b_aud, c_aud_uid, c_aud_uidred, c_aud_pc, n_aud_ip, c_aud_mcaddr, b_activo,
            f_trns, b_trns, c_trns_uid, c_trns_pc, n_trns_ip, c_trns_mcaddr
        ) VALUES (
            OLD.miembro_pk,
            NOW(), 'D', OLD.c_aud_uid, OLD.c_aud_uidred, OLD.c_aud_pc, OLD.n_aud_ip, OLD.c_aud_mcaddr, FALSE,
            NOW(), 'D', SESSION_USER, COALESCE(inet_client_addr()::text, 'localhost'), COALESCE(inet_client_addr()::text, '127.0.0.1'), 'N/A'
        );
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_aud_miembro ON miembro;
CREATE TRIGGER trg_aud_miembro
AFTER INSERT OR UPDATE OR DELETE ON miembro
FOR EACH ROW EXECUTE FUNCTION fn_trg_aud_miembro();