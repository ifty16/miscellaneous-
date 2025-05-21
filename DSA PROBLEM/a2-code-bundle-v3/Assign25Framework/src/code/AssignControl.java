package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AssignControl {


	// PROVIDED DATA
	
	TreeMap<Integer, Boolean> assignedTroopSource;  

	//my code
	private Map<Integer, Set<Integer>> cityNeighbors; // Map of city -> list of neighboring cities
	private Map<Integer, Integer> cityAssignments; // Map of city -> assigned nation
	private Set<Integer> allCities; // set of all cities and their neighbors

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

		//my code
		cityNeighbors = new HashMap<>(); // Initialize the map for city neighbors
		cityAssignments = new HashMap<>(); // Initialize the map for city assignments
		allCities = new HashSet<>(); // Initialize the map for all cities
		
	}

	public void instantiateMap(Vector<Road> roads, Vector<Assign> assigns) {
		// PRE: -
		// POST: New Mordor configuration is set up, including initial assignments
		
		// TODO

		cityNeighbors.clear(); // Clear the map of city neighbors
		cityAssignments.clear(); // Clear the map of city assignments
		allCities.clear(); // Clear the map of all cities
		assignedTroopSource.clear(); // Clear the map of assigned troop sources
		for (Road road : roads) {
			Integer city1 = road.getCity1();
			Integer city2 = road.getCity2();

			allCities.add(city1);
			allCities.add(city2);
			if (!cityNeighbors.containsKey(city1)) {
				cityNeighbors.put(city1, new HashSet<>());
			}
			cityNeighbors.get(city1).add(city2);

			if (!cityNeighbors.containsKey(city2)) {
				cityNeighbors.put(city2, new HashSet<>());
			}
			cityNeighbors.get(city2).add(city1);

			for(Assign assign : assigns) {
				setAssign(assign.getCity(), assign.getAssign());
			}
		}
	}

	public void setAssign(Integer city, Integer nation) {
		// PRE: city exists
		// POST: city is assigned troops from nation assign

		// TODO

		allCities.add(city); // Add the city to the set of all cities

		if ( !cityNeighbors.containsKey(city)) {
			cityNeighbors.put(city, new HashSet<>()); // Initialize the city in the neighbors map
		}

		cityAssignments.put(city, nation); // Assign the city to the nation


		
	}
	
	public Integer getAssign(Integer city) {
		// PRE: city exists
		// POST: Returns nation that city is assigned troops from,
		//         null if none

		// TODO
	
		return cityAssignments.get(city); // Return the assigned nation for the city
	}
	
	public Boolean isAssigned(Integer city) {
		// PRE: city exists
		// POST: Returns True if city has been assigned troops, false otherwise

		// TODO
		return cityAssignments.containsKey(city); // Check if the city has an assignment
	}
	
	public Boolean isNeighbour(Integer city1, Integer city2) {
		// PRE: city1, city2 exist
		// POST: Returns True if city1 is a neighbour of city2, false otherwise
		
		// TODO
		return cityNeighbors.get(city1).contains(city2); // Check if city1 is a neighbor of city2
	}
	
	public Vector<Integer> getNeighbours(Integer city) {
		// PRE: city exists
		// POST: Returns vector of cities that are neighbours of city
		//         (empty vector if no neighbours)
		
		// TODO

		Vector<Integer> neighbours = new Vector<>();
		if (cityNeighbors.containsKey(city)) {
			neighbours.addAll(cityNeighbors.get(city)); // Get the neighbors of the city
		}
		return neighbours;
	}
	
	public Boolean isAssignedSame(Integer city1, Integer city2) {
		// PRE: city1, city2 exist
		// POST: Returns True if city1 and city2 are assigned troops from the same nation, 
		//         False otherwise

		// TODO

		if(isAssigned(city1) && isAssigned(city2)) {
			return getAssign(city1).equals(getAssign(city2)); // Check if both cities are assigned to the same nation
		}
		return false; // If either city is not assigned, return false
	}
	
	
	public Boolean isValidAssign() {
		// PRE: -
		// POST: Returns True if troop assignment is valid, False otherwise
		
		// TODO

		for (Integer city : allCities) {
			if(!isAssigned(city)) {
				continue; // Skip if the city is not assigned
			}

			Vector<Integer> neighbours = getNeighbours(city);
			for (Integer neighbour : neighbours) {
				if(!isAssigned(neighbour)) {
					continue; // Skip if the neighbour is not assigned
				}
				if(isAssignedSame(city, neighbour)) {
					return false; // If the city and its neighbour are assigned to the same nation, return false
				}
			}
		}
		return true;
	}
	
	public Integer numDiffAssigns() {
		// PRE: -
		// POST: Returns the number of different nations from which troops are assigned
		
		// TODO


		Set<Integer> uniqueNations = new HashSet<>();
		for(Integer nation : cityAssignments.values()) {
			uniqueNations.add(nation); 
		}
		return uniqueNations.size(); 
	}
	
	public Boolean isEveryCityAssigned() {
		// PRE: -
		// POST: Returns True if every city is assigned some troops, False otherwise
		
		// TODO

		for (Integer city : allCities) {
			if (!isAssigned(city)) {
				return false; 
			}
		}
		return true;
	}

	public void giveAnyAssignment() {
		// PRE: -
		// POST: Gives a valid assignment of troops to every city that does not already have troops assigned
		
		// TODO
		for (Integer city : allCities) {
			if(isAssigned(city)) {
				continue; 
			}
			assignCity(city);
		}
	}
	
	// CREDIT
	
	public Boolean existsPath(Integer city1, Integer city2) {
		// PRE: city1 and city2 exist
		// POST: Returns True if there is a path between city1 and city2, False otherwise
		
		// TODO
		if(city1.equals(city2)) {
			return true;
		}

		//lets bfs
		Set<Integer> visited = new HashSet<>();
		Queue<Integer> queue = new LinkedList<>();
		queue.add(city1);
		visited.add(city1);
		while(!queue.isEmpty()) {
			Integer currentCity = queue.poll();
			Vector<Integer> neighbours = getNeighbours(currentCity);

			for(Integer neighbour : neighbours) {
				if(neighbour.equals(city2)) {
					return true; 
				}
				if(!visited.contains(neighbour)) {
					queue.add(neighbour); 
					visited.add(neighbour); 
				}
			}
		}
		return false;
	}

	public void assignCity(Integer city) {
		// PRE: city not assigned
		// POST: Troops from some nation are validly assigned to city 
		
		// TODO

		if(isAssigned(city)) {
			return; // If the city is already assigned, return
		}
		Set<Integer> assaignedNations = new HashSet<>();

		for (Integer neighbour : getNeighbours(city)) {
			if (isAssigned(neighbour)) {
				assaignedNations.add(getAssign(neighbour)); // Add the assigned nation of the neighbour
			}
		}

		int nation =1 ;
		while (assaignedNations.contains(nation)) {
			nation++; // Find the first nation that is not assigned to any neighbour
		}
		setAssign(city, nation); // Assign the city to the found nation
	}

	public void assignCityLowest(Integer city) {
		// PRE: city not assigned
		// POST: Troops from the lowest possible numbered nation are validly assigned to city 

		// TODO

		if(isAssigned(city)) {
			return; 
		}
		Set<Integer> assignedNations = new HashSet<>();
		Vector<Integer> neighbours = getNeighbours(city);
		for (Integer neighbour : neighbours) {
			if (isAssigned(neighbour)) {
				assignedNations.add(getAssign(neighbour)); // Add the assigned nation of the neighbour
			}
		}

		int lowestNation = 1;
		while (assignedNations.contains(lowestNation)) {
			lowestNation++; // Find the lowest nation that is not assigned to any neighbour
		}

		setAssign(city, lowestNation); 
	}
	
	
	// DISTINCTION
	
	public void giveGreedyCityOrderingAssignment() {
		// PRE: -
		// POST: Assigns troops greedily, starting at city1 and continuing to cityN,
		//         subject to constraints 

		// TODO

		List<Integer> sortedCities = new ArrayList<>(allCities);
		Collections.sort(sortedCities);

		for(Integer city : sortedCities){
			if (!isAssigned(city)) {
                assignCityLowest(city);
            }
		}
		
	}
	
	public void giveGreedyRoadOrderingAssignment() {
		// PRE: -
		// POST: Assigns troops greedily, starting at with most roads and working through to least
		//         (breaking ties by city ordering, so city i before city j if same number of
		//         roads and i < j), subject to constraints 

		// TODO

		List<Map.Entry<Integer, Integer>> cityRoadCounts = new ArrayList<>();

		for (Integer city : allCities){
			int roadCount = getNeighbours(city).size();
			cityRoadCounts.add(new AbstractMap.SimpleEntry<>(city, roadCount));
		}

		Collections.sort(cityRoadCounts, (entry1, entry2) -> {
            int roadCountCompare = Integer.compare(entry2.getValue(), entry1.getValue());
            if (roadCountCompare != 0) {
                return roadCountCompare;  // Sort by road count (descending)
            } else {
                return Integer.compare(entry1.getKey(), entry2.getKey());  // Sort by city number (ascending)
            }
        });

		for (Map.Entry<Integer, Integer> entry : cityRoadCounts) {
            Integer city = entry.getKey();
            // Skip cities that already have assignments
            if (!isAssigned(city)) {
                assignCityLowest(city);
            }
        }
	}
	
	// Do assignWithNSources
	
	public Boolean canDoWithNSources(Integer N) {
		// PRE: -
		// POST: Returns True if there is some complete and valid assignment of troops that uses at most N nations,
		//         instantiating the map with such a complete and valid assignment;
		//         False otherwise
		
		// TODO

		Map<Integer, Integer> savedAssaignment = new HashMap<>(cityAssignments);
		
		if(backtrackAssign(new ArrayList<>(allCities), 0, N)) {
			return true; // If a valid assignment is found, return true
		}
		else{
			cityAssignments = savedAssaignment; // Restore the original assignments
			return false; // If no valid assignment is found, return false
		}

	}
	//implement the custom backtrackAssign method
	private boolean backtrackAssign(List<Integer> cities, int index, int maxNations) {
		if (index >= cities.size()) {
			return numDiffAssigns() <= maxNations && isValidAssign(); // Check if the assignment is valid and within the limit
		}

		Integer city = cities.get(index);

		if (isAssigned(city)) {
			return backtrackAssign(cities, index + 1, maxNations); // Skip to the next city if already assigned
		}

		Set<Integer> forbiddenNations = new HashSet<>();
		Vector<Integer> neighbours = getNeighbours(city);

		for (Integer neighbour : neighbours) {
			if (isAssigned(neighbour)) {
				forbiddenNations.add(getAssign(neighbour)); 
			}
		}

		Set<Integer> usedNations = new HashSet<>();
		for(Integer assignedCity :cityAssignments.keySet()) {
			usedNations.add(getAssign(assignedCity)); // Get the used nations
		}

		for (Integer nation : usedNations) {
            if (!forbiddenNations.contains(nation)) {
                setAssign(city, nation);
                if (backtrackAssign(cities, index + 1, maxNations)) {
                    return true;
                }
                cityAssignments.remove(city);  // Undo assignment
            }
        }

		// If adding a new nation would exceed maxNations, no need to try
        if (usedNations.size() >= maxNations) {
            return false;
        }

		// Try a new nation (lowest possible)
        int newNation = 1;
        while (usedNations.contains(newNation) || forbiddenNations.contains(newNation)) {
            newNation++;
        }
        
        setAssign(city, newNation);
        if (backtrackAssign(cities, index + 1, maxNations)) {
            return true;
        }
        cityAssignments.remove(city);  // Undo assignment
        
        return false;
	}
	
	public Boolean canFindBetterSoln() {
		// PRE: There is some initial complete assignment of troops
		// POST: Returns True if there is some complete and valid assignment of troops that is better than the initial,
		//         instantiating the map with such a complete and valid assignment;
		//         False otherwise
		
		// TODO
		int initialNumDiffAssigns = numDiffAssigns();
		Map<Integer, Integer> savedAssignments = new HashMap<>(cityAssignments);
		boolean success = canDoWithNSources(initialNumDiffAssigns - 1);

		if (success) {
			return true; 
		} else {
			cityAssignments = savedAssignments; 
			return false; 
		}
		// return null;
	}
	
	// HIGH DISTINCTION


	public Vector<Integer> pathWithAssign(Integer city1, Integer city2, Integer troopSource) {
		// PRE: city1, city2 are existing cities; troopSource is an existing nation;
		//        there is a complete valid assignment already 
		// POST: Returns a path between city1 and city2 that has at least one city assigned from troopSource,
		//         if such a path exists, as a vector;
		//         returns an empty vector otherwise

		// TODO

		if(!allCities.contains(city1) || !allCities.contains(city2)) {
			return new Vector<>(); 
		}

		if(!existsPath(city1, city2)){
			return new Vector<>();
		}


		Queue<Integer> queue = new LinkedList<>();
		Map<Integer, Integer> prev = new HashMap<>();
		Set<Integer> visited = new HashSet<>();

		queue.add(city1);
		visited.add(city1);
		prev.put(city1, null);

		boolean foundPath = false;
		boolean pathHasTroopSource = false;

		while (!queue.isEmpty() && !foundPath) {
			Integer current = queue.poll();
			if(isAssigned(current) && getAssign(current).equals(troopSource)) {
				pathHasTroopSource = true; // Found a city with the troop source
			}

			if(current.equals(city2) && pathHasTroopSource) {
				foundPath = true; 
				break;
			}

			Vector<Integer> neighbours = getNeighbours(current);
			for (Integer neighbour : neighbours) {
				if (!visited.contains(neighbour)) {
					
					visited.add(neighbour);
					prev.put(neighbour, current);
					queue.add(neighbour);

					if(neighbour.equals(city2) && (isAssigned(neighbour) && getAssign(neighbour).equals(troopSource) || pathHasTroopSource)) {
						foundPath = true; 
						break;
					}
				}
			}
		}

		if(!foundPath) {
			queue.clear(); // Clear the queue if no path is found
			visited.clear(); // Clear the visited set
			prev.clear(); // Clear the previous map

			queue.add(city1);
			visited.add(city1);
			prev.put(city1, null);

			while (!queue.isEmpty()) {
				Integer current = queue.poll();
				Vector<Integer> neighbours = getNeighbours(current);
				for (Integer neighbour : neighbours) {
					if (!visited.contains(neighbour)) {
						visited.add(neighbour);
						prev.put( neighbour , current);
						queue.add( neighbour );

						if (neighbour.equals(city2)) {
							Vector<Integer> path = reconstructPath(prev, city1, city2);
							for( Integer city :  path) {
								if (isAssigned(city) && getAssign(city).equals(troopSource)) {
									return path; // Return the path if it contains the troop source
								}
							}
						}
					}
				}
			}
			return new Vector<>(); 
		}

		return reconstructPath(prev, city1, city2); 
		// return null;
	}

	private Vector<Integer> reconstructPath(Map<Integer, Integer> prev, Integer start, Integer end) {
		Vector<Integer> path = new Vector<>();
		Integer current = end;

		while (current != null) {
			path.add(0, current); 
			current = prev.get(current);
		}

		return path;
	}

	public Vector<Integer> makePathWithAssign(Integer city1, Integer city2, Integer troopSource) {
        // PRE: city1, city2 are existing cities; troopSource is a nation;
        //      there is NOT a complete valid assignment already 
        // POST: Returns a path between city1 and city2 that has at least one city assigned from troopSource,
        //       if such a path exists, as a vector;
        //       returns an empty vector otherwise
        
        // Check if cities exist
        if (!allCities.contains(city1) || !allCities.contains(city2)) {
            return new Vector<>();
        }
        
        // First, find a path between city1 and city2
        if (!existsPath(city1, city2)) {
            return new Vector<>();
        }
        
        // Save current assignments
        Map<Integer, Integer> savedAssignments = new HashMap<>(cityAssignments);
        
        // Find a path from city1 to city2
        Vector<Integer> path = findPathBetween(city1, city2);
        
        if (path.isEmpty()) {
            return new Vector<>();  // No path exists
        }
        
        // Check if the path already has a city assigned from troopSource
        boolean hasTroopSource = false;
        for (Integer city : path) {
            if (isAssigned(city) && getAssign(city).equals(troopSource)) {
                hasTroopSource = true;
                break;
            }
        }
        
        // If path doesn't have troopSource, try to assign it to an unassigned city
        if (!hasTroopSource) {
            boolean assigned = false;
            for (Integer city : path) {
                if (!isAssigned(city)) {
                    // Check if assigning troopSource to this city would be valid
                    boolean canAssign = true;
                    Vector<Integer> neighbors = getNeighbours(city);
                    for (Integer neighbor : neighbors) {
                        if (isAssigned(neighbor) && getAssign(neighbor).equals(troopSource)) {
                            canAssign = false;
                            break;
                        }
                    }
                    
                    if (canAssign) {
                        setAssign(city, troopSource);
                        assigned = true;
                        break;
                    }
                }
            }
            
            // If couldn't assign troopSource to any city on path
            if (!assigned) {
                // Restore original assignments
                cityAssignments = savedAssignments;
                
                // Try to complete the assignment with the constraint that at least one city on the path
                // must be assigned to troopSource
                for (Integer city : path) {
                    if (!isAssigned(city)) {
                        setAssign(city, troopSource);
                        break;
                    }
                }
                
                // Complete the assignment
                giveAnyAssignment();
                
                // Check if the final assignment is valid
                if (!isValidAssign()) {
                    // Restore original assignments and return empty path
                    cityAssignments = savedAssignments;
                    return new Vector<>();
                }
            }
        }
        
        // Complete the assignment
        giveAnyAssignment();
        
        return path;
    }

	private Vector<Integer> findPathBetween(Integer city1, Integer city2) {
        // Use BFS to find a path between city1 and city2
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> prev = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        
        queue.add(city1);
        visited.add(city1);
        prev.put(city1, null);
        
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            Integer current = queue.poll();
            
            if (current.equals(city2)) {
                found = true;
                break;
            }
            
            Vector<Integer> neighbors = getNeighbours(current);
            for (Integer neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    prev.put(neighbor, current);
                }
            }
        }
        
        // If no path found, return empty vector
        if (!found) {
            return new Vector<>();
        }
        
        // Reconstruct path
        return reconstructPath(prev, city1, city2);
    }



	// MAIN

	public static void main(String[] args) {
		AssignControl g = new AssignControl();
		Vector<Road> rList;
		Vector<Assign> aList;
		
	
		try {

			String dataDir = "D:/academics/miscellenius/DSA PROBLEM/a2-sample-data/data";
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
