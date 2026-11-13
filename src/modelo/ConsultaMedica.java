
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ConsultaMedica {
    private int idConsulta;
    private String motivoConsulta;
    private String especialidad;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private LocalTime hora;
    private Paciente paciente;
    private Medico medico;

    public ConsultaMedica() {
    }

    public ConsultaMedica(int idConsulta, String motivoConsulta, String especialidad, String diagnostico, String tratamiento, LocalDate fecha, LocalTime hora, Paciente paciente, Medico medico) {
        this.idConsulta = idConsulta;
        this.motivoConsulta = motivoConsulta;
        this.especialidad = especialidad;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = fecha;
        this.hora = hora;
        this.paciente = paciente;
        this.medico = medico;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }


    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

@Override
public String toString() {
    return String.format("""
            - ID Consulta: %d
            - Motivo de Consulta: %s
            - Especialidad: %s
            - Diagnóstico: %s
            - Tratamiento: %s
            - Fecha: %s    
            - Hora: %s
             %s""", 
            this.idConsulta, this.motivoConsulta,this.especialidad,this.diagnostico,this.tratamiento,
            this.fecha != null ? this.fecha.toString() : "No asignada",
            this.hora != null? this.hora.toString(): "No asignado", medico.toString()
    );
}
    
    
}
