-- =============================================================
-- Disparadores (triggers)
-- =============================================================
USE hc_postas;

-- -------------------------------------------------------------
-- TRIGGER 1: Al registrar una nueva consulta, registra
-- automáticamente la fecha de última atención en historia_clinica.
-- -------------------------------------------------------------
DROP TRIGGER IF EXISTS trg_after_insert_consulta;
DELIMITER $$
CREATE TRIGGER trg_after_insert_consulta
AFTER INSERT ON consulta_medica
FOR EACH ROW
BEGIN
    UPDATE historia_clinica
    SET fechaUltimaAtencion = NEW.fechaCons
    WHERE idHC = NEW.idHC;
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- TRIGGER 2: Antes de eliminar un paciente, verifica que no
-- tenga consultas registradas. Si tiene, impide la eliminación.
-- -------------------------------------------------------------
DROP TRIGGER IF EXISTS trg_before_delete_paciente;
DELIMITER $$
CREATE TRIGGER trg_before_delete_paciente
BEFORE DELETE ON paciente
FOR EACH ROW
BEGIN
    DECLARE total_consultas INT;

    SELECT COUNT(*) INTO total_consultas
    FROM consulta_medica c
    INNER JOIN historia_clinica hc ON c.idHC = hc.idHC
    WHERE hc.dniPaciente = OLD.dniPaciente;

    IF total_consultas > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede eliminar: el paciente tiene consultas registradas.';
    END IF;
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- TRIGGER 3: Al registrar un paciente, valida que el peso y la
-- talla sean positivos (el IMC calculado no puede ser cero ni
-- negativo).
-- -------------------------------------------------------------
DROP TRIGGER IF EXISTS trg_before_insert_paciente;
DELIMITER $$
CREATE TRIGGER trg_before_insert_paciente
BEFORE INSERT ON paciente
FOR EACH ROW
BEGIN
    IF NEW.talla <= 0 OR NEW.peso <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Peso y talla deben ser valores positivos.';
    END IF;
END$$
DELIMITER ;
