package org.uma.jmetal.example.multiobjective.fap;

import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixCrossover implements CrossoverOperator<IntegerSolution> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double crossoverProbability;
    private final Random random;

    public MatrixCrossover(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
        this.random = new Random();
    }

    @Override
    public List<IntegerSolution> execute(List<IntegerSolution> parents) {
    	
    	
    	
        if (parents.size() != 2) {
            throw new IllegalArgumentException("");
        }

        IntegerSolution parent1 = parents.get(0);
        IntegerSolution parent2 = parents.get(1);

        IntegerSolution offspring1 = (IntegerSolution) parent1.copy();
        IntegerSolution offspring2 = (IntegerSolution) parent2.copy();

        if (random.nextDouble() <= crossoverProbability) {
            int numVariables = parent1.getNumberOfVariables();

            for (int i = 0; i < numVariables; i++) {
             
                int cutPoint1 = random.nextInt(numVariables);
                int cutPoint2 = random.nextInt(numVariables);

                if (cutPoint1 > cutPoint2) {
                    int temp = cutPoint1;
                    cutPoint1 = cutPoint2;
                    cutPoint2 = temp;
                }

                // Cruzamiento 2PX
                for (int j = 0; j < numVariables; j++) {
                    if (j < cutPoint1 || j > cutPoint2) {
                        offspring1.setVariable(j, parent1.getVariable(j));
                        offspring2.setVariable(j, parent2.getVariable(j));
                    } else {
                        offspring1.setVariable(j, parent2.getVariable(j));
                        offspring2.setVariable(j, parent1.getVariable(j));
                    }
                }
            }
            
            // Reparación básica
            repairSolution(offspring1);
            repairSolution(offspring2);
        }

        List<IntegerSolution> offspring = new ArrayList<>();
        offspring.add(offspring1);
        offspring.add(offspring2);
        return offspring;
    }

    private void repairSolution(IntegerSolution solution) {
        List<Integer> values = new ArrayList<>();
        
        
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            Integer value = solution.getVariable(i);
            if (value != -1) {
                values.add(value);
            }
        }

        
        List<Integer> uniqueValues = new ArrayList<>();
        for (Integer value : values) {
            if (!uniqueValues.contains(value)) {
                uniqueValues.add(value);
            }
        }
        uniqueValues.sort(Integer::compareTo); 

       
        int idx = 0;
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (idx < uniqueValues.size()) {
                solution.setVariable(i, uniqueValues.get(idx));
                idx++;
            } else {
                solution.setVariable(i, -1); 
            }
        }

      
        List<Integer> seen = new ArrayList<>();
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            Integer value = solution.getVariable(i);
            if (seen.contains(value)) {
                solution.setVariable(i, -1);
            } else {
                seen.add(value);
            }
        }
    }


    @Override
    public double getCrossoverProbability() {
        return this.crossoverProbability;
    }

    @Override
    public int getNumberOfRequiredParents() {
        return 2; 
    }

    @Override
    public int getNumberOfGeneratedChildren() {
        return 2; 
    }
}

