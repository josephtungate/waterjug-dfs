/* WaterJugProblem.java
 * Implements a DFS algorithm for listing the set of all possible states for a given water-jug problem.
 * Author: Joseph Marcus Tungate - B913717
 * Last Updated: 26/11/2020
 */

import java.util.ArrayList;
import java.util.ArrayDeque;

public class WaterJugProblem {
	//Stores the capacities of each jug in the problem, i.e. capacities[0] is the capacity of the first jug.
	private int[] capacities;
	private int[] startState;
	
	/**
	 * Creates a water-jug problem for the given capacities and  start state.
	 * @param capacities An array of values where each value is the capacity of a jug.
	 * @param startState The starting state of the water-jug problem.
	 */
	public WaterJugProblem(int[] capacities, int[] startState) {
		if(capacities.length == startState.length) {
			this.capacities = capacities;
			this.startState = startState;
		} else 
			throw new IllegalArgumentException("The size of the capacities array and startState array must match");
	}
	
	/**
	 * Generates the set of all states for this instance's water-jug problem using DFS.
	 * @return A list containing all of the states in the water-jug problem in the order that they were visited.
	 */
	public ArrayList<int[]> getStates(){
		//List to hold all of the states.
		ArrayList<int[]> states = new ArrayList<int[]>();
		//A stack implementation of the fringe to facilitate the DFS.
		ArrayDeque<int[]> fringe = new ArrayDeque<int[]>();
		
		fringe.push(startState);
		
		//Expand the nodes on the fringe depth first and add to states list until fringe is empty.
		while(!fringe.isEmpty()) {
			int[] currentState = fringe.pop();
			
			//Only generate successor states if the state hasn't been expanded before.
			if(!containsState(states, currentState)) {
				states.add(currentState);
				
				//Add the successors of this state to the fringe.
				successors(currentState, fringe);
			}
		}
		
		return states;
	}
	
	/**
	 * Generates the successors of a given state and adds them to the given fringe.
	 * @param currentState The state whose successors are to be generated.
	 * @param fringe The fringe where the successors are to be added.
	 */
	private void successors(int[] currentState, ArrayDeque<int[]> fringe) {
		for (int primaryJug = 0; primaryJug < currentState.length; primaryJug++) {
			//Generate the states that arise from emptying and filling the primary jug.
			fringe.push(emptyJug(currentState, primaryJug));
			fringe.push(fillJug(currentState, primaryJug));
			
			for (int secondaryJug = 0; secondaryJug < currentState.length; secondaryJug++) {
				/*Generate the states that arise from attempting to pour the contents
				  of the primary jug to the other jugs. */
				if(primaryJug != secondaryJug) {
					fringe.push(pourJug(currentState, primaryJug, secondaryJug));
				}
			}
		}
	}
	
	/**
	 * Generates a successor state that arises when the given jug is emptied.
	 * @param state The state from which to generate the successor.
	 * @param jugIndex The index of the jug to be emptied.
	 * @return The state when the jug at jugIndex is emptied.
	 */
	private int[] emptyJug(int[] state, int jugIndex) {
		int[] s = state.clone();
		s[jugIndex] = 0;
		
		return s;
	}
	
	/**
	 * Generates a successor state that arises when the given jug is filled.
	 * @param state The state from which to generate the successor.
	 * @param jugIndex The index of the jug to be filled.
	 * @return The state when the jug at jugIndex is filled.
	 */
	private int[] fillJug(int[] state, int jugIndex) {
		int[] s = state.clone();
		s[jugIndex] = capacities[jugIndex];
		
		return s;
	}
	
	/**
	 * Generates the successor state that arises when the contents of the primary jug
	 * is poured into the secondary jug.
	 * @param currentState The state from which to generate the successor.
	 * @param primaryJug The index of the jug to be transfer liquid from.
	 * @param secondaryJug The index of the jug to transfer liquid to.
	 * @return The state when the contents of the primary jug is poured into the secondary jug.
	 */
	private int[] pourJug(int[] currentState, int primaryJug, int secondaryJug) {
		int[] s = currentState.clone();
		
		int availableVolume = capacities[secondaryJug] - currentState[secondaryJug];
		int volumeTransferred = currentState[primaryJug] < availableVolume ? currentState[primaryJug] : availableVolume;
		
		s[primaryJug] -= volumeTransferred;
		s[secondaryJug] += volumeTransferred;
		
		return s;
	}
	
	/**
	 * Performs a linear search on the given ArrayList of states to determine 
	 * whether it contains the key state.
	 * @param states List of states to search.
	 * @param key State to find within states.
	 * @return true if key exists within states, false otherwise.
	 */
	private boolean containsState(ArrayList<int[]> states, int[] key) {
		
		for(int[] state : states) {
			//Assume state is contained in states until proven otherwise.
			boolean found = true;
			
			for(int i = 0; i < key.length && found; i++) {
				if(state[i] != key[i]) {
					found = false;
					break;
				}
			}
			
			if(found)
				return true;
		}
		
		return false;
	}
}
