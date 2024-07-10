package flashtanki.server.battles;

import flashtanki.server.protocol.commands.Command;
import flashtanki.server.protocol.commands.Commands;

public class FundManager {
	
	private BattleModel battle;
    public int fund = 1337;
    
    public FundManager(BattleModel bm) {
    	this.battle = bm;
    }

	public void addFund(int fund) {
    	this.fund += fund;
    	this.battle.send2Battle(new Command(Commands.ChangeFund, this.fund));
    }
}
