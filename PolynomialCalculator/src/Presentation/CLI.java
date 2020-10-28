/**
 * @author Itay Bouganim, 305278384
 * @author Sahar Vaya , 205583453
 */
package Presentation;
import java.io.IOException;
import java.util.Scanner;
import Logic.Calculator;

/*
 * A Command line interface class that is responsible of all user I/O interaction.
 */
public class CLI 
{
	//Fields
	private static Scanner scan; // A scanner to receive user input through command line interface. 
	
	//Methods
	/*
	 * Initial load of the menu and initialization of the user input scanner.
	 */
	public static void Initiate()
	{
		NewScreen();
		scan = new Scanner(System.in);
		menu();
	}
	
	/*
	 * Prints menu and asks for user choice of operation.
	 */
	public static void menu()
    {
        int menuChoice = -1;
        boolean exit = false;
        while (!exit)
        {
        	chooseField();
            String menu = "Please select an operation:\n"+
            				"1.Addition\n"+
            				"2.Multiplication\n"+
            				"3.Evaluation\n"+
            				"4.Derivate\n"+
            				"5.Exit";
            System.out.println(menu);
            String choice = scan.next();
            try 
            {
            	menuChoice = Integer.parseInt(choice);
            }
            catch(NumberFormatException e) { };
            switch (menuChoice)
            {
                case 1: addition();
            		break;	
                case 2: multiplication();
                    break;
                case 3: evaluation();
                    break;
                case 4: derivate();
                    break;
                case 5: exit();
                default:
                    System.out.println("\nNot a valid menu choice.\nPlease try again.\n");
                    NewScreen();
                    menu();
                    break;
            }
        }
    }
	
	/*
	 * Asks the user to choose over what field will he wish to perform all the calculations.
	 * Set up the chosen field in the calculator.
	 */
	private static void chooseField()
	{
		char fieldChoice = '0';
		System.out.println("Please select the scalar field\nRational (Q) or Real (R)");
    	fieldChoice = scan.next().trim().charAt(0);
    	switch (fieldChoice)
        {
            case ('Q'):
            case ('q'):
        			Calculator.setFieldRepresentation('Q');
                 break;
            case ('R'):
            case 'r':
            		Calculator.setFieldRepresentation('R');
                break;
                default:
                    System.out.println("\nNot a valid field choice.\nPlease try again.\n");
                    NewScreen();
                    menu();
                    break;
        }
	}
	
	/*
	 * Prints instructions for the user about the addition process.
	 * Calls the calculator addition method to perform the calculations.
	 * Prints result or error if input is invalid.
	 */
	private static void addition()
	{
		System.out.println("You have selected: Addition");
		System.out.println("Please insert the first polynomial");
		String poly1 = scan.next();
		System.out.println("Please insert the second polynomial");
		String poly2 = scan.next();
		String solution = Calculator.addition(poly1, poly2);
		if (!(solution==null))
		{
			System.out.println("The solution is:");
			System.out.println(solution+"\n");
		}
		else System.out.println(error());
	}
	
	/*
	 * Prints instructions for the user about the multiplication process.
	 * Calls the calculator multiplication method to perform the calculations.
	 * Prints result or error if input is invalid.
	 */
	private static void multiplication()
	{
		System.out.println("You have selected: Multiplication");
		System.out.println("Please insert the first polynomial");
		String poly1 = scan.next();
		System.out.println("Please insert the second polynomial");
		String poly2 = scan.next();
		String solution = Calculator.multiplication(poly1, poly2);
		if (!(solution==null))
		{
			System.out.println("The solution is:");
			System.out.println(solution+"\n");
		}
		else System.out.println(error());
	}
	
	/*
	 * Prints instructions for the user about the evaluation process.
	 * Calls the calculator evaluation method to perform the calculations.
	 * Prints result or error if input is invalid.
	 */
	private static void evaluation()
	{
		System.out.println("You have selected: Evaluation");
		System.out.println("Please insert the polynomial");
		String poly = scan.next();
		System.out.println("Please insert the scalar");
		String scalar = scan.next();
		String solution = Calculator.evaluation(poly, scalar);
		if (!(solution==null))
		{
			System.out.println("The solution is:");
			System.out.println(solution+"\n");
		}
		else System.out.println(error());
	}
	
	/*
	 * Prints instructions for the user about the derivation process.
	 * Calls the calculator derivate method to perform the calculations.
	 * Prints result or error if input is invalid.
	 */
	private static void derivate()
	{
		System.out.println("You have selected: Derivate");
		System.out.println("Please insert the polynomial");
		String poly = scan.next();
		String solution = Calculator.derivate(poly);
		if (!(solution==null))
		{
			System.out.print("The derivative polynomial is: ");
			System.out.print(solution+"\n\n");
		}
		else System.out.println(error());
	}
	
	/*
	 * A function that is called if user input was incorrect or not valid.
	 * @return A string representing an error with instructions and input expectations. 
	 */
	private static String error() 
	{
		return "\nError occured! please check that you entered the polynomials correctly:\n"
				+ "1)All math expressions contain only variable X/x.\n"
				+ "2)All math expressions are mathematically legal.\n"
				+ "3)The polynomial you entered is over the correct field.\n"
				+ "INSTRUCTIONS\n"
				+ "************\n"
				+ " -All polynomial coefficients must be from the selected field:\n"
				+ "  *Rational: in the form of quotient (A/B), *Real: Decimal form (A.B) or *Natural: for all fields.\n"
				+ " -Exponents must be non-negative and Natural.\n"
				+ " NOTE: in evaluation, scalars to evaluate with can be from all fields.\n\n"
				+ "TRY AGAIN.\n";
	}
	
	/*
	 * Exits from the application.
	 */
	private static void exit()
	{
		System.out.println("Press any key to exit...");
		int key = scan.nextInt();
		System.exit(0);
	}
	
	/*
	 * Clears the screen and prints welcome message to the user.
	 */
    private static void NewScreen()
    {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
          } 
        catch (final Exception e) {
          }    
        System.out.println("Welcome to the polynomial calculator.");
    }
}
