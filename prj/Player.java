
public class Player {
	private String username, password, fName, lName, cClass, email;
	private int totalGames, gamesAttended;
	private boolean on_a_team;

	public Player()
	{
		fName = null;
		lName = null;
		cClass = null;
		email = null;
		on_a_team = false;
	}	
	public Player(String firstName, String lastName, String currentClass, String inputEmail)
	{
		fName = firstName;
		lName = lastName;
		cClass = currentClass;
		email = inputEmail;
		on_a_team = false;
		totalGames = 0;
		gamesAttended = 0;
	}	
	public void createTeam()
	{}
}
