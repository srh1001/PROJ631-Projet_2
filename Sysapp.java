package projet632_projet2_eclipse;

import java.util.*;

public class Sysapp { // a Sysapp is a graph in this code.
	
	private HashMap<String, Node> mapNodes = new HashMap<String, Node> (); // nodes of the graph, the key is the ID of the node.
	
	
	public Sysapp() {
	}
	
	public Sysapp(HashMap<String, Node> mapNodes) {
		this.mapNodes = mapNodes;
	}
	
	public HashMap<String, Node> getMapNodes(){
		return this.mapNodes;
	}
	
	
	public void addNode(Node node) {
		this.mapNodes.put(node.getId(), node);
	}
	
	
	public LinkedHashMap<String, Object> getShortestPath(String startNodeId, String endNodeId) {
	    // Find the start and end nodes
	    Node startNode = mapNodes.get(startNodeId);
	    Node endNode = mapNodes.get(endNodeId);
	    if (startNode == null || endNode == null) {
	        throw new IllegalArgumentException("Start or end node not found.");
	    }
	    
	    // Run Dijkstra's algorithm to find the shortest path
	    Map<Node, Integer> distances = new HashMap<>();
	    Map<Node, Node> parents = new HashMap<>();
	    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
	    for (Node node : mapNodes.values()) {
	        distances.put(node, Integer.MAX_VALUE);
	        parents.put(node, null);
	        queue.offer(node);
	    }
	    distances.put(startNode, 0);
	    queue.remove(startNode);
	    queue.offer(startNode);
	    while (!queue.isEmpty()) {
	        Node current = queue.poll();
	        if (current == endNode) {
	            break;
	        }
	        for (Pair<Node, Integer> neighbor : current.getNeighbours().values()) {
	            Node neighborNode = neighbor.getFirst();
	            int distance = neighbor.getSecond();
	            int newDistance = distances.get(current) + distance;
	            if (newDistance < distances.get(neighborNode)) {
	                distances.put(neighborNode, newDistance);
	                parents.put(neighborNode, current);
	                queue.remove(neighborNode);
	                queue.offer(neighborNode);
	            }
	        }
	    }
	    
	    // Check if a path exists
	    if (parents.get(endNode) == null) {
	    	throw new IllegalStateException("No path was found.");
	    }
	    
	    // Build the path from end to start
	    List<Node> path = new ArrayList<>();
	    Node currentNode = endNode;
	    while (currentNode != null) {
	        path.add(0, currentNode);
	        currentNode = parents.get(currentNode);
	    }
	    
	    // Construct the result map and return it
	    LinkedHashMap<String, Object> result = new LinkedHashMap<>();
	    result.put("length", distances.get(endNode));
	    LinkedHashMap<String, Node> pathMap = new LinkedHashMap<>();
	    for (Node node : path) {
	        pathMap.put(node.getId(), node);
	    }
	    result.put("path", pathMap);
	    return result;
	}
}

