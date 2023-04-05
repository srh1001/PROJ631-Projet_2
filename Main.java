package projet632_projet2_eclipse;
import java.util.*;


public class Main {

	public static void main(String[] args) {
		
		Data data1 = new Data("d1", 40);
		Data data2 = new Data("d2", 6);
		Data data3 = new Data("d3", 20);

		
		NodeUser user1 = new NodeUser("u1");
		user1.addInterestData(data1);
		user1.addInterestData(data2);
		user1.addInterestData(data3);

		NodeUser user2 = new NodeUser("u2");
		//user2.addInterestData(data1);
		user2.addInterestData(data2);
		user2.addInterestData(data3);
		
		NodeSystem nsys1 = new NodeSystem("s1", 10);
		NodeSystem nsys2 = new NodeSystem("s2", 20);
		NodeSystem nsys3 = new NodeSystem("s3", 20);
		NodeSystem nsys4 = new NodeSystem("s4", 15);
			
		user1.addNeighbour(nsys1, 1);
		user2.addNeighbour(nsys4, 1);
		nsys1.addNeighbour(nsys2, 5);
		nsys1.addNeighbour(nsys3, 10);
		nsys2.addNeighbour(nsys1, 5);
		nsys2.addNeighbour(nsys4, 10);
		nsys3.addNeighbour(nsys1, 10);
		nsys3.addNeighbour(nsys4, 1);
		nsys4.addNeighbour(nsys2, 10);
		nsys4.addNeighbour(nsys3, 1);


		
		Sysapp sys = new Sysapp();
		sys.addNode(user1);
		sys.addNode(user2);
		sys.addNode(nsys1);
		sys.addNode(nsys2);
		sys.addNode(nsys3);
		sys.addNode(nsys4);
		
		System.out.println("--- Noeud du système sys :");
		for (Map.Entry<String, Node> entry : sys.getMapNodes().entrySet()) {
			System.out.println("Node id : " + entry.getKey());
		}
		
        System.out.println("\n");
        System.out.println("--- Voisins du noeud s1 :");
		nsys1.sortNeighborsByDistance();
		HashMap<String, Pair<Node, Integer>> neighbours = nsys1.getNeighbours();		
		for (Map.Entry<String, Pair<Node, Integer>> entry : neighbours.entrySet()) {
		    System.out.println("Neighbor id: " + entry.getKey() + ", Distance: " + entry.getValue().getSecond());
		}
		
		
        System.out.println("\n");
        System.out.println("--- Plus court chemin entre u1 et s4 :");
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
        System.out.println("--- Placement de d1 par rapport à u1 :");		
		user1.placeData("d1", sys);
		
        System.out.println("\n");
        System.out.println("--- Placement de d2 par rapport à u2 :");
		user2.placeData("d2", sys);

        System.out.println("\n");
        System.out.println("--- Placement de d3 par rapport à u1 puis u2 :");
		data3.placeData(sys, user1, user2);

	}
}