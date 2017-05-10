import java.util.ArrayList;
public class Team {
	private String tName, captain;
	private int wins, losses, ties, totalGames, gamesAttended;
	private final int requiredRosterNum = 5;
	private ArrayList<Player> roster;
	
	public Team()
	{
		tName = null;
		wins = 0;
		losses = 0;
		ties = 0;
	}
	
	public Team(String teamName)
	{
		tName = teamName;
		wins = 0;
		losses = 0;
		ties = 0;
		roster = new ArrayList<Player>();
		totalGames = 0;
		gamesAttended = 0;
	}
	
	public void addPlayer(Player player)
	{
		roster.add(player);
	}
	
	public void incWin()
	{
		wins++;
	}
	
	public void incLoss()
	{
		losses++;
	}
	
	public void incTie()
	{
		ties++;
	}
	
	public String getName()
	{
		return tName;
	}
	public boolean teamLegal_game()
	{
		if(this.totalGames - this.gamesAttended >= 2)
			return false;
		else 
			return true;
	}
	
	public boolean teamLegal_players()
	{
		if(roster.size() >= requiredRosterNum)
			return true;
		else 
			return false;
	}
	
	public boolean teamLegal_name(ArrayList<Team> league)
	{
		boolean nameTaken = false;
		int i = 0;
		while(nameTaken == false && i < league.size())
		{
			if(league.get(i).getName().equals(this.tName))
				nameTaken = true;
		}
		return nameTaken;
	}
	
	public boolean teamLegal_league(ImleaguesLeague league)
	{
		ArrayList<Team> teamList = league.getTeamList();
		if(teamList.size() == league.getMaxSize())
			return false;
		else 
			return true;
	}
}
