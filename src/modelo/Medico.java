
package modelo;


public class Medico {
    
    private int colMedico;
    private String nombres;
    private String apellido;
    private Usuario usuario;

    public Medico() {
    }

    public Medico(int colMedico, String nombres, String apellido, Usuario usuario) {
        this.colMedico = colMedico;
        this.nombres = nombres;
        this.apellido = apellido;
        this.usuario = usuario;
    }

    public int getColMedico() {
        return colMedico;
    }

    public void setColMedico(int colMedico) {
        this.colMedico = colMedico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
public String toString() {
    return String.format("""
            -----------------------------------------
                     DATOS DEL MÉDICO
            -----------------------------------------
            - N° Colegiatura: %d
            - Nombre Completo: %s %s
            - Usuario del Sistema: %s
            """, 
            this.colMedico,this.nombres,this.apellido,
            this.usuario != null ? this.usuario.toString() : "Sin usuario asignado"
    );
}
    
}
