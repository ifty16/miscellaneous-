import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class rough {
    public Vector<Integer> pathWithAssign(Integer city1, Integer city2, Integer troopSource) {
        // PRE: city1, city2 are existing cities; troopSource is an existing nation;
        // there is a complete valid assignment already
        // POST: Returns a path between city1 and city2 that has at least one city
        // assigned from troopSource,
        // if such a path exists, as a vector;
        // returns an empty vector otherwise

        if (!allCities.contains(city1) || !allCities.contains(city2)) {
            return new Vector<>();
        }

        // Use DFS to find path while checking for troopSource on-the-fly
        Vector<Integer> path = new Vector<>();
        Set<Integer> visited = new HashSet<>();

        if (findPathWithTroopSourceDFS(city1, city2, troopSource, path, visited)) {
            return path;
        }

        return new Vector<>();
    }

    private boolean findPathWithTroopSourceDFS(Integer current, Integer target, Integer troopSource,
            Vector<Integer> path, Set<Integer> visited) {
        // Add current city to path and mark as visited
        path.add(current);
        visited.add(current);

        // Check if current city has the required troop source
        boolean hasRequiredTroop = isAssigned(current) && getAssign(current).equals(troopSource);

        // If we reached target and found required troop source somewhere in path
        if (current.equals(target)) {
            // Check if any city in the current path has the required troop source
            for (Integer city : path) {
                if (isAssigned(city) && getAssign(city).equals(troopSource)) {
                    return true; // Found valid path
                }
            }
            // Remove current from path since this path doesn't work
            path.remove(path.size() - 1);
            visited.remove(current);
            return false;
        }

        // Explore neighbors
        Vector<Integer> neighbors = getNeighbours(current);
        for (Integer neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (findPathWithTroopSourceDFS(neighbor, target, troopSource, path, visited)) {
                    return true; // Found valid path through this neighbor
                }
            }
        }

        // Backtrack: remove current city from path and visited set
        path.remove(path.size() - 1);
        visited.remove(current);
        return false;
    }

    ///////////////////////
    ///
    ///
    public Vector<Integer> makePathWithAssign(Integer city1, Integer city2, Integer troopSource) {
        // PRE: city1, city2 are existing cities; troopSource is a nation;
        // there is NOT a complete valid assignment already
        // POST: Returns a path between city1 and city2 that has at least one city
        // assigned from troopSource,
        // if such a path exists, as a vector;
        // returns an empty vector otherwise

        // Basic validation
        if (!allCities.contains(city1) || !allCities.contains(city2) || !existsPath(city1, city2)) {
            return new Vector<>();
        }

        // Save initial assignments
        Map<Integer, Integer> initialAssignments = new HashMap<>(cityAssignments);

        // Try to find a path using optimized DFS approach
        Vector<Integer> result = findAndMakePathWithTroopSource(city1, city2, troopSource, initialAssignments);

        if (result.isEmpty()) {
            // Restore initial state if no solution found
            cityAssignments.clear();
            cityAssignments.putAll(initialAssignments);
        }

        return result;
    }

    private Vector<Integer> findAndMakePathWithTroopSource(Integer city1, Integer city2, Integer troopSource,
            Map<Integer, Integer> initialAssignments) {
        // Use DFS to find path while trying to assign troopSource
        Vector<Integer> path = new Vector<>();
        Set<Integer> visited = new HashSet<>();

        if (findPathAndAssignTroopSourceDFS(city1, city2, troopSource, path, visited, initialAssignments)) {
            // Complete the assignment for remaining cities
            giveAnyAssignment();

            // Verify the solution is valid and complete
            if (isValidAssign() && isEveryCityAssigned() && pathContainsTroopSource(path, troopSource)) {
                return path;
            }
        }

        return new Vector<>();
    }

    private boolean findPathAndAssignTroopSourceDFS(Integer current, Integer target, Integer troopSource,
            Vector<Integer> path, Set<Integer> visited,
            Map<Integer, Integer> initialAssignments) {
        // Add current city to path and mark as visited
        path.add(current);
        visited.add(current);

        // Check if we can assign troopSource to current city (if not already assigned)
        boolean assignedHere = false;
        if (!isAssigned(current) && canSafelyAssignNationToCity(current, troopSource)) {
            setAssign(current, troopSource);
            assignedHere = true;
        }

        // If we reached target, check if path has troopSource
        if (current.equals(target)) {
            if (pathContainsTroopSource(path, troopSource)) {
                return true; // Success!
            }
            // Cleanup and backtrack
            if (assignedHere) {
                cityAssignments.remove(current);
            }
            path.remove(path.size() - 1);
            visited.remove(current);
            return false;
        }

        // Explore neighbors
        Vector<Integer> neighbors = getNeighbours(current);
        for (Integer neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                if (findPathAndAssignTroopSourceDFS(neighbor, target, troopSource, path, visited, initialAssignments)) {
                    return true; // Found valid path through this neighbor
                }
            }
        }

        // Backtrack: remove assignments and path changes
        if (assignedHere) {
            cityAssignments.remove(current);
        }
        path.remove(path.size() - 1);
        visited.remove(current);
        return false;
    }
}
