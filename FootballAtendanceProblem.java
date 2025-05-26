package org.uma.jmetal.example.multiobjective.fap;

import org.uma.jmetal.problem.AbstractGenericProblem;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;
import org.apache.commons.lang3.tuple.Pair;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FootballAtendanceProblem extends AbstractGenericProblem<IntegerSolution> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int numGroups;
    private final int numMatches;
    private final int[][] costoTraslado; // Matriz de costos de traslado
    private final List<Partido> partidos; // Lista de partido
    private final List<Integer> lowerBound;
    private final List<Integer> upperBound;
    private final List<Grupo> grupos;

    public FootballAtendanceProblem(int numGroups, int numMatches) {
        this.numGroups = numGroups;
        this.numMatches = numMatches;

        setNumberOfVariables(numGroups * numMatches);
        setNumberOfObjectives(2);

        this.lowerBound = new ArrayList<>();
        this.upperBound = new ArrayList<>();
        for (int i = 0; i < numGroups * numMatches; i++) {
            this.lowerBound.add(-1);
            this.upperBound.add(numMatches - 1);
        }

        this.partidos = iniPartidos();
        this.costoTraslado = iniCostoTraslado(this.partidos);
        this.grupos = iniGrupos();
    }
    

    @Override
    public int getNumberOfVariables() {
        return numGroups * numMatches;
    }

    @Override
    public int getNumberOfObjectives() {
        return 2; // Minimizar costos y maximizar interés
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }


    @Override
    public String getName() {
        return "FootballAttendanceProblem";
    }


    

    private List<Partido> iniPartidos() {
    
        List<Partido> matches = new ArrayList<>();

        matches.add(new Partido(0, "Ciudad de México", 1500, 120, 1));
        matches.add(new Partido(1, "Guadalajara", 2000, 120, 1));
        matches.add(new Partido(2, "Toronto", 1500, 120, 2));
        matches.add(new Partido(3, "Los Ángeles", 1500, 120, 2));
        matches.add(new Partido(4, "Boston", 1000, 120, 3));
        matches.add(new Partido(5, "Vancouver", 1400, 120, 3));
        matches.add(new Partido(6, "New York", 1600, 120, 3));
        matches.add(new Partido(7, "San Francisco", 2000, 120, 3));
        matches.add(new Partido(8, "Philadelphia", 1000, 120, 4));
        matches.add(new Partido(9, "Houston", 1400, 120, 4));
        matches.add(new Partido(10, "Dallas", 1700, 120, 4));
        matches.add(new Partido(11, "Monterrey", 2000, 120, 4));
        matches.add(new Partido(12, "Miami", 1000, 120, 5));
        matches.add(new Partido(13, "Atalanta", 1400, 120, 5));
        matches.add(new Partido(14, "Los Ángeles", 1700, 120, 5));
        matches.add(new Partido(15, "Seattle", 2000, 120, 5));
        matches.add(new Partido(16, "New York", 1000, 120, 6));
        matches.add(new Partido(17, "Boston", 1400, 120, 6));
        matches.add(new Partido(18, "Kansas", 1700, 120, 6));
        matches.add(new Partido(19, "San Francisco", 2000, 120, 6));
        matches.add(new Partido(20, "Toronto", 1000, 120, 7));
        matches.add(new Partido(21, "Dallas", 1400, 120, 7));
        matches.add(new Partido(22, "Houston", 1700, 120, 7));
        matches.add(new Partido(23, "Ciudad de México", 2000, 120, 7));
        matches.add(new Partido(24, "Atlanta", 1000, 120, 8));
        matches.add(new Partido(25, "Los Ángeles", 1400, 120, 8));
        matches.add(new Partido(26, "Vancouver", 1700, 120, 8));
        matches.add(new Partido(27, "Guadalajara", 2000, 120, 8));
        matches.add(new Partido(28, "Philadelphia", 1000, 120, 9));
        matches.add(new Partido(29, "Boston", 1400, 120, 9));
        matches.add(new Partido(30, "San Francisco", 1700, 120, 9));
        matches.add(new Partido(31, "Seattle", 2000, 120, 9));
        matches.add(new Partido(32, "Toronto", 1000, 120, 10));
        matches.add(new Partido(33, "Kansas", 1400, 120, 10));
        matches.add(new Partido(34, "Houston", 1700, 120, 10));
        matches.add(new Partido(35, "Monterrey", 2000, 120, 10));
        matches.add(new Partido(36, "Miami", 1000, 120, 11));
        matches.add(new Partido(37, "Atlanta", 1400, 120, 11));
        matches.add(new Partido(38, "Los Ángeles", 1700, 120, 11));
        matches.add(new Partido(39, "Vancouver", 2000, 120, 11));
        matches.add(new Partido(40, "New York", 1000, 120, 12));
        matches.add(new Partido(41, "Philadelphia", 1400, 120, 12));
        matches.add(new Partido(42, "Dallas", 1700, 120, 12));
        matches.add(new Partido(43, "San Francisco", 2000, 120, 13));
        matches.add(new Partido(44, "Boston", 1000, 120, 13));
        matches.add(new Partido(45, "Toronto", 1400, 120, 13));
        matches.add(new Partido(46, "Houston", 1700, 120, 13));
        matches.add(new Partido(47, "Guadalajara", 2000, 120, 13));
        matches.add(new Partido(48, "Miami", 1700, 120, 14));
        matches.add(new Partido(49, "Atlanta", 2000, 120, 14));
        matches.add(new Partido(50, "Vancouver", 1000, 120, 14));
        matches.add(new Partido(51, "Seattle", 1400, 120, 14));
        matches.add(new Partido(52, "Ciudad de México", 1700, 120, 14));
        matches.add(new Partido(53, "Monterrey", 2000, 120, 14));
        matches.add(new Partido(54, "Philadelphia", 1700, 120, 15));
        matches.add(new Partido(55, "New York", 2000, 120, 15));
        matches.add(new Partido(56, "Dallas", 1000, 120, 15));
        matches.add(new Partido(57, "Kansas", 1400, 120, 15));
        matches.add(new Partido(58, "Los Ángeles", 1700, 120, 15));
        matches.add(new Partido(59, "San Francisco", 2000, 120, 15));
        matches.add(new Partido(60, "Boston", 1700, 120, 16));
        matches.add(new Partido(61, "Toronto", 2000, 120, 16));
        matches.add(new Partido(62, "Seattle", 1000, 120, 16));
        matches.add(new Partido(63, "Vancouver", 1400, 120, 16));
        matches.add(new Partido(64, "Houston", 1700, 120, 16));
        matches.add(new Partido(65, "Guadalajara", 2000, 120, 16));
        matches.add(new Partido(66, "New York", 1700, 120, 17));
        matches.add(new Partido(67, "Philadelphia", 2000, 120, 17));
        matches.add(new Partido(68, "Kansas", 1000, 120, 17));
        matches.add(new Partido(69, "Dallas", 1400, 120, 17));
        matches.add(new Partido(70, "Miami", 1700, 120, 17));
        matches.add(new Partido(71, "Atlanta", 2000, 120, 17));
        matches.add(new Partido(72, "Los Ángeles", 2000, 120, 18));
        matches.add(new Partido(73, "Boston", 1500, 120, 19));
        matches.add(new Partido(74, "Monterrey", 1900, 120, 19));
        matches.add(new Partido(75, "Houston", 2100, 120, 19));
        matches.add(new Partido(76, "New York", 1500, 120, 20));
        matches.add(new Partido(77, "Dallas", 1900, 120, 20));
        matches.add(new Partido(78, "Ciudad de México", 2100, 120, 20));
        matches.add(new Partido(79, "Atlanta", 1500, 120, 21));
        matches.add(new Partido(80, "San Francisco", 1900, 120, 21));
        matches.add(new Partido(81, "Seattle", 2100, 120, 21));
        matches.add(new Partido(82, "Toronto", 1500, 120, 22));
        matches.add(new Partido(83, "Los Ángeles", 1900, 120, 22));
        matches.add(new Partido(84, "Vancouver", 2100, 120, 22));
        matches.add(new Partido(85, "Miami", 1500, 120, 23));
        matches.add(new Partido(86, "Kansas", 1900, 120, 23));
        matches.add(new Partido(87, "Dallas", 2100, 120, 23));
        matches.add(new Partido(88, "Philadelphia", 1500, 120, 24));
        matches.add(new Partido(89, "Houston", 1900, 120, 24));
        matches.add(new Partido(90, "New York", 2100, 120, 25));
        matches.add(new Partido(91, "Ciudad de México", 2100, 120, 25));
        matches.add(new Partido(92, "Dallas", 2100, 120, 26));
        matches.add(new Partido(93, "Seattle", 1500, 120, 27));
        matches.add(new Partido(94, "Monterrey", 1900, 120, 27));
        matches.add(new Partido(95, "Vancouver", 2100, 120, 27));
        matches.add(new Partido(96, "San Francisco", 1500, 120, 28));
        matches.add(new Partido(97, "Kansas", 1900, 120, 28));
        matches.add(new Partido(98, "Houston", 2100, 120, 28));
        matches.add(new Partido(99, "Philadelphia", 1500, 120, 29));
        matches.add(new Partido(100, "New York", 1900, 120, 29));
        matches.add(new Partido(101, "San Francisco", 2100, 120, 29));
        matches.add(new Partido(102, "Los Ángeles", 1500, 120, 30));
        matches.add(new Partido(103, "Boston", 1900, 120, 31));

        return matches;
    }

    private int[][] iniCostoTraslado(List<Partido> partidos) {
        int numPartidos = partidos.size();


        
        List<String> ordenCiudades = List.of(
            "Ciudad de México", "Guadalajara", "Monterrey", "Houston", "Dallas", "Atlanta", "Miami", "Philadelphia", 
            "Nueva York", "Boston", "Toronto", "Kansas", "Los Ángeles", "San Francisco", "Seattle", "Vancouver"
        );

        
        int[][] costos = new int[numPartidos][numPartidos];

        for (int i = 0; i < numPartidos; i++) {
            for (int j = 0; j < numPartidos; j++) {
                if (i == j) {
                    costos[i][j] = 0;
                } else {
                    
                    int ciudadI = ordenCiudades.indexOf(partidos.get(i).getCiudad());
                    int ciudadJ = ordenCiudades.indexOf(partidos.get(j).getCiudad());

                    
                    int diferencia = Math.abs(ciudadI - ciudadJ);
                    costos[i][j] = 100 + diferencia * 50;
                }
            }
        }

        
        return costos; 
        
    }

    

    
    private List<Grupo> iniGrupos() {
        List<Grupo> grupos = new ArrayList<>();

        grupos.add(new Grupo("Latinoamericano", 10000, Arrays.asList(10, 1, 6, 6, 5, 7, 4, 4, 1, 1, 4, 10, 4, 4, 1, 1, 8, 4, 8, 10, 6, 1, 3, 7, 2, 2, 8, 9, 10, 2, 9, 7, 1, 8, 9, 7, 5, 5, 9, 9, 1, 3, 7, 9, 1, 6, 5, 10, 8, 9, 5, 8, 10, 5, 3, 2, 1, 3, 4, 9, 9, 10, 2, 5, 5, 7, 2, 7, 6, 10, 8, 7, 1, 7, 4, 8, 6, 6, 2, 6, 2, 4, 10, 8, 2, 9, 2, 7, 9, 4, 2, 10, 4, 10, 8, 8, 9, 8, 6, 3, 9, 9, 9, 10)));
        grupos.add(new Grupo("Brasil", 8500, Arrays.asList(7, 5, 1, 8, 8, 8, 9, 5, 2, 1, 1, 2, 9, 7, 4, 4, 6, 7, 1, 1, 4, 10, 7, 2, 2, 5, 6, 3, 8, 4, 7, 7, 4, 4, 2, 1, 9, 2, 7, 7, 10, 9, 1, 6, 4, 2, 2, 4, 8, 5, 2, 8, 8, 7, 2, 3, 6, 2, 2, 1, 6, 9, 8, 8, 3, 7, 6, 8, 6, 5, 7, 3, 6, 6, 7, 9, 1, 8, 7, 7, 4, 7, 6, 4, 10, 2, 6, 2, 10, 4, 2, 1, 7, 8, 10, 10, 8, 3, 1, 7, 10, 7, 7, 10)));
        grupos.add(new Grupo("Neutral", 16000, Arrays.asList(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5)));
        grupos.add(new Grupo("Europeo", 18000, Arrays.asList(5, 10, 6, 9, 3, 9, 8, 10, 9, 6, 5, 5, 10, 4, 2, 9, 6, 5, 5, 1, 4, 6, 9, 4, 6, 2, 9, 8, 10, 2, 1, 10, 6, 7, 8, 9, 3, 2, 1, 2, 3, 5, 7, 4, 9, 5, 4, 6, 10, 2, 2, 8, 2, 10, 10, 7, 5, 3, 8, 1, 5, 9, 7, 1, 9, 7, 3, 4, 10, 9, 7, 9, 3, 8, 3, 7, 10, 5, 2, 5, 1, 2, 8, 4, 3, 4, 10, 3, 7, 3, 6, 7, 4, 6, 1, 2, 7, 8, 7, 2, 9, 8, 8, 10)));
        grupos.add(new Grupo("Asia", 15000, Arrays.asList(8, 8, 2, 8, 7, 2, 9, 10, 3, 6, 4, 6, 8, 5, 4, 10, 5, 2, 4, 1, 5, 7, 8, 2, 5, 2, 3, 10, 7, 9, 10, 8, 7, 10, 4, 9, 1, 3, 4, 6, 9, 2, 4, 6, 5, 4, 10, 4, 8, 1, 10, 3, 4, 1, 7, 9, 8, 4, 1, 9, 10, 8, 4, 3, 10, 10, 5, 3, 3, 6, 10, 4, 5, 6, 7, 10, 8, 6, 3, 2, 1, 6, 6, 10, 9, 8, 9, 7, 5, 2, 4, 5, 4, 8, 3, 4, 1, 3, 5, 10, 9, 9, 9, 9)));
        grupos.add(new Grupo("África", 9000, Arrays.asList(9, 4, 7, 9, 7, 10, 4, 4, 9, 3, 1, 2, 6, 7, 6, 9, 1, 9, 1, 3, 5, 10, 10, 5, 2, 3, 2, 9, 3, 9, 1, 1, 3, 10, 5, 10, 9, 2, 7, 3, 4, 3, 8, 10, 9, 2, 8, 10, 1, 8, 6, 2, 7, 10, 7, 2, 1, 3, 10, 9, 1, 4, 6, 9, 10, 4, 1, 6, 5, 8, 3, 1, 5, 1, 3, 10, 9, 4, 2, 1, 3, 1, 4, 3, 1, 6, 4, 4, 1, 2, 5, 10, 2, 2, 10, 4, 1, 8, 4, 6, 5, 7, 10, 10)));
        grupos.add(new Grupo("América del Norte", 19000, Arrays.asList(8, 2, 5, 7, 5, 3, 2, 5, 4, 5, 9, 1, 5, 4, 10, 7, 3, 1, 2, 1, 7, 2, 8, 5, 10, 8, 5, 10, 2, 4, 3, 1, 3, 2, 9, 5, 3, 1, 6, 6, 2, 9, 2, 9, 5, 8, 8, 8, 3, 7, 3, 4, 7, 7, 2, 7, 7, 2, 8, 4, 10, 4, 10, 3, 5, 3, 4, 5, 9, 4, 5, 10, 2, 4, 8, 4, 10, 8, 6, 8, 4, 9, 4, 8, 6, 4, 2, 5, 7, 6, 9, 9, 7, 2, 2, 10, 10, 6, 8, 4, 7, 7, 9, 10)));
        grupos.add(new Grupo("Suramérica", 15000, Arrays.asList(7, 5, 5, 7, 10, 10, 7, 4, 5, 3, 9, 2, 7, 1, 6, 4, 8, 7, 3, 10, 6, 6, 1, 9, 2, 9, 3, 8, 8, 9, 2, 2, 6, 3, 6, 6, 9, 5, 10, 2, 3, 5, 7, 7, 8, 1, 4, 1, 6, 2, 8, 3, 10, 10, 9, 5, 3, 2, 9, 1, 7, 1, 4, 6, 6, 4, 8, 6, 5, 6, 6, 8, 5, 6, 7, 1, 2, 8, 1, 5, 1, 7, 9, 3, 1, 1, 2, 4, 4, 6, 7, 4, 3, 2, 4, 6, 4, 1, 2, 5, 1, 8, 1, 9)));
        grupos.add(new Grupo("Oceania", 12000, Arrays.asList(4, 7, 6, 10, 7, 10, 3, 6, 3, 8, 2, 10, 1, 10, 4, 7, 5, 10, 4, 9, 2, 5, 10, 10, 6, 9, 8, 2, 2, 1, 10, 7, 4, 8, 3, 1, 1, 4, 2, 5, 5, 2, 8, 4, 6, 4, 3, 9, 10, 8, 10, 1, 7, 4, 8, 10, 5, 3, 10, 10, 10, 8, 10, 9, 10, 4, 2, 10, 4, 1, 1, 5, 7, 7, 7, 5, 5, 4, 4, 7, 1, 4, 7, 1, 10, 2, 7, 7, 7, 1, 9, 4, 8, 10, 1, 8, 5, 7, 3, 1, 2, 9, 9, 4)));
        grupos.add(new Grupo("Mundialistas", 20000, Arrays.asList(1, 5, 4, 2, 8, 3, 1, 9, 1, 10, 6, 4, 6, 8, 2, 4, 5, 7, 8, 2, 6, 10, 8, 9, 9, 7, 8, 6, 1, 6, 10, 4, 4, 1, 6, 2, 6, 3, 6, 5, 1, 7, 3, 3, 7, 1, 9, 6, 5, 8, 5, 6, 8, 5, 1, 4, 2, 3, 5, 8, 7, 5, 7, 5, 2, 2, 1, 5, 1, 9, 1, 8, 3, 2, 3, 7, 2, 8, 1, 4, 1, 8, 7, 7, 2, 10, 1, 2, 5, 2, 8, 4, 5, 5, 3, 3, 2, 1, 6, 9, 4, 4, 8, 9)));

        return grupos;
    }

    
   
    public List<Partido> getPartidos() {
        return partidos;
    }

    public int[][] getCostoTraslado() {
        return costoTraslado;
    }


    
    @Override
    public IntegerSolution createSolution() {
        List<Pair<Integer, Integer>> bounds = new ArrayList<>();
        for (int i = 0; i < numGroups * numMatches; i++) {
            bounds.add(Pair.of(-1, numMatches - 1)); 
        }

        int numObjectives = 2; 
        IntegerSolution solution = new DefaultIntegerSolution(bounds, numObjectives, 0);

        
        Random random = new Random();

        for (int i = 0; i < numGroups; i++) {
            List<Integer> assignedMatches = new ArrayList<>();  
            Set<Integer> assignedMatchSet = new HashSet<>();    

            for (int j = 0; j < numMatches; j++) {
                int value = -1;  
                if (random.nextDouble() < 0.7) {  

                    value = random.nextInt(numMatches);  
                   
                    while (assignedMatchSet.contains(value)) {
                        value = random.nextInt(numMatches);  
                    }
                    assignedMatchSet.add(value);  
                }
                assignedMatches.add(value); 
            }

           
            assignedMatches.sort((a, b) -> {
                if (a == -1 && b != -1) return 1;  
                if (a != -1 && b == -1) return -1; 
                return Integer.compare(a, b);      
            });

           
            for (int j = 0; j < numMatches; j++) {
                int index = i * numMatches + j;
                solution.setVariable(index, assignedMatches.get(j));
            }
        }

        return solution;
    }


    public int getNumGroups() {
        return numGroups;
    }

    public int getNumMatches() {
        return numMatches;
    }
    
    
    private void repararSolucion(IntegerSolution solution) {
        repararSuperposicion(solution); 
        repararPresupuesto(solution);   
    }

    @Override
    public void evaluate(IntegerSolution solution) {
        double totalCost = 0.0; 
        double totalInterest = 0.0; 

        
        repararSolucion(solution);
        

     
        for (int i = 0; i < numGroups; i++) {
            double groupCost = 0.0;
            double groupInterest = 0.0;
            int previousMatch = -1;

            for (int j = 0; j < numMatches; j++) {
                int index = i * numMatches + j;
                int currentMatch = solution.getVariable(index);

               
                if (currentMatch == -1) {
                    break;
                }

               
                if (previousMatch != -1) {
                    groupCost += costoTraslado[previousMatch][currentMatch];
                }

               
                groupInterest += grupos.get(i).calcularInteresPorPartido(currentMatch);

              
                previousMatch = currentMatch;
            }

            
            totalCost += groupCost;
            totalInterest += groupInterest;
        }

       
        solution.setObjective(0, totalCost);      
        solution.setObjective(1, -totalInterest);  
    }

    private boolean verificarSuperposicion(Partido partido1, Partido partido2, int tiempoTraslado, int margen) {
        final int MINUTOS_EN_UN_DIA = 24 * 60; 

        if (partido1.getDia() == partido2.getDia()) {
            int tiempoDisponible = partido2.getHoraInicio() - (partido1.getHoraInicio() + partido1.getDuracion());
            return tiempoTraslado + partido1.getDuracion() + margen <= tiempoDisponible;
        }

      
        if (partido1.getDia() < partido2.getDia()) {
            int finDiaActual = MINUTOS_EN_UN_DIA - partido1.getHoraInicio() - partido1.getDuracion();
            int inicioDiaSiguiente = partido2.getHoraInicio(); 

            int tiempoDisponible = finDiaActual + inicioDiaSiguiente;
            return tiempoTraslado + margen <= tiempoDisponible;
        }

        return false;
    }

    private void repararSuperposicion(IntegerSolution solution) {
        for (int i = 0; i < numGroups; i++) {
            boolean needsReevaluation;

           
            do {
                needsReevaluation = false;
                int previousMatch = -1;

                for (int j = 0; j < numMatches; j++) {
                    int index = i * numMatches + j;
                    int currentMatch = solution.getVariable(index);

                    if (currentMatch == -1) break; 

                    if (previousMatch != -1) {
                        Partido partidoAnterior = partidos.get(previousMatch);
                        Partido partidoActual = partidos.get(currentMatch);
                        int tiempoTraslado = costoTraslado[previousMatch][currentMatch];

                       
                        if (!verificarSuperposicion(partidoAnterior, partidoActual, tiempoTraslado, 30)) {
                                desplazarIzquierda(solution, i, j); 
                            needsReevaluation = true;
                            break; 
                        }
                    }

                    previousMatch = currentMatch;
                }
            } while (needsReevaluation);
        }
    }


    private void desplazarIzquierda(IntegerSolution solution, int grupo, int desdeIndice) {
        for (int j = desdeIndice; j < numMatches - 1; j++) {
            int indexActual = grupo * numMatches + j;
            int indexSiguiente = grupo * numMatches + (j + 1);

           
            solution.setVariable(indexActual, solution.getVariable(indexSiguiente));
        }

       
        int ultimoIndice = grupo * numMatches + (numMatches - 1);
        solution.setVariable(ultimoIndice, -1);
    }


    private void repararPresupuesto(IntegerSolution solution) {
        for (int i = 0; i < grupos.size(); i++) {
            Grupo grupo = grupos.get(i);  
            double groupCost = 0.0;
            int previousMatch = -1;

            
            int presupuesto = grupo.getPresupuesto();

            
            for (int j = 0; j < numMatches; j++) {
              
                int currentMatch = solution.getVariable(i * numMatches + j);
                if (currentMatch == -1) break; 

               
                if (previousMatch != -1) {
                    groupCost += costoTraslado[previousMatch][currentMatch];
                }

              
                if (groupCost > presupuesto) {
                    
                    for (int k = j; k < numMatches; k++) {
                        solution.setVariable(i * numMatches + k, -1); 
                    }
                    break; 
                }

                previousMatch = currentMatch;
            }
        }
    }


	public List<Grupo> getGrupos() {
		return this.grupos;
	}

}