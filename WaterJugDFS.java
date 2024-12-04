/* WaterjugDFS.java
 * Prints the set of all possible states of the three jug water-jug problem
 * by traversing the search space with depth first search.
 * Author: Joseph Marcus Tungate - B913717
 * Last updated: 26/11/2020 
 * */

/* Data structures used in this program
 * ArrayList - A list used to store the set of states visited by the DFS.
 * ArrayDeque - A stack used to implement the fringe used by the DFS.
 * int[] - States are implemented as arrays of length three where each element
 *         stores the contents of a single jug.
 * Scanner - This class is used to get values for the capacities of the jugs
 *           from the user.
 * WaterJugProblem - This class implements the state finding algorithm. This is done to separate
 *                   the algorithm from program specific details of input and output.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class WaterJugDFS {
	public static void main(String[] args) {
		int[] startState = {0, 0, 0};
		int[] capacities = getCapacities();
		
		WaterJugProblem problem = new WaterJugProblem(capacities, startState);
		ArrayList<int[]> states = problem.getStates();
		
		printResults(states);
	}
		
	
	/**
	 * Prompts the user for values for the capacities of the jugs.
	 * Only returns once valid values have been given.
	 * @return Array containing the capacities of the jugs.
	 */
	private static int[] getCapacities() {
		Scanner input = new Scanner(System.in);
		int[] capacities = new int[3];
		boolean gotInput = false;
		
		//Keep prompting for input until a valid input is attained.
		while(!gotInput) {
			try {
				for(int i = 0; i < 3; i++) {
					System.out.printf("Enter the capacity of jug %d: ", i + 1);
					
					int c = input.nextInt();
					
					//Only accept positive values for capacities.
					if(c >= 0)
						capacities[i] = c;
					else
						throw new Exception();
					
				}
				
				gotInput = true;
				
			} catch (Exception e) {
				System.out.println("There is a problem with your input, please try again.");
				input.nextLine(); //Flush the input.
			}
		}
		input.close();
		
		return capacities;
	}
	
	/**
	 * Prints the list of states of the water-jug problem in order of expansion.
	 * @param results The list of states to the printed.
	 */
	private static void printResults(ArrayList<int[]> results) {
		System.out.printf("States in order of expansion\n");
		
		for(int i = 0; i < results.size(); i++) 
			System.out.printf("%d: (%d, %d, %d)\n", i, results.get(i)[0],
													 results.get(i)[1],
													 results.get(i)[2]);
		
	}
}
