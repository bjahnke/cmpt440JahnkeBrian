import java.util.*;
public class ValidTeamDFA {

	private static final int q0 = 0;  //team created 
	private static final int q1 = 1;  //team is in wait list
	private static final int q2 = 2;  //team name unique
	private static final int q3 = 3;  //has required amount of players
	private static final int q4 = 4;  //players are not on two teams/accepted state

	
	private static int state = 0;
	
	private static int[][] delta =
	{
			{q0, q1, q2, q3, q4},
			{q0, q1, q2, q3, q4},
			{q0, q1, q2, q3, q4},
			{q0, q1, q2, q3, q4},
			{q0, q1, q2, q3, q4}
	};

	public int process(ImleaguesLeague league, Team t)
	{
		
		int index = 0;
		if(league.isWaitList(t))
		{
			index = 1;
			if(league.isTeamNameLegal(t))
			{	
				index = 2;
				if(league.isTeamBigEnough(t))
				{			
					index = 3;
					if(league.validRoster(t))
					{
						index = 4;
					}
				}
			}
		}
		return state = delta[state][index];	
	}
	
	public String stateReport(ImleaguesLeague league, Team t)
	{
		state = process(league, t);
		String report = "";
		if(state == q0)
			report = "This team is not currently in the league's wait list.";
		if(state == q1)
			report = "The name of this team is already taken by another team in the league./n";
		if(state == q2)
			report = "This team does not meet the required roster size of the league.";
		if(state == q3)
			report = "This team contains players that exist on another team. ";
		if(state == q4)
			report = "This team is valid!";
		return report;
	}
	
	public boolean runDFA(ImleaguesLeague league, Team t)
	{
		int num = 0;
		while(state != q4)
		{
			state = process(league, t);
			if(state == q0)
			{
				league.addTeamWaitList(t);
			}
			if(state == q1)
			{
				num++;
				t.setTeamName(t.getTeamName() + "000" + num);
			}
			if(state == q2)
			{
				int x = 0;
				while(t.getRoster().size() < league.getReqTeamSize())
				{
					Player freeAgent = league.getFreeAgentList().get(x);
					t.addPlayer(freeAgent);
					league.removeFreeAgent(freeAgent);
					x++;
				}
			}
			if(state == q3)
			{
				ArrayList<Player> badPlayers = league.invalidPlayers(t);
				for(int i = 0; i < badPlayers.size(); i++)
					t.removePlayer(badPlayers.get(i));
			}

		}
		
		if(league.isLeagueJoinable())
		{
			league.addTeamLeague(t);
			league.removeTeamWaitList(t);
			return true;
		}
		return false;
	}
}