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
		Map<Node, Integer> distances = new HashMap<>();  // Distance to each node from the start node
		Map<Node, Node> parents = new HashMap<>();  // Parent node of each node in the shortest path
		PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));  // Priority queue to choose the next node to visit
		for (Node node : mapNodes.values()) {  // Initialize distances and parents for all nodes
			distances.put(node, Integer.MAX_VALUE);  // Initially set the distance to each node as "infinite"
			parents.put(node, null);  // Initially set the parent of each node as null
			queue.offer(node);  // Add each node to the priority queue
		}
		distances.put(startNode, 0);  // Distance to the start node is 0
		queue.remove(startNode);  // Remove the start node from the queue (to update its distance value)
		queue.offer(startNode);  // Add the start node back to the queue
		
		while (!queue.isEmpty()) {  // While there are still nodes to visit
			Node current = queue.poll();  // Choose the node with the smallest distance
			if (current == endNode) {  // If we've reached the end node, we're done
				break;
			}
			for (Pair<Node, Integer> neighbor : current.getNeighbours().values()) {  // For each neighbor of the current node
				Node neighborNode = neighbor.getFirst();  // Get the neighbor node
				int distance = neighbor.getSecond();  // Get the distance to the neighbor
				int newDistance = distances.get(current) + distance;  // Calculate the new distance to the neighbor
				if (newDistance < distances.get(neighborNode)) {  // If the new distance is smaller than the current distance to the neighbor
					distances.put(neighborNode, newDistance);  // Update the distance to the neighbor
					parents.put(neighborNode, current);  // Update the parent of the neighbor
					queue.remove(neighborNode);  // Remove the neighbor from the queue (to update its distance value)
					queue.offer(neighborNode);  // Add the neighbor back to the queue
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
			path.add(0, currentNode);  // Add the current node to the beginning of the path list
			currentNode = parents.get(currentNode);  // Move to the parent node
		}
		
		// Construct the result map and return it
		LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		result.put("length", distances.get(endNode));  // Add the length of the shortest path to the result map
		LinkedHashMap<String, Node> pathMap = new LinkedHashMap<>();  // Map of node IDs to

		// nodes in the shortest path
		for (Node node : path) {
			pathMap.put(node.getId(), node); // Add each node in the shortest path to the pathMap
			}
			result.put("path", pathMap); // Add the path map to the result map
			return result; // Return the result map containing the length of the shortest path and the nodes in the path
	}
}

