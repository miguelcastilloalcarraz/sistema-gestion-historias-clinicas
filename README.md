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
⚙️ Instalación y EjecuciónClonar el repositorio:Bashgit clone [https://github.com/miguelcastilloalcarraz/sistema-gestion-historias-clinicas.git](https://github.com/miguelcastilloalcarraz/sistema-gestion-historias-clinicas.git)
Configurar la Base de Datos en MySQL:Inicia tu servidor MySQL (XAMPP, MySQL Workbench o Laragon).Ejecuta en estricto orden los scripts de la carpeta db/ para crear la base de datos hc_postas:01_schema.sql02_procedimientos.sql03_funciones.sql04_triggers.sql05_datos_iniciales.sqlVerificar la Conexión en Java:Abre el proyecto en Apache NetBeans.La clase src/modelo/ConexionBD.java viene preconfigurada para entorno local:URL: jdbc:mysql://localhost:3306/hc_postasUsuario: rootContraseña: "" (vacía por defecto; modifícala si tu servidor MySQL tiene clave).Ejecutar la Aplicación:Interfaz Gráfica completa: Haz clic derecho sobre src/vista/Login.java → Run File (Shift + F6).Prueba de modelo en consola (sin BD): Ejecuta src/modelo/ejecutor.java.🔑 Credenciales y Datos de PruebaAl ejecutar 05_datos_iniciales.sql, el sistema cuenta con los siguientes datos precargados para pruebas inmediatas:TipoDato / UsuarioCredencial / ValorAdministradoradminadmin123Médico (Col. 87687)medicomedico123Paciente de pruebaJuan Ramírez LópezDNI: 45678912🎥 Video DemostrativoVer video explicativo del proyecto👥 AutoresMiguel Angel Castillo AlcarrazNarda Milenka Briceño CastilloYadira Lucrecia Estañis LinaresAdemir Alfredo Fernandez HernandezJose Luis Haro TorresProyecto desarrollado para el curso de Programación Orientada a Objetos — Universidad Tecnológica del Perú (UTP), 2026.
