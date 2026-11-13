
package modelo;

import java.util.ArrayList;

public class HistorialClinico {
    
    private Paciente paciente;
    private ArrayList<ConsultaMedica> consultas;
    private ArrayList<ReferenciaHospitalaria> referencias;

    public HistorialClinico() {
    }

    public HistorialClinico(Paciente paciente, ArrayList<ConsultaMedica> consultas, ArrayList<ReferenciaHospitalaria> referencias) {
        this.paciente = paciente;
        this.consultas = consultas;
        this.referencias = referencias;
    }

    public ArrayList<ReferenciaHospitalaria> getReferencias() {
        return referencias;
    }

    public void setReferencias(ArrayList<ReferenciaHospitalaria> referencias) {
        this.referencias = referencias;
    }

  

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ArrayList<ConsultaMedica> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<ConsultaMedica> consultas) {
        this.consultas = consultas;
    }

    
    public void agregarConsultas(ConsultaMedica consulta){
       consultas.add(consulta);
    }

    public String generarReporte() {
        
    StringBuilder sbConsultas = new StringBuilder();

    if (consultas == null || consultas.isEmpty()) {
        sbConsultas.append("Sin consultas registradas.\n");
    } else {
        for (ConsultaMedica c : consultas) {
            sbConsultas.append(c.toString()).append("\n");
        }
    }

      StringBuilder sbReferencias = new StringBuilder();
        if (referencias == null || referencias.isEmpty()) {
            sbReferencias.append("Sin referencias registradas.\n");
        } else {
            for (ReferenciaHospitalaria r : referencias) {
                sbReferencias.append(r.toString()).append("\n");
            }
        }

        return String.format("""
                              -----------------------------------------
                                        Historial Clinico
                              -----------------------------------------
                              %s
                              -------------CONSULTAS MEDICAS -----------
                              %s
                              --------- REFERENCIAS HOSPITALARIAS -------
                              %s
                              """,
                this.paciente, sbConsultas.toString(), sbReferencias.toString());
    }
    
    @Override
    public String toString(){
        return generarReporte();
    }
}
 

