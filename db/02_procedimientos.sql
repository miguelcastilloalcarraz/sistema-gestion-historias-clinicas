-- =============================================================
-- Procedimientos almacenados
-- =============================================================
USE hc_postas;

-- -------------------------------------------------------------
-- Procedimiento 1: Registrar un nuevo paciente con su historia clinica
-- -------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_registrar_paciente;
DELIMITER $$
CREATE PROCEDURE sp_registrar_paciente(
    IN p_dniPaciente     INT,
    IN p_nombre          VARCHAR(100),
    IN p_apellido        VARCHAR(100),
    IN p_fechaNacimiento DATE,
    IN p_sexo            CHAR(1),
    IN p_grupoSanguineo  VARCHAR(10),
    IN p_telefono        INT,
    IN p_tipoSeguro      VARCHAR(20),
    IN p_direccion       VARCHAR(150),
    IN p_talla           DECIMAL(4,2),
    IN p_peso            DECIMAL(5,2)
)
BEGIN
    INSERT INTO paciente(
        dniPaciente, nombre, apellido, fechaNacimiento, sexo,
        grupoSanguineo, telefono, tipoSeguro, direccion, talla, peso
    )
    VALUES(
        p_dniPaciente, p_nombre, p_apellido, p_fechaNacimiento, p_sexo,
        p_grupoSanguineo, p_telefono, p_tipoSeguro, p_direccion, p_talla, p_peso
    );

    INSERT INTO historia_clinica(dniPaciente)
    VALUES(p_dniPaciente);
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- Procedimiento 2: Registrar una consulta médica
-- -------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_registrar_consulta;
DELIMITER $$
CREATE PROCEDURE sp_registrar_consulta(
    IN p_dniPaciente    INT,
    IN p_motivoConsulta VARCHAR(255),
    IN p_especialidad   VARCHAR(50),
    IN p_colMedico      INT,
    IN p_diagnostico    VARCHAR(255),
    IN p_tratamiento    VARCHAR(255),
    IN p_fechaCons      DATE,
    IN p_hora           TIME
)
BEGIN
    DECLARE v_idHC INT;

    SELECT idHC INTO v_idHC
    FROM historia_clinica
    WHERE dniPaciente = p_dniPaciente;

    INSERT INTO consulta_medica(
        idHC, motivoConsulta, especialidad, colMedico,
        diagnostico, tratamiento, fechaCons, hora
    )
    VALUES(
        v_idHC, p_motivoConsulta, p_especialidad, p_colMedico,
        p_diagnostico, p_tratamiento, p_fechaCons, p_hora
    );
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- Procedimiento 3: Registrar una referencia hospitalaria
-- -------------------------------------------------------------
DROP PROCEDURE IF EXISTS sp_registrar_referencia;
DELIMITER $$
CREATE PROCEDURE sp_registrar_referencia(
    IN p_dniPaciente   INT,
    IN p_hospitalRef   VARCHAR(100),
    IN p_colMedico     INT,
    IN p_fechaCons     DATE,
    IN p_observaciones VARCHAR(255),
    IN p_estado        VARCHAR(20)
)
BEGIN
    DECLARE v_idHC INT;

    SELECT idHC INTO v_idHC
    FROM historia_clinica
    WHERE dniPaciente = p_dniPaciente;

    INSERT INTO referencia_hospitalaria(
        idHC, hospitalRef, colMedico, fechaCons, observaciones, estadoReferencia
    )
    VALUES(
        v_idHC, p_hospitalRef, p_colMedico, p_fechaCons, p_observaciones, p_estado
    );
END$$
DELIMITER ;
