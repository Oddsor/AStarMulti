

package net.oddsor.AStarMulti;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * This is an AStar-implementation that lets you specify one or 
 * multiple goals. I ended up needing this for a project, and figured
 * having a simple library would be beneficial.
 * 
 * @author Odd
 */
public class AStarMulti {
    public static Queue<AStarNode> getRoute(Collection<? extends AStarNode> goals, 
            AStarNode start) throws Exception{
        //TODO figure out if nullpointerexceptions 'handle themselves'.
        if(goals.isEmpty()){
            throw new Exception("No goals in list");
        }
        Set closedSet = new HashSet();
        Set openSet = new HashSet();
        Map<AStarNode, AStarNode> cameFrom = new HashMap<>();
        openSet.add(start);
        Map<AStarNode, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);
        Map<AStarNode, Double> fScore = new HashMap<>();
        fScore.put(start, getShortestDistance(start, goals));
        
        int cunter = 0;
        while(!openSet.isEmpty()){
            cunter++;
            System.out.println("Round " + cunter);
            AStarNode current = getLowest(fScore, openSet);
            for(AStarNode goal: goals){
                if(current.equals(goal)) return reconstructPath(cameFrom, goal);
            }
            openSet.remove(current);
            closedSet.add(current);
            
            Collection<AStarNode> neighbours = current.getNeighbours();
            for(AStarNode neighbour: neighbours){
                if(closedSet.contains(neighbour)){
                    System.out.println("skip");
                    continue;
                }
                double tentativeG = gScore.get(current) + 
                        current.getDistance(neighbour);
                if(!openSet.contains(neighbour) || tentativeG < gScore.get(current)){
                    cameFrom.put(neighbour, current);
                    gScore.put(neighbour, tentativeG);
                    fScore.put(neighbour, tentativeG + 
                            getShortestDistance(neighbour, goals));
                    openSet.add(neighbour);
                }
            }
        }
        return null;
    }
    
    private static Queue<AStarNode> reconstructPath(Map<AStarNode, AStarNode> cameFrom, AStarNode current) {
        Queue<AStarNode> nodes = new ArrayDeque<>();
        if(cameFrom.containsKey(current)){
            nodes.addAll(reconstructPath(cameFrom, cameFrom.get(current)));
        }
        nodes.add(current);
        return nodes;
    }
    
    private static AStarNode getLowest(Map<AStarNode, Double> scores, Set<AStarNode> openSet){
        double shortestDistance = 100000000.0;
        AStarNode shortestNode = null;
        for(AStarNode node: scores.keySet()){
            if(scores.get(node) < shortestDistance && openSet.contains(node)){
                shortestNode = node;
                shortestDistance = scores.get(node);
            }
        }
        return shortestNode;
    }
    
    private static double getShortestDistance(AStarNode start, 
            Collection<? extends AStarNode> goals) throws Exception{
        Iterator<? extends AStarNode> it = goals.iterator();
        double distance = 1000000000.0;
        while (it.hasNext()){
            double tempDistance = start.getDistance(it.next());
            if(distance > tempDistance) distance = tempDistance;
        }
        return distance;
    }
}
