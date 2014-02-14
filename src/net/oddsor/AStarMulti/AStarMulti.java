

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
    public static <Node extends AStarNode> Queue<Node> getRoute(Collection<Node> goals, 
            Node start) throws Exception{
        //TODO figure out if nullpointerexceptions 'handle themselves'.
        if(goals.isEmpty()){
            throw new Exception("No goals in list");
        }
        Set closedSet = new HashSet();
        Set openSet = new HashSet();
        Map<Node, Node> cameFrom = new HashMap<>();
        openSet.add(start);
        Map<Node, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);
        Map<Node, Double> fScore = new HashMap<>();
        fScore.put(start, getShortestDistance(start, goals));
        
        while(!openSet.isEmpty()){
            Node current = (Node) getLowest(fScore, openSet);
            for(Node goal: goals){
                if(current.equals(goal)) return reconstructPath(cameFrom, goal);
            }
            openSet.remove(current);
            closedSet.add(current);
            
            Collection<Node> neighbours = current.getNeighbours();
            for(Node neighbour: neighbours){
                if(closedSet.contains(neighbour)){
                    continue;
                }
                double currentDistance = current.getDistance(neighbour);
                if(currentDistance < 0.0) throw new DistanceOutOfRangeException(
                        "Distance from current to neighbour isn't a positive number");
                double tentativeG = gScore.get(current) + 
                        currentDistance;
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
    
    private static <Node extends AStarNode> Queue<Node> 
        reconstructPath(Map<Node, Node> cameFrom, Node current) 
        {
        Queue<Node> nodes = new ArrayDeque<>();
        if(cameFrom.containsKey(current)){
            nodes.addAll(reconstructPath(cameFrom, cameFrom.get(current)));
        }
        nodes.add(current);
        return nodes;
    }
    
    private static <Node extends AStarNode> Node getLowest(Map<Node, Double> scores, Set<Node> openSet){
        double shortestDistance = 100000000.0;
        Node shortestNode = null;
        for(Node node: scores.keySet()){
            if(scores.get(node) < shortestDistance && openSet.contains(node)){
                shortestNode = node;
                shortestDistance = scores.get(node);
            }
        }
        return shortestNode;
    }
    
    private static <Node extends AStarNode> double getShortestDistance(Node start, 
            Collection<Node> goals) throws Exception{
        Iterator<Node> it = goals.iterator();
        double distance = 1000000000.0;
        while (it.hasNext()){
            double tempDistance = start.getDistance(it.next());
            if(tempDistance < 0.0) throw new DistanceOutOfRangeException("Distance between nodes negative!");
            if(distance > tempDistance) distance = tempDistance;
        }
        return distance;
    }
}