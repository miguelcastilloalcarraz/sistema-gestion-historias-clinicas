# 🏥 Sistema de Gestión Digital de Historias Clínicas (`hc_postas`)

Aplicación de escritorio desarrollada en **Java (Swing)** bajo la arquitectura **Modelo-Vista-Controlador (MVC)** y conectada a una base de datos relacional **MySQL**, diseñada para optimizar el control de pacientes, apertura automática de historias clínicas, registro de consultas médicas, emisión de referencias interhospitalarias y generación de reportes clínicos en postas o centros de salud.

---

## 🚀 Características Principales

* **Autenticación y Control de Acceso:** Módulo de inicio de sesión (`Login`) validado contra base de datos para restringir el acceso al sistema.
* **Gestión Integral de Pacientes:** Registro, búsqueda por DNI, actualización y eliminación de pacientes con control antropométrico (talla, peso, grupo sanguíneo, tipo de seguro). Al registrar un paciente, se genera automáticamente su `historia_clinica`.
* **Consultas Médicas:** Registro de atenciones por especialidad, motivo, diagnóstico y tratamiento, vinculadas al historial clínico del paciente y al médico tratante (`colMedico`).
* **Referencias Hospitalarias:** Emisión y seguimiento de derivaciones hacia hospitales de mayor complejidad con generación automática de código de referencia.
* **Generación de Reportes:** Visualización consolidada de la ficha del paciente, historial de consultas y referencias emitidas.
* **Automatización e Integridad en MySQL:**
  * **Procedimientos Almacenados:** `sp_registrar_paciente`, `sp_registrar_consulta`, `sp_registrar_referencia`.
  * **Funciones:** `fn_calcular_edad`, `fn_calcular_imc`, `fn_total_consultas`.
  * **Disparadores (Triggers):** Actualización automática de la fecha de última atención, bloqueo de eliminación de pacientes con historial activo y validación de valores antropométricos positivos.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Java (JDK)
* **Interfaz Gráfica:** Java Swing (NetBeans GUI Builder / `CardLayout`)
* **Base de Datos:** MySQL (`utf8mb4_unicode_ci`)
* **Conectividad:** JDBC (`mysql-connector-j-8.4.0.jar`)
* **IDE:** Apache NetBeans

---

## 📂 Estructura del Proyecto

```text
├── db/
│   ├── 01_schema.sql              # Creación de la BD hc_postas y sus 6 tablas relacionales
│   ├── 02_procedimientos.sql      # Procedimientos almacenados para inserciones transaccionales
│   ├── 03_funciones.sql           # Funciones escalares (edad, IMC, conteo de consultas)
│   ├── 04_triggers.sql            # Disparadores de auditoría e integridad referencial
│   └── 05_datos_iniciales.sql     # Usuarios, médicos y registros clínicos de prueba
├── lib/
│   └── mysql-connector-j-8.4.0.jar
├── nbproject/                     # Archivos de configuración de NetBeans
└── src/
    ├── imagenes/                  # Íconos e ilustraciones de la interfaz
    ├── modelo/                    # Entidades POO, ConexionBD (Singleton) y lógica de datos
    └── vista/                     # Formularios (Login, PrincipalFrame) y paneles modulares

