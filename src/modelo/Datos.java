package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Clase encargada de todas las operaciones con la base de datos
 */
public class Datos {

    private Datos() {
    }

    private static Connection conn() throws SQLException {
        return ConexionBD.getConnection();
    }

    //convierte los errores SQL en mensajes para mostrar al usuario
    private static RuntimeException errorBD(SQLException e) {
        //mensajes que lanzan los triggers
        if ("45000".equals(e.getSQLState())) {
            return new RuntimeException(e.getMessage());
        }
        return new RuntimeException("Error de base de datos: " + e.getMessage());
    }

    // ------------------------- Usuarios -------------------------

    //valida el usuario y contraseña, devuelve null si no coinciden
    public static Usuario autenticar(String username, String contrasena) {
        String sql = "SELECT idUsuario, username, contrasena FROM usuario WHERE username = ? AND contrasena = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getInt("idUsuario"),
                            rs.getString("username"), rs.getString("contrasena"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos. "
                    + "Verifique que MySQL esté iniciado y que la BD hc_postas exista. Detalle: " + e.getMessage());
        }
    }

    // ------------------------- Médicos -------------------------

    public static Medico buscarMedico(int colMedico) {
        String sql = "SELECT colMedico, nombre, apellido FROM medico WHERE colMedico = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, colMedico);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Medico(rs.getInt("colMedico"),
                            rs.getString("nombre"), rs.getString("apellido"), null);
                }
                return null;
            }
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    // ------------------------- Pacientes -------------------------

    private static Paciente mapPaciente(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getInt("dniPaciente"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getDate("fechaNacimiento").toLocalDate(),
                rs.getString("direccion"),
                rs.getString("sexo").charAt(0),
                rs.getInt("telefono"),
                rs.getString("grupoSanguineo"),
                rs.getString("tipoSeguro"),
                rs.getDouble("peso"),
                rs.getDouble("talla"));
    }

    public static Paciente buscarPaciente(int dni) {
        String sql = "SELECT * FROM paciente WHERE dniPaciente = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapPaciente(rs) : null;
            }
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    //registra al paciente junto con su historia clinica
    public static void registrarPaciente(Paciente p) {
        String sql = "{CALL sp_registrar_paciente(?,?,?,?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = conn().prepareCall(sql)) {
            cs.setInt(1, p.getDni());
            cs.setString(2, p.getNombres());
            cs.setString(3, p.getApellidos());
            cs.setDate(4, java.sql.Date.valueOf(p.getFacheNac()));
            cs.setString(5, String.valueOf(p.getGenero()));
            cs.setString(6, p.getGrupoSanguineo());
            cs.setInt(7, p.getTelefono());
            cs.setString(8, p.getTipoSeguro());
            cs.setString(9, p.getDireccion());
            cs.setDouble(10, p.getTalla());
            cs.setDouble(11, p.getPeso());
            cs.execute();
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    public static void actualizarPaciente(Paciente p) {
        String sql = "UPDATE paciente SET nombre=?, apellido=?, fechaNacimiento=?, sexo=?, "
                + "grupoSanguineo=?, telefono=?, tipoSeguro=?, direccion=?, talla=?, peso=? "
                + "WHERE dniPaciente=?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, p.getNombres());
            ps.setString(2, p.getApellidos());
            ps.setDate(3, java.sql.Date.valueOf(p.getFacheNac()));
            ps.setString(4, String.valueOf(p.getGenero()));
            ps.setString(5, p.getGrupoSanguineo());
            ps.setInt(6, p.getTelefono());
            ps.setString(7, p.getTipoSeguro());
            ps.setString(8, p.getDireccion());
            ps.setDouble(9, p.getTalla());
            ps.setDouble(10, p.getPeso());
            ps.setInt(11, p.getDni());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    //elimina al paciente junto con su historia clinica y referencias
    public static void eliminarPaciente(int dni) {
        try {
            //primero verifica que no tenga consultas registradas
            try (PreparedStatement ps = conn().prepareStatement("SELECT fn_total_consultas(?)")) {
                ps.setInt(1, dni);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new RuntimeException("No se puede eliminar: el paciente tiene consultas registradas.");
                    }
                }
            }
            Connection c = conn();
            c.setAutoCommit(false);
            try (Statement st = c.createStatement()) {
                st.executeUpdate("DELETE r FROM referencia_hospitalaria r "
                        + "JOIN historia_clinica hc ON r.idHC = hc.idHC WHERE hc.dniPaciente = " + dni);
                st.executeUpdate("DELETE FROM historia_clinica WHERE dniPaciente = " + dni);
                st.executeUpdate("DELETE FROM paciente WHERE dniPaciente = " + dni);
                c.commit();
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    // ------------------------- Consultas -------------------------

    public static ArrayList<ConsultaMedica> consultasDe(int dni) {
        String sql = "SELECT c.* FROM consulta_medica c "
                + "JOIN historia_clinica hc ON c.idHC = hc.idHC WHERE hc.dniPaciente = ?";
        ArrayList<ConsultaMedica> lista = new ArrayList<>();
        Paciente p = buscarPaciente(dni);
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ConsultaMedica(
                            rs.getInt("idConsulta"),
                            rs.getString("motivoConsulta"),
                            rs.getString("especialidad"),
                            rs.getString("diagnostico"),
                            rs.getString("tratamiento"),
                            rs.getDate("fechaCons").toLocalDate(),
                            rs.getTime("hora").toLocalTime(),
                            p,
                            buscarMedico(rs.getInt("colMedico"))));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    //registra la consulta y devuelve el id generado
    public static int registrarConsulta(ConsultaMedica c) {
        String sql = "{CALL sp_registrar_consulta(?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = conn().prepareCall(sql)) {
            cs.setInt(1, c.getPaciente().getDni());
            cs.setString(2, c.getMotivoConsulta());
            cs.setString(3, c.getEspecialidad());
            cs.setInt(4, c.getMedico().getColMedico());
            cs.setString(5, c.getDiagnostico());
            cs.setString(6, c.getTratamiento());
            cs.setDate(7, java.sql.Date.valueOf(c.getFecha()));
            cs.setTime(8, Time.valueOf(c.getHora()));
            cs.execute();
            return ultimoId();
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    // ------------------------- Referencias -------------------------

    private static ReferenciaHospitalaria mapReferencia(ResultSet rs, Paciente p) throws SQLException {
        return new ReferenciaHospitalaria(
                rs.getInt("idReferencia"),
                rs.getString("hospitalRef"),
                buscarMedico(rs.getInt("colMedico")),
                p,
                rs.getDate("fechaCons").toLocalDate(),
                rs.getString("observaciones"),
                rs.getString("estadoReferencia"));
    }

    public static ArrayList<ReferenciaHospitalaria> referenciasDe(int dni) {
        String sql = "SELECT r.* FROM referencia_hospitalaria r "
                + "JOIN historia_clinica hc ON r.idHC = hc.idHC WHERE hc.dniPaciente = ?";
        ArrayList<ReferenciaHospitalaria> lista = new ArrayList<>();
        Paciente p = buscarPaciente(dni);
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapReferencia(rs, p));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    public static ReferenciaHospitalaria buscarReferencia(int codigo) {
        String sql = "SELECT r.*, hc.dniPaciente FROM referencia_hospitalaria r "
                + "JOIN historia_clinica hc ON r.idHC = hc.idHC WHERE r.idReferencia = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapReferencia(rs, buscarPaciente(rs.getInt("dniPaciente")));
                }
                return null;
            }
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    //registra la referencia y devuelve su código (si llega 0 lo genera la BD)
    public static int registrarReferencia(ReferenciaHospitalaria r) {
        try {
            if (r.getCodReferencia() <= 0) {
                String sql = "{CALL sp_registrar_referencia(?,?,?,?,?,?)}";
                try (CallableStatement cs = conn().prepareCall(sql)) {
                    cs.setInt(1, r.getPaciente().getDni());
                    cs.setString(2, r.getHospitalRef());
                    cs.setInt(3, r.getMedico().getColMedico());
                    cs.setDate(4, java.sql.Date.valueOf(r.getFechaCons()));
                    cs.setString(5, r.getObservaciones());
                    cs.setString(6, r.getEstadoReferencia());
                    cs.execute();
                }
                return ultimoId();
            }
            String sql = "INSERT INTO referencia_hospitalaria "
                    + "(idReferencia, idHC, hospitalRef, colMedico, fechaCons, observaciones, estadoReferencia) "
                    + "SELECT ?, hc.idHC, ?, ?, ?, ?, ? FROM historia_clinica hc WHERE hc.dniPaciente = ?";
            try (PreparedStatement ps = conn().prepareStatement(sql)) {
                ps.setInt(1, r.getCodReferencia());
                ps.setString(2, r.getHospitalRef());
                ps.setInt(3, r.getMedico().getColMedico());
                ps.setDate(4, java.sql.Date.valueOf(r.getFechaCons()));
                ps.setString(5, r.getObservaciones());
                ps.setString(6, r.getEstadoReferencia());
                ps.setInt(7, r.getPaciente().getDni());
                ps.executeUpdate();
            }
            return r.getCodReferencia();
        } catch (SQLException e) {
            if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                throw new RuntimeException("Ya existe una referencia con el código " + r.getCodReferencia() + ".");
            }
            throw errorBD(e);
        }
    }

    //elimina una referencia por su código
    public static boolean eliminarReferencia(int codigo) {
        String sql = "DELETE FROM referencia_hospitalaria WHERE idReferencia = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw errorBD(e);
        }
    }

    // ------------------------- Auxiliares -------------------------

    private static int ultimoId() throws SQLException {
        try (Statement st = conn().createStatement();
             ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
