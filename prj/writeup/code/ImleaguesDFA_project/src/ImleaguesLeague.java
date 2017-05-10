import java.util.*;
import java.text.*;
public class ImleaguesLeague {
	private ArrayList<Team> teamList;
	private ArrayList<Team> waitList;
	private ArrayList<Player> freeAgents;
	private int maxSize, reqTeamSize;
	private String leagueName;
	public ImleaguesLeague()
	{
		leagueName = "default";
		maxSize = 0;
		reqTeamSize = 0;
		teamList = new ArrayList<Team>();
		waitList = new ArrayList<Team>();
	}
	public ImleaguesLeague(String name, int max, int minTeamSize, String deadline)
	{
		leagueName = name;
		maxSize = max;
		reqTeamSize = minTeamSize;
		teamList = new ArrayList<Team>();
		waitList = new ArrayList<Team>();
		freeAgents = new ArrayList<Player>();
	}
	public ArrayList<Team> getTeamList()
	{
		return teamList;
	}
	public ArrayList<Team> getWaitList()
	{
		return waitList;
	}
	public int getMaxSize()
	{
		return this.maxSize;
	}
	public String getLeagueName()
	{
		return this.leagueName;
	}
	public int getReqTeamSize()
	{
		return this.reqTeamSize;
	}
	public ArrayList<Player> getFreeAgentList()
	{
		return this.freeAgents;
	}
	public void addTeamLeague(Team x)
	{
		this.teamList.add(x);	
	}
	public void removeTeamLeague(Team x)
	{
		this.teamList.remove(x);
	}
	public void addTeamWaitList(Team x)   
	{
		this.waitList.add(x);
	}
	public void removeTeamWaitList(Team x)
	{
		this.waitList.remove(x);
	}
	public void addFreeAgent(Player p)
	{
		this.freeAgents.add(p);
	}
	public void removeFreeAgent(Player p)
	{
		this.freeAgents.remove(p);
	}
	public boolean isLeagueJoinable()    //Checks if there is available space for Teams to join.
	{
		if(this.teamList.size() < this.maxSize)
			return true;
		else 
			return false;
	}
	public boolean isTeamBigEnough(Team x)     //Checks if Team has a large enough roster for the league.
	{
		if(x.getRoster().size() >= this.reqTeamSize)
			return true;
		else 
			return false;
	}
	public boolean isTeamNameLegal(Team x)     //Checks if the team 'x' shares the same name as a team already in the league
	{
		if(this.teamList.size() == 0)
			return true;
		
		
		boolean nameTaken = false;
		int i = 0;
		while(nameTaken == false && i < this.teamList.size())
		{
			String teamInLeague = this.teamList.get(i).getTeamName();
			String inputTeamName = x.getTeamName();
			if(inputTeamName.equals(teamInLeague))
				nameTaken = true;
		}
		return nameTaken;
	}
	public String searchPlayer(Player p)       //Checks if Player 'p' is present on some team within the league
	{
		
		ArrayList<Team> leagueTeams = this.teamList;
		for(int i = 0; i < leagueTeams.size(); i++)
		{
			ArrayList<Player> leagueTeamRoster = leagueTeams.get(i).getRoster();
			for(int x = 0; x < leagueTeamRoster.size(); x++)
			{
				Player leaguePlayer = leagueTeamRoster.get(x);
				if(p.getEmail().equals(leaguePlayer.getEmail()))
					return leagueTeams.get(i).getTeamName();
			}
		}
		return null;
	}
	public ArrayList<Player> invalidPlayers(Team x)
	{
		ArrayList<Player> badPlayers = new ArrayList<Player>();
		for(int i = 0; i < x.getRoster().size(); i++)
		{
			if(this.searchPlayer(x.getRoster().get(i)) != null)
				badPlayers.add(x.getRoster().get(i));
		}
		return badPlayers;
	}
	public boolean validRoster(Team x)
	{
		if(invalidPlayers(x).size() == 0)
			return true;
		else
			return false;
	}
	public boolean isWaitList(Team x)
	{
		for(int i = 0; i < this.waitList.size(); i++)
		{
			if(this.waitList.get(i) == x)
				return true;
		}
		return false;
	}
	public void printFreeAgents()
	{
		System.out.println("\nFree Agents: " );
		for(int i = 0; i < this.getFreeAgentList().size(); i++)
		{
			Player freeAgent = this.getFreeAgentList().get(i);
			System.out.println(i + ".) " +freeAgent.getfName() + " " + freeAgent.getlName());
		}
	}
	public void printLeagueInfo()
	{
		System.out.println("\nLeague Info: ");
		System.out.println("League Name : " + this.getLeagueName() + "\n" +
							"Current Size : " + this.getTeamList().size() + "\n" +
							"Maximum Size : " + this.getMaxSize() + "\n" +
							"Teams : ");
		for(int i = 0; i < this.getTeamList().size(); i++)
			System.out.println(this.getTeamList().get(i).getTeamName());
	}
	public void printWaitList()
	{
		System.out.println("\nWait Listed Teams: ");
		for(int i = 0; i < this.getWaitList().size(); i++)
		{
			Team waitTeam = this.getWaitList().get(i);
			System.out.println(waitTeam.getTeamName());
		}
	}
}
