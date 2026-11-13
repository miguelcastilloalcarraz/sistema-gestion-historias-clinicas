
package modelo;

import java.time.LocalDate;


public class ReferenciaHospitalaria {
    private int codReferencia;
    private String hospitalRef;
    private Medico medico;
    private Paciente paciente;
    private LocalDate fechaCons;
    private String observaciones;
    private String estadoReferencia;

    public ReferenciaHospitalaria() {
    }

    public ReferenciaHospitalaria(int codReferencia, String hospitalRef, Medico medico, Paciente paciente, LocalDate fechaCons, String observaciones, String estadoReferencia) {
        this.codReferencia = codReferencia;
        this.hospitalRef = hospitalRef;
        this.medico = medico;
        this.paciente = paciente;
        this.fechaCons = fechaCons;
        this.observaciones = observaciones;
        this.estadoReferencia = estadoReferencia;
    }


    public int getCodReferencia() {
        return codReferencia;
    }

    public void setCodReferencia(int codReferencia) {
        this.codReferencia = codReferencia;
    }

    public String getHospitalRef() {
        return hospitalRef;
    }

    public void setHospitalRef(String hospitalRef) {
        this.hospitalRef = hospitalRef;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFechaCons() {
        return fechaCons;
    }

    public void setFechaCons(LocalDate fechaCons) {
        this.fechaCons = fechaCons;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstadoReferencia() {
        return estadoReferencia;
    }

    public void setEstadoReferencia(String estadoReferencia) {
        this.estadoReferencia = estadoReferencia;
    }

   @Override
public String toString() {
       return String.format("""
            - Código Referencia: %d
            - Hospital de Destino: %s
            - Fecha de Consulta: %s
            - Estado de Referencia: %s
            - Paciente: %s
            - Médico Derivante: %s
            - Observaciones: %s
            -----------------------------------------
            """,
            this.codReferencia,
            this.hospitalRef,
            this.fechaCons != null ? this.fechaCons.toString() : "No asignada",
            this.estadoReferencia != null ? this.estadoReferencia : "No asignado",
            this.paciente != null ? this.paciente.getNombres() + " " + this.paciente.getApellidos() : "No asignado",
            this.medico != null ? this.medico.getNombres() + " " + this.medico.getApellido() : "No asignado",
            this.observaciones != null ? this.observaciones : "Ninguna"
    );
}
    
    
}
