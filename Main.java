package projet632_projet2_eclipse;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		Data data1 = new Data("d1", 40);
		Data data2 = new Data("d2", 40);

		NodeUser user1 = new NodeUser("u1");
		user1.addInterestData(data1);
		user1.addInterestData(data2);
		
		NodeSystem nsys1 = new NodeSystem("s1", 10);
		NodeSystem nsys2 = new NodeSystem("s2", 20);
		NodeSystem nsys3 = new NodeSystem("s3", 5);
		NodeSystem nsys4 = new NodeSystem("s4", 25);
		
		
		user1.addNeighbour(nsys1, 1);
		nsys1.addNeighbour(nsys2, 5);
		nsys1.addNeighbour(nsys3, 10);
		nsys2.addNeighbour(nsys4, 1);
		nsys3.addNeighbour(nsys4, 1);
				
		Sysapp sys = new Sysapp();
		sys.addNode(user1);
		sys.addNode(nsys1);
		sys.addNode(nsys2);
		sys.addNode(nsys3);
		sys.addNode(nsys4);
		
		for (Map.Entry<String, Node> entry : sys.getMapNodes().entrySet()) {
			System.out.println("Node id : " + entry.getKey());
		}
		
		nsys1.sortNeighborsByDistance();
		HashMap<String, Pair<Node, Integer>> neighbours = nsys1.getNeighbours();		
		for (Map.Entry<String, Pair<Node, Integer>> entry : neighbours.entrySet()) {
		    System.out.println("Neighbor id: " + entry.getKey() + ", Distance: " + entry.getValue().getSecond());
		}
		
		
		
		Map<String, Object> result = sys.getShortestPath("u1", "s4");
		if (result == null) {
		    System.out.println("No path was found.");
		} else {
			System.out.println("Shortest path length: " + result.get("length"));
			System.out.print("Shortest path nodes: ");
			Map<String, Node> path = (LinkedHashMap<String, Node>) result.get("path");
			for (Map.Entry<String, Node> entry : path.entrySet()) {
			    System.out.print(entry.getValue().getId() + " ");
			}		
		}

		
		System.out.println("\n");
		user1.placeData("d1", sys);
		


	}
}