package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class AssignControl {


	// PROVIDED DATA
	
	TreeMap<Integer, Boolean> assignedTroopSource;  

	// PROVIDED CODE
	
	public Vector<Road> readRoadsFromFile(File f) throws IOException {
		// Reads list of edges from a file, one pair of integers per line 
		BufferedReader fIn = new BufferedReader(
							 new FileReader(f));
		String s;
		Vector<Road> rList = new Vector<Road>();
		Integer x, y;
		
		while ((s = fIn.readLine()) != null) {
			java.util.StringTokenizer line = new java.util.StringTokenizer(s);
			while (line.hasMoreTokens()) {
				x = Integer.parseInt(line.nextToken());
				y = Integer.parseInt(line.nextToken());
				rList.add(new Road(x,y));
			}
		}
		fIn.close();
		
		return rList;
	}

	public Vector<Assign> readAssignsFromFile(File f) throws IOException {
		// Reads list of edges from a file, one pair of integers per line 
		BufferedReader fIn = new BufferedReader(
							 new FileReader(f));
		String s;
		Vector<Assign> rList = new Vector<Assign>();
		Integer x, y;
		
		while ((s = fIn.readLine()) != null) {
			java.util.StringTokenizer line = new java.util.StringTokenizer(s);
			while (line.hasMoreTokens()) {
				x = Integer.parseInt(line.nextToken());
				y = Integer.parseInt(line.nextToken());
				rList.add(new Assign(x,y));
			}
		}
		fIn.close();
		
		return rList;
	}

	public Boolean clashExists(Integer[] cities) {
		// PRE: cities is an array of existing cities
		// POST: Returns True if any assignments at cities clash,
		//         False otherwise
		
		Vector<Integer> neighbours;
		for (Integer c: cities) {
			neighbours = this.getNeighbours(c);
			for (Integer n: neighbours) {
				if (this.isAssigned(c) &&
						this.isAssigned(n) &&
						this.getAssign(c) == this.getAssign(n))
					return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}

	public Integer countAssignedTroopSource(Integer[] cities) {
		// PRE: cities is an array of existing cities
		// POST: Returns the count of nations with troops assigned

		assignedTroopSource.clear();
		
		for (Integer c: cities) {
			if (this.getAssign(c) != null)
				assignedTroopSource.put(this.getAssign(c), Boolean.TRUE);
		}
		
		return this.assignedTroopSource.size();
	}
	
	public void setAllAssigns(Integer[] assigns) {
		// PRE: assigns has length N, where N is number of cities
		// POST: Sets troop assignment for city i to assigns[i-1]
		
		for (int i = 0; i < assigns.length; i++)
			this.setAssign(i+1, assigns[i]);
		
		// NOTE: This will obviously only work as intended once setAssign() is implemented.
	}
	

	// PASS
	
	public AssignControl() {
		// Constructor
		
		// TODO: Add other initialisation
		
		assignedTroopSource = new TreeMap<Integer, Boolean>();
	}

	public void instantiateMap(Vector<Road> roads, Vector<Assign> assigns) {
		// PRE: -
		// POST: New Mordor configuration is set up, including initial assignments
		
		// TODO
	}

	public void setAssign(Integer city, Integer nation) {
		// PRE: city exists
		// POST: city is assigned troops from nation assign

		// TODO
	}
	
	public Integer getAssign(Integer city) {
		// PRE: city exists
		// POST: Returns nation that city is assigned troops from,
		//         null if none

		// TODO
		return null;
	}
	
	public Boolean isAssigned(Integer city) {
		// PRE: city exists
		// POST: Returns True if city has been assigned troops, false otherwise

		// TODO
		return null;
	}
	
	public Boolean isNeighbour(Integer city1, Integer city2) {
		// PRE: city1, city2 exist
		// POST: Returns True if city1 is a neighbour of city2, false otherwise
		
		// TODO
		return null;
	}
	
	public Vector<Integer> getNeighbours(Integer city) {
		// PRE: city exists
		// POST: Returns vector of cities that are neighbours of city
		//         (empty vector if no neighbours)
		
		// TODO
		return null;
	}
	
	public Boolean isAssignedSame(Integer city1, Integer city2) {
		// PRE: city1, city2 exist
		// POST: Returns True if city1 and city2 are assigned troops from the same nation, 
		//         False otherwise

		// TODO
		return null;
	}
	
	
	public Boolean isValidAssign() {
		// PRE: -
		// POST: Returns True if troop assignment is valid, False otherwise
		
		// TODO
		return null;
	}
	
	public Integer numDiffAssigns() {
		// PRE: -
		// POST: Returns the number of different nations from which troops are assigned
		
		// TODO
		return null;
	}
	
	public Boolean isEveryCityAssigned() {
		// PRE: -
		// POST: Returns True if every city is assigned some troops, False otherwise
		
		// TODO
		return null;
	}

	public void giveAnyAssignment() {
		// PRE: -
		// POST: Gives a valid assignment of troops to every city that does not already have troops assigned
		
		// TODO
	}
	
	// CREDIT
	
	public Boolean existsPath(Integer city1, Integer city2) {
		// PRE: city1 and city2 exist
		// POST: Returns True if there is a path between city1 and city2, False otherwise
		
		// TODO
		return null;
	}

	public void assignCity(Integer city) {
		// PRE: city not assigned
		// POST: Troops from some nation are validly assigned to city 
		
		// TODO
	}

	public void assignCityLowest(Integer city) {
		// PRE: city not assigned
		// POST: Troops from the lowest possible numbered nation are validly assigned to city 

		// TODO
	}
	
	
	// DISTINCTION
	
	public void giveGreedyCityOrderingAssignment() {
		// PRE: -
		// POST: Assigns troops greedily, starting at city1 and continuing to cityN,
		//         subject to constraints 

		// TODO
	}
	
	public void giveGreedyRoadOrderingAssignment() {
		// PRE: -
		// POST: Assigns troops greedily, starting at with most roads and working through to least
		//         (breaking ties by city ordering, so city i before city j if same number of
		//         roads and i < j), subject to constraints 

		// TODO
	}
	
	// Do assignWithNSources
	
	public Boolean canDoWithNSources(Integer N) {
		// PRE: -
		// POST: Returns True if there is some complete and valid assignment of troops that uses at most N nations,
		//         instantiating the map with such a complete and valid assignment;
		//         False otherwise
		
		// TODO
		return null;
	}
	
	public Boolean canFindBetterSoln() {
		// PRE: There is some initial complete assignment of troops
		// POST: Returns True if there is some complete and valid assignment of troops that is better than the initial,
		//         instantiating the map with such a complete and valid assignment;
		//         False otherwise
		
		// TODO
		return null;
	}
	
	// HIGH DISTINCTION


	public Vector<Integer> pathWithAssign(Integer city1, Integer city2, Integer troopSource) {
		// PRE: city1, city2 are existing cities; troopSource is an existing nation;
		//        there is a complete valid assignment already 
		// POST: Returns a path between city1 and city2 that has at least one city assigned from troopSource,
		//         if such a path exists, as a vector;
		//         returns an empty vector otherwise

		// TODO
		return null;
	}

	public Vector<Integer> makePathWithAssign(Integer city1, Integer city2, Integer troopSource) {
		// PRE: city1, city2 are existing cities; troopSource is a nation;
		//        there is NOT a complete valid assignment already 
		// POST: Returns a path between city1 and city2 that has at least one city assigned from troopSource,
		//         if such a path exists, as a vector;
		//         returns an empty vector otherwise

		// TODO
		return null;
	}



	// MAIN

	public static void main(String[] args) {
		AssignControl g = new AssignControl();
		Vector<Road> rList;
		Vector<Assign> aList;
		
	
		try {

			String dataDir = "C:\\Users\\madra\\OneDrive\\Documents\\Documents\\teaching\\25comp2010\\ass\\data";
			// make sure there's a file separator at the end
			String fileBaseName = "sample1";
			FileNames fNames = new FileNames(dataDir, fileBaseName);

			rList = g.readRoadsFromFile(fNames.getRoadFile());
			aList = g.readAssignsFromFile(fNames.getAssignFile());
			g.instantiateMap(rList, aList);

			
			long start = System.nanoTime();
			g.setAssign(1, 5);
			long finish = System.nanoTime();
			double timeElapsed = (double) (finish - start) / 1000000; // in milliseconds
			System.out.print("Milliseconds elapsed: ");
			System.out.println(timeElapsed);

			
		}
		catch (IOException e) {
			System.out.println("in exception: " + e);
		}
		
	}
	
}
