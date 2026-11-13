
package modelo;

public class Usuario {
    
    private int idUsuario;
    private String username;
    private String contraseña;

    public Usuario() {
    }

    public Usuario(int idUsuario, String username, String contraseña) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.contraseña = contraseña;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
    return String.format("""
            - ID Usuario: %d
            - Username: %s
            - Contraseña: %s""", 
            this.idUsuario,this.username,
            this.contraseña != null ? "********" : "No asignada"
    );
}
    
    
}
