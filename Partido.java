package org.uma.jmetal.example.multiobjective.fap;

public class Partido {
    private final int id;
    private final String ciudad;
    private final int horaInicio;
    private final int duracion;
    private final int dia; 

    public Partido(int id, String ciudad, int horaInicio, int duracion, int dia) {
        this.id = id;
        this.ciudad = ciudad;
        this.horaInicio = horaInicio;
        this.duracion = duracion;
        this.dia = dia;
    }

    public int getId() {
        return id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getDia() {
        return dia;
    }
}
