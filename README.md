# Sistema de Gestión Digital de Historias Clínicas (`hc_postas`)

Aplicación de escritorio desarrollada en **Java (Swing)** bajo la arquitectura **Modelo-Vista-Controlador (MVC)** y conectada a una base de datos relacional **MySQL**, diseñada para optimizar el control de pacientes, apertura automática de historias clínicas, registro de consultas médicas, emisión de referencias interhospitalarias y generación de reportes clínicos en postas o centros de salud.

## Características Principales

* **Autenticación y Control de Acceso:** Módulo de inicio de sesión (`Login`) validado contra base de datos para restringir el acceso al personal médico y administrativo.
* **Gestión Integral de Pacientes:** Registro, búsqueda por DNI, actualización y eliminación de pacientes con control antropométrico (talla, peso, grupo sanguíneo, tipo de seguro), generando automáticamente su historia clínica.
* **Consultas Médicas:** Registro detallado de atenciones por especialidad, motivo, diagnóstico y tratamiento, vinculadas al historial clínico del paciente y al médico tratante (`colMedico`).
* **Referencias Hospitalarias:** Emisión y seguimiento de derivaciones hacia hospitales de mayor complejidad con generación automática de código de referencia.
* **Generación de Reportes:** Visualización consolidada de la ficha del paciente, historial de consultas y referencias emitidas.
* **Automatización en Base de Datos:** Integración de procedimientos almacenados (`sp_registrar_paciente`, `sp_registrar_consulta`, `sp_registrar_referencia`), funciones (`fn_calcular_edad`, `fn_calcular_imc`, `fn_total_consultas`) y disparadores (`triggers`) para auditoría e integridad referencial.

## Estructura del Proyecto

```text
ProyectoMVC/
├── db/
│   ├── 01_schema.sql              # Creación de la BD hc_postas y sus 6 tablas relacionales
│   ├── 02_procedimientos.sql      # Procedimientos almacenados para inserciones transaccionales
│   ├── 03_funciones.sql           # Funciones escalares (edad, IMC, conteo de consultas)
│   ├── 04_triggers.sql            # Disparadores de auditoría e integridad referencial
│   └── 05_datos_iniciales.sql     # Usuarios, médicos y registros clínicos de prueba
├── lib/
│   └── mysql-connector-j-8.4.0.jar # Driver JDBC de conexión a MySQL
├── nbproject/                     # Archivos de configuración de Apache NetBeans
└── src/
    ├── imagenes/                  # Íconos e ilustraciones de la interfaz gráfica
    ├── modelo/
    │   ├── ConexionBD.java        # Gestión de conexión JDBC (Patrón Singleton)
    │   ├── ConsultaMedica.java    # Entidad de atención médica
    │   ├── Datos.java             # Capa de acceso a datos y llamadas a procedimientos
    │   ├── HistorialClinico.java  # Entidad consolidada de historial y generación de reportes
    │   ├── Medico.java            # Entidad del personal médico
    │   ├── Paciente.java          # Entidad de datos personales y antropométricos
    │   ├── ReferenciaHospitalaria.java # Entidad de derivación interhospitalaria
    │   ├── Usuario.java           # Entidad de credenciales de acceso
    │   └── ejecutor.java          # Clase de prueba de instancias en consola
    └── vista/
        ├── Login.java             # Pantalla de autenticación
        ├── PrincipalFrame.java    # Dashboard principal con navegación modular (CardLayout)
        └── paneles/               # Paneles CRUD (Pacientes, Consultas, Referencias, Reportes)
```

## Tecnologías y Conceptos Aplicados

* **Lenguaje:** Java (JDK)
* **Interfaz Gráfica:** Java Swing (NetBeans GUI Builder y `CardLayout`)
* **Base de Datos:** MySQL (`utf8mb4_unicode_ci`) con Procedimientos Almacenados, Funciones y Triggers
* **Conectividad:** JDBC (`mysql-connector-j-8.4.0`)
* **Paradigma:** Programación Orientada a Objetos (Abstracción, Encapsulamiento, Modularidad y Patrón MVC)
* **Colecciones y Fechas:** `ArrayList`, `java.time.LocalDate` y `java.time.LocalTime`

## Cómo Ejecutar el Proyecto

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/miguelcastilloalcarraz/sistema-gestion-historias-clinicas.git
   ```
2. **Configurar la base de datos en MySQL:**
   * Inicia tu servidor MySQL (XAMPP, MySQL Workbench o Laragon).
   * Ejecuta en orden los scripts de la carpeta `db/` para crear la base de datos `hc_postas`: `01_schema.sql`, `02_procedimientos.sql`, `03_funciones.sql`, `04_triggers.sql` y `05_datos_iniciales.sql`.
3. **Abrir en tu IDE y verificar conexión:**
   * Abre la carpeta del proyecto en **Apache NetBeans**.
   * Verifica en `src/modelo/ConexionBD.java` que las credenciales coincidan con tu entorno local (por defecto usa usuario `root` y contraseña vacía `""` en el puerto `3306`).
4. **Ejecutar:**
   * Haz clic derecho sobre `src/vista/Login.java` y selecciona **Run File** (`Shift + F6`) para iniciar el sistema gráfico.
   * **Credenciales de prueba:** Usuario `admin` / Contraseña `admin123` (o usuario `medico` / Contraseña `medico123`).

## Autores

* **Miguel Angel Castillo Alcarraz** — [@miguelcastilloalcarraz](https://github.com/miguelcastilloalcarraz)
* Narda Milenka Briceño Castillo
* Yadira Lucrecia Estañis Linares
* Ademir Alfredo Fernandez Hernandez
* Jose Luis Haro Torres
