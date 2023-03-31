package projet632_projet2_eclipse;

import java.util.*;


public class Node {
	
	private String id;
	private HashMap<String, Pair<Node, Integer>> neighbours = new HashMap<>(); // The key is the ID of the neighbor and the pair<node, integer> contains the neighbour node and the distance between the current node and this neighbor.
	
	public Node(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public HashMap<String, Pair<Node, Integer>> getNeighbours() {
		return neighbours;
	}
	
	public void setNeighbours(HashMap<String, Pair<Node, Integer>> neighbours) {
		this.neighbours = neighbours;
	}

	public void addNeighbour(Node node, Integer distance) {
		Pair<Node, Integer> p = new Pair<Node, Integer> (node, distance);
		neighbours.put(node.getId(), p);
	}
	
	@Override
	public String toString() {
	    return "Node " + id;
	}
	
	public void sortNeighborsByDistance() {
	    List<Map.Entry<String, Pair<Node, Integer>>> sortedList = new ArrayList<>(neighbours.entrySet());
	    Collections.sort(sortedList, new Comparator<Map.Entry<String, Pair<Node, Integer>>>() {
	        @Override
	        public int compare(Map.Entry<String, Pair<Node, Integer>> o1, Map.Entry<String, Pair<Node, Integer>> o2) {
	            return o1.getValue().getSecond().compareTo(o2.getValue().getSecond());
	        }
	    });
	    neighbours = new LinkedHashMap<>();
	    for (Map.Entry<String, Pair<Node, Integer>> entry : sortedList) {
	        neighbours.put(entry.getKey(), entry.getValue());
	    }
	}

}