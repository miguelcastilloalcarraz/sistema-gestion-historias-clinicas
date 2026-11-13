-- =============================================================
-- Funciones
-- =============================================================
USE hc_postas;

-- -------------------------------------------------------------
-- Función 1: Calcular la edad de un paciente
-- Ejemplo: SELECT fn_calcular_edad('2000-05-15');
-- -------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_calcular_edad;
DELIMITER $$
CREATE FUNCTION fn_calcular_edad(
    fecha_nacimiento DATE
)
RETURNS INT
DETERMINISTIC
BEGIN
    RETURN TIMESTAMPDIFF(
        YEAR,
        fecha_nacimiento,
        CURDATE()
    );
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- Función 2: Obtener el número de consultas de un paciente
-- Ejemplo: SELECT fn_total_consultas(45678912);
-- -------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_total_consultas;
DELIMITER $$
CREATE FUNCTION fn_total_consultas(
    p_dniPaciente INT
)
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE total INT;

    SELECT COUNT(*)
    INTO total
    FROM consulta_medica c
    INNER JOIN historia_clinica hc ON c.idHC = hc.idHC
    WHERE hc.dniPaciente = p_dniPaciente;

    RETURN total;
END$$
DELIMITER ;

-- -------------------------------------------------------------
-- Función 3: Obtener el índice de masa corporal de un paciente
-- Ejemplo: SELECT fn_calcular_imc(70, 1.75);
-- -------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_calcular_imc;
DELIMITER $$
CREATE FUNCTION fn_calcular_imc(
    p_peso  DECIMAL(5,2),
    p_talla DECIMAL(4,2)
)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    RETURN p_peso / (p_talla * p_talla);
END$$
DELIMITER ;
