package projet632_projet2_eclipse;

import java.util.*;

public class NodeUser extends Node {
    private String id;
    private HashMap<String, Data> interestData = new HashMap<String, Data> ();

    
    public NodeUser(String id) {
    	super(id); // call the constructor of the superclass with the id argument
    	this.id = id;
    }
    
    public NodeUser(String id, HashMap<String, Data> interestData) {
    	super(id); // call the constructor of the superclass with the id argument
        this.id = id;
    	this.interestData = interestData;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, Data> getInterestData() {
        return interestData;
    }
    
    public void addInterestData(Data data) {
    	interestData.put(data.getId(), data);
    }
    
    public Map<String, Object> findClosestNode(String idData, Sysapp sys) {
        // Get the data object with the given ID
        Data data = this.interestData.get(idData);
        if (data == null) {
            System.out.println();
            throw new IllegalStateException("Error: data with ID " + idData + " does not exist for this user.");
        }

        // Initialize variables to keep track of the shortest path and closest node
        Map<String, Object> shortestPath = null;
        Integer shortestLength = Integer.MAX_VALUE;
        NodeSystem closestNode = null;

        // Iterate through all nodes in the system
        for (Node node : sys.getMapNodes().values()) {
            // If the node is a system node and has enough memory capacity for the data, then check its shortest path
            if (node instanceof NodeSystem && ((NodeSystem) node).getMemoryCapacity() >= data.getSize()) {
                
                try {
                    // Get the shortest path from this user node to the current system node
                    Map<String, Object> result = sys.getShortestPath(this.getId(), node.getId());
                    Integer length = (Integer) result.get("length");

                    // If the path is shorter than the current shortest path, update the variables
                    if (length < shortestLength) {
                        shortestPath = result;
                        shortestLength = length;
                        closestNode = (NodeSystem) node;
                    }
                } catch (IllegalStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        // Create a map containing the shortest path, shortest length, and closest node
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("shortestPath", shortestPath);
        m.put("shortestLength", shortestLength);
        m.put("closestNode", closestNode);
        return m;
    }

    public void placeData(String idData, Sysapp sys) {

        // Get the data object with the given ID
        Data data = this.interestData.get(idData);
        if (data == null) {
            System.out.println();
            throw new IllegalStateException("Error: data with ID " + idData + " does not exist for this user.");
        }
        
        // Find the system node closest to the user that has enough memory capacity to store the data
        Map<String, Object> result = this.findClosestNode(idData, sys);
        Node closestNode = (Node) result.get("closestNode");
        
        // Add data to the system node with the shortest path
        if (closestNode != null) {
            ((NodeSystem) closestNode).addStoredData(data);
            System.out.println("Data " + data.getId() + " placed at system node " + closestNode.getId() + " via path " + result.get("shortestPath"));
        } else {
            System.out.println("No system node with enough empty space for your data was found.");
        }
    }
}
