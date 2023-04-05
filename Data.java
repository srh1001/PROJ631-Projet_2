package projet632_projet2_eclipse;
import java.util.*;

public class Data {
	
    private String id;
    private int size;

    public Data(String id, int size) {
        this.id = id;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
    
    public void placeData(Sysapp sys, NodeUser user_a, NodeUser user_b) {
        // Calculate shortest path to each system node for user_a
    	Map<String, Object> findClosestNode_a = user_a.findClosestNode(this.getId(), sys);
        Node closestNode_a = (Node) findClosestNode_a.get("closestNode");
 
        // Calculate shortest path to the node with data from user_b
        // using the closest node for user_a as an intermediate node
        LinkedHashMap<String, Object> shortestPath_b_to_closestNode_a = sys.getShortestPath(user_b.getId(), closestNode_a.getId());
        
        // Choose node with shortest total path length for both users
        Node finalNode = closestNode_a;
        Integer shortestLength_a = (Integer) findClosestNode_a.get("shortestLength");
        Integer shortestLength_b = (Integer) shortestPath_b_to_closestNode_a.get("length");
        Integer shortestTotalLength = shortestLength_a + shortestLength_b;
        
        // Iterate through all system nodes and find the one with the shortest path
        // length for both users
        LinkedHashMap<String, Node> nodes = (LinkedHashMap<String, Node>) shortestPath_b_to_closestNode_a.get("path");
        for (Node node : sys.getMapNodes().values()) {
            // Check if the node is a system node and has enough memory capacity to store the data
            if (node instanceof NodeSystem && ((NodeSystem) node).getMemoryCapacity() >= this.getSize()) {
               
                // Calculate shortest path to the node from user_a
            	Map<String, Object> result_a = sys.getShortestPath(user_a.getId(), node.getId());
                Integer length_a = (Integer) result_a.get("length");
              //  System.out.println("len shortest path from " + user_a.getId() + " to " + node.getId() + " : " + length_a);
               
                // Calculate shortest path to the node from user_b
                Map<String, Object> result_b = sys.getShortestPath(user_b.getId(), node.getId());
                Integer length_b = (Integer) result_b.get("length");
              //  System.out.println("len shortest path from " + user_b.getId() + " to " + node.getId() + " : " + length_b);

                // Check if the total path length through this node is shorter
                // than the current shortest total path length
            //    System.out.println("current shortestTotalLength : " + shortestTotalLength + " , new : " + length_a + length_b);
                if (length_a + length_b < shortestTotalLength)  {
                    shortestTotalLength = length_a + length_b;
                    finalNode = node;
                }
            }
        }
        
        // Add data to the final node with the shortest total path length
        ((NodeSystem) finalNode).addStoredData(this);
        System.out.println("Data " + this.getId() + " placed at system node " + finalNode.getId()) ;
    } 
}