/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import java.util.Date;

public class Paciente {
    
    private int dni;
    private String nombres;
    private String apellidos;
    private LocalDate facheNac;
    private String direccion;
    private char genero;
    private int telefono;
    private String grupoSanguineo;
    private String tipoSeguro;
    private double peso;
    private double talla;

    public Paciente() {
    }

    public Paciente(int dni, String nombres, String apellidos, LocalDate facheNac, String direccion, char genero, int telefono, String grupoSanguineo, String tipoSeguro, double peso, double talla) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.facheNac = facheNac;
        this.direccion = direccion;
        this.genero = genero;
        this.telefono = telefono;
        this.grupoSanguineo = grupoSanguineo;
        this.tipoSeguro = tipoSeguro;
        this.peso = peso;
        this.talla = talla;
    }

  

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFacheNac() {
        return facheNac;
    }

    public void setFacheNac(LocalDate facheNac) {
        this.facheNac = facheNac;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

   @Override
    public String toString() {
        return String.format("""
                             ----------------------
                               DATOS DEL PACIENTE
                             ----------------------
                             - DNI: %d
                             - Nombres: %s
                             - Apellidos: %s
                             - Fecha de nacimiento: %s
                             - Genero: %s
                             - Grupo Sanguineo: %s
                             - Direccion: %s
                             - Telefono: %d
                             - Tipo de Seguro: %s
                             - Peso: %.2f
                             - Talla: %.2f""",
                this.dni, this.nombres, this.apellidos,
                this.facheNac, this.genero, this.grupoSanguineo,
                this.direccion, this.telefono, this.tipoSeguro,
                this.peso, this.talla);
    
    }
   
}
