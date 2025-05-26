package org.uma.jmetal.example.multiobjective.fap;

import java.util.List;

public class Grupo {
    private final String tipo; 
    private final int presupuesto; 
   
    private List<Integer> partidosInteres; 

    public Grupo(String tipo, int presupuesto, List<Integer> partidos) {
        this.tipo = tipo;
        this.presupuesto = presupuesto;
       
        this.partidosInteres = partidos;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPresupuesto() {
        return presupuesto;
    }

    public List<Integer> getPartidosPrioritarios() {
        return partidosInteres;
    }

    public int calcularInteresPorPartido(int partidoId) {
        return partidosInteres.get(partidoId);
    }
}
