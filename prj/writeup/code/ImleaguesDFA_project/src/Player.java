import java.util.*;
public class Player {
	private String username, password, fName, lName, cClass, email;
	private Team currentTeam;
	private int totalGames, gamesAttended;
	private boolean on_a_team;

	public Player()
	{
		fName = null;
		lName = null;
		cClass = null;
		email = null;
		currentTeam = null;
		on_a_team = false;
	}	
	public Player(String firstName, String lastName, String currentClass)
	{
		fName = firstName;
		lName = lastName;
		cClass = currentClass;
		email = null;
		currentTeam = null;
		on_a_team = false;
		totalGames = 0;
		gamesAttended = 0;
	}	
	public boolean playerCommands(String cmd, ImleaguesLeague league)
	{
		Scanner sc = new Scanner(System.in);
		switch (cmd.toLowerCase())
		{
			case "help":
				System.out.println("Your are currently signed in as " + this.email + "\n" +
									"Available Commands:" + "\n" +
									"my info" + "\n" +
									"create team" + "\n" +
									"create legal team" + "\n" +
									"add free agent" + "\n" +
									"enter wait list" + "\n" +
									"become free agent" + "\n" +
									"leave team" + "\n" +
									"check team" + "\n" +
									"check free agents" + "\n" +
									"logout");
				return true;
				
			case "my info":
				System.out.println("Your Player Card: \n" + this.playerCard());
				return true;
				
			case "create team":
				if(currentTeam == null)
				{
					System.out.println("Enter a team name: ");
					String teamName = sc.nextLine();
					Team userTeam = new Team(teamName, this);
					this.currentTeam = userTeam;
					System.out.println("Team Created!");
				}
				else 
					System.out.println("You may not create a team while currently on another.");
				return true;
				
			case "create legal team":
				ValidTeamDFA dfa = new ValidTeamDFA();
				dfa.runDFA(league, this.currentTeam);
				return true;
				
			case "check free agents":
				league.printFreeAgents();
				return true;
				
			case "add free agent": 
				if(this.currentTeam != null)
				{
					if(this.currentTeam.getCaptain() == this)
					{
						league.printFreeAgents();
						System.out.println("\nWho would you like to add to your roster?" + " (Choose player by entering adjacent number");
						String input = sc.nextLine();
						int iFreeAgent = Integer.parseInt(input);
						Player freeAgent = league.getFreeAgentList().get(iFreeAgent);
						this.currentTeam.addPlayer(freeAgent);
						league.removeFreeAgent(freeAgent);
					}
					else 
						System.out.println("Only the captain of the team " + this.currentTeam.getCaptain().getfName() + " may add players to the team.");		
				}
				else
					System.out.println("You are not on a team. You can not add players.");
				return true;
			case "remove player":
				if(this.currentTeam != null)
				{
					System.out.println(this.currentTeam.rosterString());
					System.out.println("Enter the index of the player you with to remove.");
					String input = sc.nextLine();
					int iPlayer = Integer.parseInt(input);
					Player player = this.currentTeam.getRoster().get(iPlayer);
					this.currentTeam.removePlayer(player);
				}
				return true;
			case "enter wait list":
				if(this.currentTeam != null)
				{
					league.addTeamWaitList(this.currentTeam);
					System.out.print("You're team has been added to the wait list.");
				}
				else
					System.out.println("You currently have no team to enter the wait list with.");
				return true;
				
			case "leave team":
				if(this.currentTeam != null)
				{
					System.out.println("Are you sure you would like to leave " 
										+ this.currentTeam.getTeamName() + "? " + "(Y/N)");
					String input = sc.nextLine();
					if(input.toLowerCase().equals("y"))
					{
						boolean remove = this.leaveTeam();
						if(remove)
							System.out.println("You were successfully removed.");
						else 
							System.out.println("Operation failed.");
					}
					else if(input.toLowerCase().equals("n"))
						System.out.println("Operation Aborted.");
					else 
					{
						System.out.println("Invalid Input. Please type 'Y' or 'N'.\n");
						this.playerCommands("leave team", league);
					}
				}
				else
					System.out.println("You are currently not on a team.");
				return true;
				
			case "become free agent":
				if(this.currentTeam == null)
				{
					league.addFreeAgent(this);
					System.out.println("You have been added to the free agent list.");
				}
				else
					System.out.println("You are currently on a team. You can not become a free agent.");
				
			case "check team":
				if(this.currentTeam != null)
					System.out.println("Roster: "+"\n" + this.currentTeam.rosterString());
				else
					System.out.println("You are currently not on a team.");
				return true;
				
			case "logout":			
				System.out.println("Logging out of user: " + this.email);
				return false;
				
			default: 
				System.out.println("Not a valid Player command. Try again.");
				return true;
		}
	}
	public String playerCard()
	{
		String team = "none";
		if(this.currentTeam != null)
			team = this.currentTeam.getTeamName();
		return "Name: " + this.fName + " " + this.lName + "\n" +
				"Class: " + this.cClass + "\n" +
				"Current Team: " + team + "\n" +
				"Contact: " + this.email + "\n";						
	}
	public String getEmail()
	{
		return this.email;
	}
	public String getfName()
	{
		return this.fName;
	}
	public String getlName()
	{
		return this.lName;
	}
	public void setTeam(Team newTeam)
	{
		this.currentTeam = newTeam;
	}
	public void setEmail(ArrayList<Player> p)
	{
		int count = 0;
		for(int i = 0; i < p.size(); i++)
		{
			if(p.get(i).getfName().equals(this.fName) && p.get(i).getlName().equals(this.lName))
				count++;				
		}
		this.email = this.fName.toLowerCase() + "." + this.lName.toLowerCase() + count +"@marist.edu"; 
	}
	public boolean leaveTeam()
	{
		return this.currentTeam.removePlayer(this);
	}
	public void playerRun(ImleaguesLeague league)
	{
		System.out.println("\nType 'help' to veiw available commands.");
		Scanner sc = new Scanner(System.in);
		boolean proceed = true;
		while(proceed)
		{
			String input = sc.nextLine();
			proceed = playerCommands(input, league);
			
		}
	}
}
