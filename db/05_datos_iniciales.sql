-- =============================================================
-- Datos iniciales de prueba
-- =============================================================
USE hc_postas;

-- -------------------------------------------------------------
-- Usuarios del sistema
-- -------------------------------------------------------------
INSERT INTO usuario (username, contrasena) VALUES
    ('admin',  'admin123'),
    ('medico', 'medico123');

-- -------------------------------------------------------------
-- Médicos
-- -------------------------------------------------------------
INSERT INTO medico (colMedico, nombre, apellido, idUsuario) VALUES
    (87687, 'Juan Carlos', 'Ramírez Soto', 2),
    (97543, 'María Elena', 'Torres Vega', NULL);

-- -------------------------------------------------------------
-- Paciente de prueba
-- -------------------------------------------------------------
CALL sp_registrar_paciente(45678912, 'Juan', 'Ramírez López', '1990-05-14',
    'M', 'O+', 987654321, 'SIS', 'Av. Los Próceres 456, San Isidro', 1.75, 72.50);

-- -------------------------------------------------------------
-- Consulta de prueba
-- -------------------------------------------------------------
CALL sp_registrar_consulta(45678912, 'Dolor abdominal recurrente',
    'Medicina General', 87687, 'Gastritis crónica',
    'Omeprazol 20mg cada 24h por 14 días', CURDATE(), '09:30:00');

-- -------------------------------------------------------------
-- Referencia de prueba
-- -------------------------------------------------------------
CALL sp_registrar_referencia(45678912, 'Hosp. Dos de Mayo', 87687,
    CURDATE(), 'Requiere evaluación por gastroenterología', 'Pendiente');
