import java.util.Scanner;
public class driverDFA
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter a possible solution here: ");
		
		String input = sc.nextLine();
		ManWolf solution = new ManWolf();
		solution.process(input);
		
		if (solution.accepted()) 
			System.out.println("This solution is correct.");
		else
			System.out.println("This solution is incorrect.");
	}
}