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

    public void placeData(String idData, Sysapp sys) {
        // Get the data object with the given ID
        Data data = this.interestData.get(idData);
        if (data == null) {
            System.out.println("Error: data with ID " + idData + " does not exist for this user.");
            return;
        }
        
        // Calculate shortest path to each system node
        Map<String, Object> shortestPath = null;
        Integer shortestLength = Integer.MAX_VALUE;
        NodeSystem closestNode = null;
        for (Node node : sys.getMapNodes().values()) {
            if (node instanceof NodeSystem && ((NodeSystem) node).getMemoryCapacity() >= data.getSize()) {
            	
            	try {
                    Map<String, Object> result = sys.getShortestPath(this.getId(), node.getId());
                    Integer length = (Integer) result.get("length");
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
        
        
        
        // Add data to system node with shortest path
        if (closestNode != null) {
        closestNode.addStoredData(data);
        System.out.println("Data " + data.getId() + " placed at system node " + closestNode.getId() + " via path " + shortestPath);
        }else {
        	System.out.println("No system node with enough empty space for your data was found.");
        }
    }
}
