package org.uma.jmetal.example.multiobjective.fap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreedySolver {

    private final List<Partido> partidos;
    private final List<Grupo> grupos;
    private final int[][] costoTraslado;

    public GreedySolver(List<Partido> partidos, List<Grupo> grupos, int[][] costoTraslado) {
        this.partidos = partidos;
        this.grupos = grupos;
        this.costoTraslado = costoTraslado;
    }

    public List<List<Integer>> solve() {
        List<List<Integer>> soluciones = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Integer> solucionGrupo = new ArrayList<>();
            List<Partido> partidosOrdenados = new ArrayList<>(partidos);

            partidosOrdenados.sort(Comparator.comparingInt(partido -> -grupo.calcularInteresPorPartido(partido.getId())));

            int presupuestoRestante = grupo.getPresupuesto();
            int ultimoPartido = -1;

            for (Partido partido : partidosOrdenados) {
                if (ultimoPartido != -1) {
                    int costoTraslado = this.costoTraslado[ultimoPartido][partido.getId()];
                    if (costoTraslado > presupuestoRestante) continue;

                    if (!verificarSuperposicion(ultimoPartido, partido)) continue;

                    presupuestoRestante -= costoTraslado;
                }

                solucionGrupo.add(partido.getId());
                ultimoPartido = partido.getId();
            }

            soluciones.add(solucionGrupo);
        }

        return soluciones;
    }

    private boolean verificarSuperposicion(int partidoAnterior, Partido partidoActual) {
        Partido anterior = partidos.get(partidoAnterior);

        if (anterior.getDia() < partidoActual.getDia()) return true;
        int tiempoDisponible = partidoActual.getHoraInicio() - (anterior.getHoraInicio() + anterior.getDuracion());
        return tiempoDisponible >= 0;
    }
}