package flashtanki.server.battles;

import java.util.Arrays;

import org.json.simple.JSONObject;

public class Map extends JSONObject {
    public Map() {
    	this.put("enabled", true);
    	this.put("mapId", "map_sandbox");
    	this.put("mapName", "Песочница");
    	this.put("maxPeople", 8);
    	this.put("preview", 618467);
    	this.put("maxRank", 30);
    	this.put("minRank", 1);
    	this.put("supportedModes", Arrays.asList("DM","TDM","CTF","CP"));
    	this.put("theme", "SUMMER");
    }
}
