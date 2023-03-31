package projet632_projet2_eclipse;

import java.util.*;

public class NodeSystem extends Node {
    private String id;
    private int memoryCapacity;
    private HashMap<String, Data> storedData = new HashMap<String, Data> ();

    public NodeSystem(String id, int MemoryCapacity) {
    	super(id); // call the constructor of the superclass with the id argument
        this.id = id;
    	this.memoryCapacity = MemoryCapacity;
    }

    
    public NodeSystem(String id, int MemoryCapacity, HashMap<String, Data> storedData) {
    	super(id); // call the constructor of the superclass with the id argument
        this.id = id;
    	this.memoryCapacity = MemoryCapacity;
        this.storedData = storedData;
    }

    public String getId() {
        return id;
    }

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public HashMap<String, Data> getstoredData() {
        return storedData;
    }
    
    public void addStoredData(Data data) {
    	storedData.put(data.getId(), data);
    }
}

