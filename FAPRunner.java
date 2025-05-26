package org.uma.jmetal.example.multiobjective.fap;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.example.AlgorithmRunner;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.util.AbstractAlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class FAPRunner extends AbstractAlgorithmRunner {
    public static void main(String[] args) {
        
        int numGroups = 10;
        int numMatches = 104;
        FootballAtendanceProblem problem = new FootballAtendanceProblem(numGroups, numMatches);
        Algorithm<List<IntegerSolution>> algorithm;

        
        double crossoverProbability = 0.9;
        CrossoverOperator<IntegerSolution> crossover = new MatrixCrossover(crossoverProbability);
        double mutationProbability = 0.1;
        MutationOperator<IntegerSolution> mutation = new MatrixMutation(mutationProbability);
        SelectionOperator<List<IntegerSolution>, IntegerSolution> selection =
                new BinaryTournamentSelection<>(new RankingAndCrowdingDistanceComparator<>());

        
        int populationSize = 500;
        algorithm = new NSGAIIBuilder<>(problem, crossover, mutation, populationSize)
                .setSelectionOperator(selection)
                .setMaxEvaluations(25000)
                .build();

        
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();
        List<IntegerSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        
        List<IntegerSolution> paretoFrontNSGAII = obtenerFrenteDePareto(population);
        printBestSolution(population, paretoFrontNSGAII);
        visualizarFrenteDePareto(paretoFrontNSGAII);

       
        GreedySolver greedySolver = new GreedySolver(problem.getPartidos(), problem.getGrupos(), problem.getCostoTraslado());
        List<List<Integer>> greedySolution = greedySolver.solve();

      
        List<IntegerSolution> greedySolutionsEvaluadas = evaluarSolucionesGreedy(greedySolution, problem);
        printGreedySolutions(greedySolutionsEvaluadas);

        
        visualizarComparacionFrenteDePareto(paretoFrontNSGAII, greedySolutionsEvaluadas);

        
        System.out.println("Valor de spacing (NSGA-II): " + calculateSpacing(paretoFrontNSGAII));
        
        
    }

    private static List<IntegerSolution> evaluarSolucionesGreedy(List<List<Integer>> greedySolution, FootballAtendanceProblem problem) {
        List<IntegerSolution> solucionesEvaluadas = new ArrayList<>();
        for (List<Integer> solucionGreedy : greedySolution) {
            IntegerSolution solution = problem.createSolution();
            for (int i = 0; i < solucionGreedy.size(); i++) {
                solution.setVariable(i, solucionGreedy.get(i));
            }
            problem.evaluate(solution);
            solucionesEvaluadas.add(solution);
        }
        return solucionesEvaluadas;
    }

    private static void printGreedySolutions(List<IntegerSolution> solucionesGreedy) {
        System.out.println("\nSoluciones del Algoritmo Greedy:");
        for (IntegerSolution solution : solucionesGreedy) {
            
            System.out.println("\nObjetivos:");
            for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
                System.out.println("Objetivo " + (i + 1) + ": " + solution.getObjective(i));
            }
        }
    }

    private static void visualizarComparacionFrenteDePareto(List<IntegerSolution> frenteNSGAII, List<IntegerSolution> frenteGreedy) {
        XYSeries serieNSGAII = new XYSeries("NSGA-II");
        for (IntegerSolution s : frenteNSGAII) {
            serieNSGAII.add(s.getObjective(0), s.getObjective(1));
        }

        XYSeries serieGreedy = new XYSeries("Greedy");
        for (IntegerSolution s : frenteGreedy) {
            serieGreedy.add(s.getObjective(0), s.getObjective(1));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serieNSGAII);
        dataset.addSeries(serieGreedy);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Comparación de Frentes de Pareto",
                "Objetivo 1",
                "Objetivo 2",
                dataset
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Comparación NSGA-II vs Greedy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

   
    private static void printBestSolution(List<IntegerSolution> population, List<IntegerSolution> paretoFront) {
        IntegerSolution bestSolution = findBalancedSolution(paretoFront);
        //IntegerSolution bestSolution = paretoFront.get(0);
        boolean isParetoOptimal = isParetoOptimal(bestSolution, paretoFront);

     
        System.out.println("Mejor solución encontrada (Asignaciones de partidos):");
        for (int i = 0; i < bestSolution.getNumberOfVariables(); i++) {
            System.out.print(bestSolution.getVariable(i) + " ");
        }
        System.out.println();

      
        System.out.println("Valores de los objetivos:");
        for (int i = 0; i < bestSolution.getNumberOfObjectives(); i++) {
            System.out.println("Objetivo " + (i + 1) + ": " + bestSolution.getObjective(i));
        }

        
        if (isParetoOptimal) {
            System.out.println("pertenece al frente de Pareto.");
        } else {
            System.out.println("NO pertenece al frente de Pareto.");
        }
    }


    // Función para comparar dos soluciones y determinar si una domina a otra
    public static boolean dominates(IntegerSolution A, IntegerSolution B) {
        boolean isDominated = false;

        for (int i = 0; i < A.getNumberOfObjectives(); i++) {
            if (A.getObjective(i) > B.getObjective(i)) {
                isDominated = true; 
            } else if (A.getObjective(i) < B.getObjective(i)) {
                return false; 
            }
        }

        return isDominated;
    }

    // Función para obtener el frente de Pareto
    public static List<IntegerSolution> getParetoFront(List<IntegerSolution> solutions) {
        List<IntegerSolution> paretoFront = new ArrayList<>();

        for (IntegerSolution solutionA : solutions) {
            boolean isDominated = false;

            
            for (IntegerSolution solutionB : solutions) {
                if (dominates(solutionB, solutionA)) {
                    isDominated = true; 
                    break;
                }
            }

            if (!isDominated) {
                paretoFront.add(solutionA); 
            }
        }

        return paretoFront;
    }

    
    public static boolean isParetoOptimal(IntegerSolution solution, List<IntegerSolution> paretoFront) {
        for (IntegerSolution paretoSolution : paretoFront) {
            if (dominates(paretoSolution, solution)) {
                return false; 
            }
        }
        return true; 
    }
    
    private static double calculateSpacing(List<IntegerSolution> paretoFront) {
        int numObjectives = paretoFront.get(0).getNumberOfObjectives();
        double[] distances = new double[paretoFront.size()];

        for (int i = 0; i < paretoFront.size(); i++) {
            double minDistance = Double.MAX_VALUE;
            for (int j = 0; j < paretoFront.size(); j++) {
                if (i != j) {
                    double distance = 0.0;
                    for (int k = 0; k < numObjectives; k++) {
                        double diff = paretoFront.get(i).getObjective(k) - paretoFront.get(j).getObjective(k);
                        distance += diff * diff;
                    }
                    distance = Math.sqrt(distance);
                    minDistance = Math.min(minDistance, distance);
                }
            }
            distances[i] = minDistance;
        }

        double averageDistance = 0.0;
        for (double d : distances) {
            averageDistance += d;
        }
        averageDistance /= distances.length;

        double spacing = 0.0;
        for (double d : distances) {
            spacing += Math.pow(d - averageDistance, 2);
        }
        spacing = Math.sqrt(spacing / (distances.length - 1));

        return spacing;
    }
    
    public static void visualizarFrenteDePareto(List<IntegerSolution> frenteDePareto) {
        XYSeries serie = new XYSeries("Frente de Pareto");

        
        for (IntegerSolution s : frenteDePareto) {
            double objetivo1 = s.getObjective(0);
            double objetivo2 = s.getObjective(1);
            serie.add(objetivo1, objetivo2);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(serie);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Frente de Pareto",    
                "Objetivo 1",          
                "Objetivo 2",          
                dataset                
        );

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 600));
        JFrame frame = new JFrame("Visualización del Frente de Pareto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static List<IntegerSolution> obtenerFrenteDePareto(List<IntegerSolution> soluciones) {
        List<IntegerSolution> frenteDePareto = new ArrayList<>();

        for (int i = 0; i < soluciones.size(); i++) {
            boolean dominada = false;
            for (int j = 0; j < soluciones.size(); j++) {
                if (i != j) {
                    if (domina(soluciones.get(j), soluciones.get(i))) {
                        dominada = true;
                        break;
                    }
                }
            }
            if (!dominada) {
                frenteDePareto.add(soluciones.get(i));
            }
        }
        return frenteDePareto;
    }

    public static boolean domina(IntegerSolution a, IntegerSolution b) {
        boolean domina = true;
        for (int i = 0; i < a.getNumberOfObjectives(); i++) {
            if (a.getObjective(i) > b.getObjective(i)) {
                domina = false;
                break;
            }
        }
        return domina;
    }

    public static IntegerSolution findBalancedSolution(List<IntegerSolution> paretoFront) {
        if (paretoFront.isEmpty()) {
            throw new IllegalArgumentException("El frente de Pareto está vacío.");
        }

        int numObjectives = paretoFront.get(0).getNumberOfObjectives();
        int numVariables = paretoFront.get(0).getNumberOfVariables();

        // Calcular los extremos del frente de Pareto
        double[] minValues = new double[numObjectives];
        double[] maxValues = new double[numObjectives];

        Arrays.fill(minValues, Double.MAX_VALUE);
        Arrays.fill(maxValues, -Double.MAX_VALUE);

        for (IntegerSolution solution : paretoFront) {
            for (int i = 0; i < numObjectives; i++) {
                double value = solution.getObjective(i);
                if (value < minValues[i]) {
                    minValues[i] = value;
                }
                if (value > maxValues[i]) {
                    maxValues[i] = value;
                }
            }
        }

        
        double[] center = new double[numObjectives];
        for (int i = 0; i < numObjectives; i++) {
            center[i] = (minValues[i] + maxValues[i]) / 2.0;
        }

        
        IntegerSolution balancedSolution = null;
        double minDistance = Double.MAX_VALUE;

        for (IntegerSolution solution : paretoFront) {
            if (!tieneGrupo(solution, numVariables)) {
                continue;
            }

            double distance = 0.0;
            for (int i = 0; i < numObjectives; i++) {
                double normalizedObjective = (solution.getObjective(i) - minValues[i]) / (maxValues[i] - minValues[i]);
                double normalizedCenter = (center[i] - minValues[i]) / (maxValues[i] - minValues[i]);
                double diff = normalizedObjective - normalizedCenter;
                distance += diff * diff;
            }
            distance = Math.sqrt(distance);

            if (distance < minDistance) {
                minDistance = distance;
                balancedSolution = solution;
            }
        }

        
        if (balancedSolution == null) {
            balancedSolution = paretoFront.get(0);
        }

        return balancedSolution;
    }

    
    private static boolean tieneGrupo(IntegerSolution solution, int numVariables) {
        int numGroups = solution.getNumberOfVariables() / 104;
        for (int group = 0; group < numGroups; group++) {
            boolean hasMatch = false;
            for (int i = group * 104; i < (group + 1) * 104; i++) {
                if (solution.getVariable(i) != -1) {
                    hasMatch = true;
                    break;
                }
            }
            if (!hasMatch) {
                return false; 
            }
        }
        return true;
    }





    public static double calculateHypervolume(List<IntegerSolution> paretoFront, double[] referencePoint) {
        double hypervolume = 0.0;

        for (IntegerSolution solution : paretoFront) {
            double volume = 1.0;
            for (int i = 0; i < solution.getNumberOfObjectives(); i++) {
                volume *= Math.max(0, referencePoint[i] - solution.getObjective(i));
            }
            hypervolume += volume;
        }

        return hypervolume;
    }

    public static double calculateSpread(List<IntegerSolution> paretoFront, double[] extremePoints) {
        int numObjectives = paretoFront.get(0).getNumberOfObjectives();
        double[] distances = new double[paretoFront.size()];

        // Calcular las distancias al vecino más cercano
        for (int i = 0; i < paretoFront.size(); i++) {
            double minDistance = Double.MAX_VALUE;
            for (int j = 0; j < paretoFront.size(); j++) {
                if (i != j) {
                    double distance = 0.0;
                    for (int k = 0; k < numObjectives; k++) {
                        double diff = paretoFront.get(i).getObjective(k) - paretoFront.get(j).getObjective(k);
                        distance += diff * diff;
                    }
                    distance = Math.sqrt(distance);
                    minDistance = Math.min(minDistance, distance);
                }
            }
            distances[i] = minDistance;
        }

        // Calcular la distancia promedio
        double meanDistance = 0.0;
        for (double d : distances) {
            meanDistance += d;
        }
        meanDistance /= distances.length;

        // Calcular el spread
        double sum = 0.0;
        for (double d : distances) {
            sum += Math.abs(d - meanDistance);
        }

        double maxSpread = 0.0;
        for (int i = 0; i < extremePoints.length; i++) {
            maxSpread += Math.abs(extremePoints[i] - meanDistance);
        }

        return (sum + maxSpread) / (sum + distances.length);
    }

    public static double[] calculateExtremePoints(List<IntegerSolution> paretoFront) {
        int numObjectives = 2;
        double[] minValues = new double[numObjectives];
        double[] maxValues = new double[numObjectives];

        // Inicializar con valores extremos
        for (int i = 0; i < numObjectives; i++) {
            minValues[i] = Double.MAX_VALUE;
            maxValues[i] = Double.MIN_VALUE;
        }

        // Encontrar los valores extremos
        for (IntegerSolution solution : paretoFront) {
            for (int i = 0; i < numObjectives; i++) {
                double value = solution.getObjective(i);
                if (value < minValues[i]) {
                    minValues[i] = value;
                }
                if (value > maxValues[i]) {
                    maxValues[i] = value;
                }
            }
        }

        // Combinar extremos en un solo arreglo
        double[] extremePoints = new double[numObjectives * 2];
        for (int i = 0; i < numObjectives; i++) {
            extremePoints[i * 2] = minValues[i];      // Mínimo
            extremePoints[i * 2 + 1] = maxValues[i]; // Máximo
        }

        return extremePoints;
    }

}

