/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author narda
 */
public class ejecutor {
    public static void main(String[] args) {

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
Paciente pa1 = new Paciente(
    45678912,
    "Juan",
    "Ramírez López",
    LocalDate.of(1990, 5, 14),
    "Av. Los Próceres 456, San Isidro",
    'M',
    987654321,
    "O+",
    "SIS",
    72.5,
    1.75
);
Usuario u1 = new Usuario(1, "admin", "admin123");
Medico med1 = new Medico(
    101,
    "Juan Carlos",
    "Ramírez Soto",
    u1
);

ConsultaMedica c1 = new ConsultaMedica(
    1001,
    "Dolor abdominal recurrente",
    "Medicina General",
    "Gastritis crónica",
    "Omeprazol 20mg cada 24h por 14 días",
    LocalDate.of(2026, 7, 8),
    LocalTime.of(9, 30),
    pa1,    
    med1   
);
ReferenciaHospitalaria r1 = new ReferenciaHospitalaria(
    2001,
    "Hospital Nacional Dos de Mayo",
    med1,     
    pa1,      
    LocalDate.of(2026, 7, 8),
    "Requiere evaluación por gastroenterología, sospecha de úlcera péptica",
    "Pendiente"
);

ArrayList<ConsultaMedica> consultasPaciente1 = new ArrayList<>();
consultasPaciente1.add(c1);

ArrayList<ReferenciaHospitalaria> referenciasPaciente1 = new ArrayList<>();
referenciasPaciente1.add(r1);

HistorialClinico historial1 = new HistorialClinico(pa1, consultasPaciente1, referenciasPaciente1);

System.out.println(historial1.generarReporte());


    }
    
}
