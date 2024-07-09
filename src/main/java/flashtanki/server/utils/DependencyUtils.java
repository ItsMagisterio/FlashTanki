package flashtanki.server.utils;

import java.util.HashMap;
import flashtanki.server.client.ClientEntity;
import flashtanki.server.client.Dependency;

public class DependencyUtils {
	
	private static int id;
	private static HashMap<Integer, Dependency> dependencies;
	
	public DependencyUtils() {
	     id = 0;
		 dependencies = new HashMap<Integer, Dependency>();
	}
	
    public static DependencyUtils getInstance() {
    	return (new DependencyUtils());
    }
    
    public void loadDependency(ClientEntity client, String file, Runnable afterLoad) {
    	Dependency dependency = Dependency.create();
    	id++;
    	dependency.loadDependency(client, id, file, afterLoad);
    	dependencies.put(id, dependency);
    }
    
    public void mark(int id) {
    	dependencies.get(id).markDependency();
    }
}
