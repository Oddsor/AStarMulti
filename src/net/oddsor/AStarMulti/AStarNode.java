
package net.oddsor.AStarMulti;

import java.util.Collection;

/**
 * Any objects in a node-network need these functions.
 * @author Odd
 */
public interface AStarNode {
    /**
     * A collection of all neighbours of this node.
     * @return Node's neighbours
     */
    public <T extends AStarNode> Collection<T> getNeighbours();
    /**
     * To make AStar's estimation work so that it finds an optimal route we 
     * need to know the distance between nodes in the network.
     * @param node The node to compare against
     * @return The distance to target node as a double
     * @throws net.oddsor.AStarMulti.DistanceOutOfRangeException
     */
    public <T extends AStarNode> double getDistance(T node) throws Exception;
}
