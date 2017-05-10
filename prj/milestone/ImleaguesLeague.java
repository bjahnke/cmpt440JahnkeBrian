import java.util.ArrayList;
public class ImleaguesLeague {
	private ArrayList<Team> teamList;
	private final int MAX_leagueSize = 8;
	public ImleaguesLeague()
	{
		teamList = new ArrayList<Team>();
	}
	
	public ArrayList<Team> getTeamList()
	{
		return teamList;
	}
	
	public int getMaxSize()
	{
		return MAX_leagueSize;
	}
	
}
