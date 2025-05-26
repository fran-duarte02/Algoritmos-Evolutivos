package org.uma.jmetal.example.multiobjective.fap;



import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import java.util.Random;

public class MatrixMutation implements MutationOperator<IntegerSolution> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double mutationProbability;
    private final Random random;

    public MatrixMutation(double mutationProbability) {
        this.mutationProbability = mutationProbability;
        this.random = new Random();
    }

    @Override
    public IntegerSolution execute(IntegerSolution solution) {
        if (random.nextDouble() <= mutationProbability) {
            int numVariables = solution.getNumberOfVariables();

         
            int index = random.nextInt(numVariables);

            Integer currentValue = solution.getVariable(index);

           
            if (currentValue != null) {
                solution.setVariable(index, getViableValue(solution, index));
            }
           
            else {
                int newIndex = findNonEmptyPosition(solution);
                if (newIndex != -1) {
                    solution.setVariable(newIndex, getViableValue(solution, newIndex));
                }
            }
        }
        return solution;
    }

    private Integer getViableValue(IntegerSolution solution, int index) {
        
        int lowerBound = solution.getLowerBound(index);
        int upperBound = solution.getUpperBound(index);
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    private int findNonEmptyPosition(IntegerSolution solution) {
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            if (solution.getVariable(i) != null) {
                return i;
            }
        }
        return -1;
    }

	@Override
	public double getMutationProbability() {
		return this.mutationProbability;
	}
}

