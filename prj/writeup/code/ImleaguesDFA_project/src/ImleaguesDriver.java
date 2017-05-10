import java.util.*;
public class ImleaguesDriver {

	public static void main(String[] args) 
	{		
		System.out.println("Welcome To My ImLeagues Emulator!");
		System.out.println("\nType 'help' to veiw available commands.");			
	
		run();		
	}
	
	public static boolean userCommands(String input, ArrayList<Player> pList, ArrayList<Team> tList, ImleaguesLeague league)    //possible commands for a User not logged in to any account.
	{
		Scanner sc = new Scanner(System.in);
		switch(input.toLowerCase())
		{
			case "help":
				System.out.println("You are currently not signed in to any player profile.\n"
						+ "Available Commands:\n"
						+ "help\n"
						+ "login\n"
						+ "register");
				return true;
				
			case "register":
				System.out.println("Enter your first name here: ");
				String fname = sc.nextLine();
				System.out.println("Enter your last name here: ");
				String lname = sc.nextLine();
				System.out.println("Enter your class here: (Freshman, Sophomore, Junior, Senior)");
				String grade = sc.nextLine();
				Player newPlayer = new Player(fname, lname, grade);
				pList.add(newPlayer);
				newPlayer.setEmail(pList);
				return true;
			case "login":
				System.out.println("Enter your Marist email here: ");
				String login = sc.nextLine();
				if(login.equals("exit"))
				{
					System.out.println("Exiting login...");
					return true;
				}
				else if(searchPlayer(pList, login) instanceof Player)
				{
					Player user = searchPlayer(pList, login);
					System.out.println("Login Successful! Welcome " + user.getfName());
					user.playerRun(league);
					return true;
				}
				else
				{
					System.out.println("Login Failed. Email not found.");
					userCommands("login", pList, tList, league);
				}		
				return true;
			case "admin":
				System.out.println("You are now in Admin mode.");
				boolean proceed = true;
				while(proceed)
				{
					String adminInput = sc.nextLine();
					proceed = adminCommands(adminInput, pList, tList, league);
				}
				return true;
			case "exit":
				System.out.println("The program will now terminate. Thank you!");
				return false;
			default:
				System.out.println("This is not a valid command. Try again.");
				return true;
		}
	}
	
	public static boolean adminCommands(String input, ArrayList<Player> pList, ArrayList<Team> tList, ImleaguesLeague league)
	{
		Scanner sc = new Scanner(System.in);
		switch(input.toLowerCase())
		{
			case "help":
				System.out.println("Available Admin Commands: \n" +
									"free agents\n" +
									"wait list\n" + 
									"league info\n" +
									"fix league\n" +
									"view roster\n" +
									"logout");
				return true;
			case "free agents":
				league.printFreeAgents();
				return true;
				
			case "league info":
				league.printLeagueInfo();
				return true;
				
			case "wait list":
				league.printWaitList();
				return true;
				
			case "view roster":
				league.printWaitList();
				System.out.print("Enter a team name to view roster: ");
				String teamName = sc.nextLine();
				int x = 0;
				String output = "Could not find a team with that name.";
				while(x < tList.size())
				{
					if(tList.get(x).getTeamName().equals(teamName))
					{
						Team t = tList.get(x);
						output = t.rosterString();
						break;
					}
					x++;
				}
				System.out.println(output);
				return true;
				
			case "fix league":
				ValidTeamDFA dfa = new ValidTeamDFA();
				int i = 0;
				while(i < league.getWaitList().size())
				{
					Team waitTeam = league.getWaitList().get(i);
					boolean didMoveTeam = dfa.runDFA(league, waitTeam);
					if(!didMoveTeam)
						i++;
				}
				System.out.println("Operation Successful");
				return true;
			case "logout":
				System.out.println("Logging out of admin mode...");
				return false;
			default:
				System.out.println("This is not a valid command. Try again.");
				return true;
		}
	}
	
	public static void run()       //the REPL. Will continue to loop until the use types 'exit'.
	{
		ImleaguesLeague L1 = new ImleaguesLeague("Badminton", 5, 2, "06/30/2017");
		ArrayList<Player> allPlayers = createPlayers(25);
		ArrayList<Team> allTeams = populateTeams(L1, allPlayers);
		populateLeague(L1, allTeams);
		
		Player me = new Player("Brian", "Jahnke", "Junior");
		allPlayers.add(me);
		me.setEmail(allPlayers);
		
		Scanner sc = new Scanner(System.in);
		boolean proceed = true;
		while(proceed)
		{
			String input = sc.nextLine();
			proceed = userCommands(input, allPlayers, allTeams, L1);
		}
	}

	public static ArrayList<Player> createPlayers(int amt)    //Generates and returns a list of Player class' who's size is defined by amt
	{
		List<String> fNames = Arrays.asList("Chris", "Tom", "Ryan", "Mike", "Andrew", "Nina", "Kim", "Taylor", "Jenna", "Kayla", "Jake");
		List<String> lNames = Arrays.asList("Abruzzo", "Quin", "Bern", "Moawad", "Kumpfbeck", "Peppel", "Schwartz", "Mueller", "Trockman", "Cole", "Yorke");
		List<String> cClass = Arrays.asList("Freshman", "Sophomore", "Junior", "Senior");
		
		ArrayList<Player> playerList = new ArrayList<Player>();
		for(int i = 1; i <= amt; i++)
		{
			int rand1 = (int)(Math.random() * (fNames.size()-0));
			int rand2 = (int)(Math.random() * (lNames.size()-0));
			int rand3 = (int)(Math.random() * (cClass.size()-0));
			String f = fNames.get(rand1);
			String l = lNames.get(rand2);
			String c = cClass.get(rand3);
			Player p = new Player(f, l, c);
			playerList.add(p);
			p.setEmail(playerList);
		}
		return playerList;
	}
	
	public static ArrayList<Team> populateTeams(ImleaguesLeague league, ArrayList<Player> pList)
	{
		List<String> tNames = Arrays.asList("Hitters", "Smash that Bird", "Shuttle Masters", "Allied Powers", "Lin Win", "Dynamic Duo", "Thunder Birds");
		ArrayList<Team> tList = new ArrayList<Team>();
		int x = 0;
		int y = 0;
		while(x < tNames.size() && y < pList.size())
		{
			Team t1 = new Team(tNames.get(x), pList.get(y));
			y++;
			if(y < pList.size())
			{
				t1.addPlayer(pList.get(y));
				y++;
			}			
			tList.add(t1);
			x++;
		}
		while(y < pList.size())
		{
			league.addFreeAgent(pList.get(y));
			y++;
		}
		return tList;
	}
	
	public static void populateLeague(ImleaguesLeague league, ArrayList<Team> tList)
	{
		for(int i = 0; i < tList.size(); i++)
			league.addTeamWaitList(tList.get(i));
	}
	
	public static Player searchPlayer(ArrayList<Player> pList, String email)	//Takes in a list of all players and an email.
	{																			//Returns Player object that shares the same email.
		for(int i = 0; i < pList.size(); i++)									//Otherwise returns null.
		{
			if(email.equals(pList.get(i).getEmail()))
				return pList.get(i);
		}
		return null;
	}
}
