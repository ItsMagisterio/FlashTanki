package flashtanki.server.battles;

public enum BattleMode {
	
	Deathmatch("DM"),
	TeamDeathmatch("TDM"),
	CaptureTheFlag("CTF"),
	ControlPoints("CP"),
	Juggernaut("JGR");
	
	public final String name;
	
    BattleMode(String name) {
    	this.name = name;
    }
    
    public static BattleMode getByName(String name) {
    	for (BattleMode mode : BattleMode.values()) {
    		if (mode.name.equals(name)) {
    			return mode;
    		}
    	}
    	return null;
    }
}
