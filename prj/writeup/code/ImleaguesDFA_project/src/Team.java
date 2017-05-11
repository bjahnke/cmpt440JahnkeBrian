import java.util.ArrayList;
public class Team {
	private String tName;
	private Player captain;
	private int wins, losses, ties, totalGames, gamesAttended;
	private ArrayList<Player> roster;
	
	public Team()
	{
		tName = null;
		wins = 0;
		losses = 0;
		ties = 0;
		roster = new ArrayList<Player>();
	}
	
	public Team(String teamName, Player capt)
	{
		tName = teamName;
		wins = 0;
		losses = 0;
		ties = 0;
		roster = new ArrayList<Player>();
		roster.add(capt);
		capt.setTeam(this);
		captain = capt;
		totalGames = 0;
		gamesAttended = 0;
	}
	
	public String getTeamName()
	{
		return this.tName;
	}
	public void setTeamName(String newName)
	{
		this.tName = newName;
	}
	public ArrayList<Player> getRoster()
	{
		return this.roster;
	}
	public boolean teamLegal_game()
	{
		if(this.totalGames - this.gamesAttended >= 2)
			return false;
		else 
			return true;
	}
	public void addPlayer(Player p)
	{
		this.roster.add(p);
		p.setTeam(this);
	}
	public boolean removePlayer(Player p)   //Removes player from roster
	{
		boolean removed = this.roster.remove(p);
		p.setTeam(null);
		this.refreshCaptain();
		return removed;
	}
	private void refreshCaptain()    //Makes sure the captain is updated if roster changes
	{
		if(this.roster.size() > 0)
			this.captain = this.roster.get(0);
		else 
			this.captain = null;
	}
	public Player getCaptain()
	{
		return this.captain;
	}
	public String rosterString()
	{
		String roster = "";
		for(int i = 0; i < this.roster.size(); i++)
		{
			roster = roster + "\n" + i + ".)" + this.roster.get(i).getfName() + " " + this.roster.get(i).getlName(); 
		}
		return roster;
	}
}
