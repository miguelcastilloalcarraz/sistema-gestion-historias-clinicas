-- =============================================================
-- Base de datos del sistema de historias clinicas
-- Curso: Programación Orientada a Objetos - UTP
-- =============================================================

DROP DATABASE IF EXISTS hc_postas;
CREATE DATABASE hc_postas
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE hc_postas;

CREATE TABLE usuario (
    idUsuario   INT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    contrasena  VARCHAR(100) NOT NULL
);

CREATE TABLE medico (
    colMedico   INT PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    apellido    VARCHAR(100) NOT NULL,
    idUsuario   INT,
    CONSTRAINT fk_medico_usuario
        FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario)
);

CREATE TABLE paciente (
    dniPaciente     INT PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    apellido        VARCHAR(100) NOT NULL,
    fechaNacimiento DATE NOT NULL,
    sexo            CHAR(1),
    grupoSanguineo  VARCHAR(10),
    telefono        INT,
    tipoSeguro      VARCHAR(20),
    direccion       VARCHAR(150),
    talla           DECIMAL(4,2),
    peso            DECIMAL(5,2)
);

CREATE TABLE historia_clinica (
    idHC                 INT AUTO_INCREMENT PRIMARY KEY,
    dniPaciente          INT NOT NULL UNIQUE,
    alergias             VARCHAR(100),
    fechaUltimaAtencion  DATE,
    CONSTRAINT fk_hc_paciente
        FOREIGN KEY (dniPaciente) REFERENCES paciente(dniPaciente)
);

CREATE TABLE consulta_medica (
    idConsulta      INT AUTO_INCREMENT PRIMARY KEY,
    idHC            INT NOT NULL,
    motivoConsulta  VARCHAR(255) NOT NULL,
    especialidad    VARCHAR(50)  NOT NULL,
    colMedico       INT,
    diagnostico     VARCHAR(255) NOT NULL,
    tratamiento     VARCHAR(255),
    fechaCons       DATE NOT NULL,
    hora            TIME NOT NULL,
    CONSTRAINT fk_consulta_hc
        FOREIGN KEY (idHC) REFERENCES historia_clinica(idHC),
    CONSTRAINT fk_consulta_medico
        FOREIGN KEY (colMedico) REFERENCES medico(colMedico)
) AUTO_INCREMENT = 1001;

CREATE TABLE referencia_hospitalaria (
    idReferencia     INT AUTO_INCREMENT PRIMARY KEY,
    idHC             INT NOT NULL,
    estadoReferencia VARCHAR(20) NOT NULL DEFAULT 'Pendiente',
    hospitalRef      VARCHAR(100) NOT NULL,
    observaciones    VARCHAR(255),
    idConsulta       INT,
    colMedico        INT,
    fechaCons        DATE NOT NULL,
    CONSTRAINT fk_ref_hc
        FOREIGN KEY (idHC) REFERENCES historia_clinica(idHC),
    CONSTRAINT fk_ref_consulta
        FOREIGN KEY (idConsulta) REFERENCES consulta_medica(idConsulta),
    CONSTRAINT fk_ref_medico
        FOREIGN KEY (colMedico) REFERENCES medico(colMedico)
) AUTO_INCREMENT = 2001;
