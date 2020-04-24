// Class Roulette for CSCI 145 Project 2 Fall 19
// Modified by:
	
import java.util.*;
import java.util.Random;
import java.io.*;
import java.util.Queue;
import java.util.Scanner;

//  Class Roulette represents a roulette betting game.
class Roulette
{
    // public name constants -- accessible to others
    public final static int BLACK     =  0;			// even numbers
    public final static int RED       =  1;			// odd numbers
    public final static int GREEN     =  2;			// 00 OR 0
    public final static int NUMBER    =  3;			// number bet
    public final static int MIN_NUM   =  1;			// smallest number to bet
    public final static int MAX_NUM   = 36;			// largest number to bet
    public final static int MIN_BET   =  1;			// minimum amount to bet
    // private ArrayList
    // private name constants -- internal use only
    private final static int MAX_POSITIONS = MAX_NUM + 2; // number of positions on wheel
    private final static int NUMBER_PAYOFF = MAX_NUM - 1; // payoff for number bet
    private final static int COLOR_PAYOFF  = 2;		// payoff for color bet

    // private variables -- internal use only
    private static int ballPosition = 0;			// 00, 0, 1 .. MAX_NUM
    private static int color = GREEN;				// GREEN, RED, OR BLACK
    private static Queue<Player> player;
    private static Queue<Player> playerInGame;
    private static int n; // is this the number of player?
    // private variables -- testing only
    private static int next = 0;					// next value in the list
    private static int []randValues = {36, 5, 0, 1, 20}; // 5 values

    // variables for readGames
    private static ArrayList<String> gameName;
	private static int numOfGames = 0;
	private static int numbets = 0;
	private static ArrayList<Integer> maxBet;
	private static ArrayList<Integer> minBet;
	private static ArrayList<Integer> maxPlayer;
	private static ArrayList<String> ballpositions;
	private static int [] roundsEachGame;
	private static Queue<Integer> betsEachGame;
	public static Queue<String[]> gameSummary = new LinkedList<String[]>();
	
	 public static int casino = 0;
	 protected static int totalCashB = 0;
	static String dsktp = System.getProperty("user.home") + "\\"+"Desktop";
	
	
    public Roulette()
    {
    	player = new LinkedList<Player> ();
    	playerInGame = new LinkedList<Player> ();
    	maxBet = new ArrayList<Integer>();
    	minBet = new ArrayList<Integer>();
    	maxPlayer = new ArrayList<Integer>();
    	gameName = new ArrayList<String> ();
    	ballpositions = new ArrayList<String> ();
    	betsEachGame = new LinkedList<Integer> ();
    	n = 0;
    }
    public static void setCasino(int bet)
    {
    	casino += bet;
    }
    public static void setCashB(int cash)
    {
    	totalCashB += cash;
    }
    //  Contains the main processing loop for the roulette game.
    public void readPlayer()throws IOException
    {
    	ArrayList<Integer> id = new ArrayList<Integer> ();
      	//Scanner filePlayers = new Scanner(new File (dsktp + "\\players.txt"));
    	Scanner filePlayers = new Scanner(new File (dsktp + "\\players.txt"));
      	int money;
      	int dits;
      	double bal;
      	String name;
      	int j = 0;
      	
      	
      	while(filePlayers.hasNext())
      	{
      		id.add(filePlayers.nextInt());
      		if(id.get(j) == 0)
      		{
      			money = (int)filePlayers.nextInt();
      			name = filePlayers.next();
      			Player p = new Regular(name,money);
      			player.add(p);
      			j++;
      			n = getN() + 1;
      		}
      		else if (id.get(j) == 1)
      		{
      			money = (int)filePlayers.nextInt();
      			name = filePlayers.next();
      			dits = (int)filePlayers.nextInt();
      			Player p = new VIP(name,money,dits);
      			player.add(p);
      			j++;
      			n = getN() + 1;
      		}
      		else
      		{
      			money = (int)filePlayers.nextInt();
      			name = filePlayers.next();
      			dits = (int)filePlayers.nextInt();
      			bal = (double)filePlayers.nextDouble();
      			Player p = new superVIP(name,money,dits,bal);
      			player.add(p);
      			j++;
      			n = getN() + 1;
      		}
      	}
      	filePlayers.close();

    }
    
    public static void readGames()throws IOException
    {
    	Scanner fileGames = new Scanner(new File (dsktp + "\\games.txt"));
    	
    	gameName.add(fileGames.next());
    	numOfGames = fileGames.nextInt();
    	roundsEachGame = new int [numOfGames+1];
    	while(fileGames.hasNext())
      	{
      		minBet.add(fileGames.nextInt());
      		maxBet.add(fileGames.nextInt());
      		maxPlayer.add(fileGames.nextInt());
      	}
    	//System.out.println();
    	fileGames.close();
    }
    
    public static void writeFile() throws IOException
    {
    	//String filename = "D:\\CSCI 145\\Projects 145\\Project 4\\report.txt";
    	String filename = (dsktp + "\\report.txt");
    	FileWriter fileWriter = new FileWriter(filename);
    	PrintWriter printWriter = new PrintWriter(fileWriter);

    	for (int i = 1; i <= numOfGames; i++)
    	{
    		
    		printWriter.println("Game: " + gameName.get(0) + i + "\n");
    		if(roundsEachGame[i] > 0)
    		{
    			int r = roundsEachGame[i];
	    		for (int j = 1; j <= r; j++)
	    		{
	    			printWriter.println("Round " + j + " (" + ballpositions.get(j-1) + ") ");
	    			printWriter.println("Player\tBAmount\tBType\tPay");
	    			for(int k = 1;k <= betsEachGame.element(); k++ ) 
	    			{
	        			printWriter.println(gameSummary.element()[0] + "\t" + gameSummary.element()[1] + "\t" + gameSummary.element()[2] + "\t" + gameSummary.element()[3]);
	        			//printWriter.println();
	        			gameSummary.remove();
	    			}
	    			betsEachGame.remove();
	    			printWriter.println();
	    			
	    		}
    		}
    		else
    			printWriter.println("This game does not have any data since it has not played yet  ");
    		if(roundsEachGame[i]> 0)
    		{
    		printWriter.println("Winning/Losing amount:" + casino);
    		printWriter.println("Cash back given: $" + totalCashB);
    		}
    		printWriter.println("----------------------------------");
    		
    	}
    	fileWriter.close();
    	
    }
    
    public void games() throws IOException 
    {
    	readPlayer();
    	readGames();
    	welcomeMessage();
    	mainMenu();
    	mainMenuOption();
    	return;
    	
   }

    
        
    //=====================================================================
    //  Presents welcome message
    //=====================================================================
    public static void welcomeMessage()
    {
    	System.out.println("Author: JJ Choon, Phuong Nguyen, Victor Mendoza \n");
      	System.out.println("Welcome to an advanced version of roulette game.");
      	System.out.println("You can place a bet on black, red, or a number.");
      	System.out.println("A color bet is paid " + COLOR_PAYOFF + " the bet amount.");
      	System.out.println("A number bet is paid " + NUMBER_PAYOFF + " the bet amount.");
      	System.out.println("You can bet on a number from " + MIN_NUM + " to " + MAX_NUM + ".");
      	System.out.println("Gamble responsibly.  Have fun and good luck!\n\n");
      	System.out.println("Initialize games. Please wait ...");
      	System.out.println("All games are ready.");
      	System.out.print("Available games: ");
      	for (int i = 1; i <= numOfGames; i++)
      	{
      		gameName.add("" + gameName.get(0) + i); // now the ArrayList of gameName has 100A at index 0, 100A1 at index 1 and so on
      		System.out.print(gameName.get(i));
      		if(i < numOfGames)
      		{
      			System.out.print(", ");
      		}
      	}
      	System.out.println();
    }

    public static void mainMenu()
    {
    	System.out.println("\n\nMain Menu \n"
    			+"1. Select an available game \n"
    			+"2. Add a new player to the waitlist\n"
    			+"3. Quit\n");
    }
    
    public static void mainMenuOption() // not completed
    {
    	try {
    		int option;
    		System.out.print("Option --> ");
			Scanner scan = new Scanner(System.in);
			option = scan.nextInt();
			while(option > 4 || option < 1) // option = 5 or option = 0
			{
				System.out.println("Invalid option. Please choose one of above");
				System.out.print("Option --> ");
				option = scan.nextInt();
			}
    		while(option < 5 && option > 0)// 1 -> 4
    		{
    			

    			switch(option)
    			{
    			case 1: // Select an available game (COMPLETED)
    				int index = 0;
    				String gamename = "";
    				while(index == 0)
    				{
    					System.out.print("Select a game --> ");
    					gamename = scan.next();
    					for(int i = 1; i <= numOfGames; i++) // check for available game
    					{
    						if(gamename.equals(gameName.get(i)))
    						{
    							index = i;
    						}
    						else if(i == numOfGames && index == 0)
    						{
    							System.out.println("Please select an available game!");

    						}
    					}
    				}
    				gameMenu();
    				gameMenuOption(gamename, index);
    				break;
    			case 2:
    				// Add a new player to the wait list
    				int moneyW;
    				String nameW;
    				int ditW;
    				double balW;

    				System.out.print("Insert a type of a new player? (0: Regular, 1: VIP , 2: Super VIP): ");
    				int types = scan.nextInt();
    				while (types < 0 || types > 2)
    				{
    					System.out.println("Invalid input. Please insert again !!!");
    					System.out.print("Insert a type of a new player? (0: Regular, 1: VIP , 2: Super VIP): ");
    					types = scan.nextInt();

    				}
    				System.out.print("Insert a new player's name: ");
    				nameW = scan.next();
    				System.out.print("Insert the amount of money a new player want to add: ");
    				moneyW = scan.nextInt();
    				if(types == 0)
    				{
    					Player p = new Regular(nameW,moneyW);
    					player.add(p);
    				}
    				else if (types == 1)
    				{
    					System.out.print("Enter the last 4-numbers in your account: ");
    					ditW = scan.nextInt();
    					Player p = new VIP(nameW,moneyW,ditW);
    					player.add(p);

    				}
    				else
    				{

    					System.out.print("Enter the last 4-numbers in your account: ");
    					ditW = scan.nextInt();
    					System.out.print("Please insert how much money is available in your account balance: ");
    					balW = scan.nextDouble();
    					Player p = new superVIP(nameW,moneyW,ditW,balW);
    					player.add(p);
    				}
    				System.out.println("A new player already added");
    				mainMenu();
    				System.out.print("Option --> ");
    				option = scan.nextInt();
    				break;
    	
    			case 3:// print output
    				Queue<Player> temp = new LinkedList<Player>();
    				System.out.println("Thanks for playing our game");
    				System.out.print("Remaining players on waitlist: ");
    				while(player.peek() != null)
		    		{
		    			System.out.print(player.element().getName() + " ");
		    			temp.add(player.element());
		    			player.remove();
		    		}
    				System.out.println();
    				System.out.println("Removing players from existing games ..."); 
    				System.out.println("Generating report ... \nClosing all games.");
    				try 
    				{
    					writeFile();
    				}
    				catch(IOException e)
    				{
    					System.out.println("Cannot write file!");
    				}
    				System.exit(0);;
    				return;
    			default:
    				mainMenu();
    				System.out.println("Invalid option. Please choose one of above");
    				System.out.print("Option --> ");
    				option = scan.nextInt();
    				break;
    			}
    		}
    	}
    	catch(InputMismatchException e)
    	{
    		System.out.println("Please enter a valid option. (1, 2, 3)");
    		mainMenuOption();
    	}
    }
    
    public static void gameMenu()
    {
    	System.out.println("\nGame Menu");
    	System.out.println("1. Add a player to the game");
    	System.out.println("2. Remove a player from the game");
    	System.out.println("3. Play one round");
    	System.out.println("4. Return to the main menu\n");
    }
    
    @SuppressWarnings("null")
	public static void gameMenuOption(String gamename, int index)  // not completed
    {
    	try {
    		System.out.print("Option --> ");
    		Scanner scan = new Scanner(System.in);
    		int option = 0;
    			option = scan.nextInt();
    		if(option > 4 || option < 1)
    		{
    			System.out.println("Invaid option. Please choose one of the valid option !!! ");
    			System.out.print("Option --> ");
    			option = scan.nextInt();
    		}
    		while (option > 0 && option < 5)
    		{
    			switch(option)
    			{
    			case 1: 
    			{
    				int countNotPlaying = 0;
    				int size = player.size();

    				if(playerInGame.size() >= maxPlayer.get(index-1))
    				{
    					System.out.println("Game is already full. Unable to add player.\r\n");
    					break;
    				}
    				while(maxPlayer.get(index - 1) > playerInGame.size())
    				{
    					if(playerInGame.size() >= maxPlayer.get(index-1))
    					{
    						System.out.println("Game is already full. Unable to add player.\r\n");
    						break;
    					}
    					String yn = "n";
    					while(yn.equals("n") && size >= countNotPlaying)
    					{
    						try
    						{
    							System.out.print("Is " + player.element().getName() + " available to play? ");
    							yn = scan.next();
    							if(yn.equals("n"))
    							{
    								player.remove();
    							}
    							countNotPlaying++;
    						}
    						catch(NoSuchElementException e)
    						{
    							System.out.println("No more players available. Returning to the main menu.");
    							mainMenu();
    							mainMenuOption();
    						}
    					}
    					String yn2 = "n";
    					double credit = 0;
    					int creditValid = 0;
    					if (player.element().whichclass() == 2)
    					{
    						 System.out.println("You have a credit balance of $" + ((superVIP) player.element()).getBalance());
    						 System.out.print("Do you want to use credit balance? ");
    						 yn2 = scan.next();
    						 credit = ((superVIP) player.element()).getBalance();
    						 while(!yn2.equals("y") && !yn2.equals("n"))
    						 {
    							 System.out.println("Please insert \"y\" or \"n\" ");
    							 System.out.print("Do you want to use credit balance? ");
    							 yn2 = scan.next();
    						 }
    						
    						 if(yn2.equals("y"))
    						 {
    							 creditValid = 1;
    						 }
    					}

    					System.out.print("How much do you want to start with? ");
    					int bet = scan.nextInt();
    				
    					while(creditValid == 1 && bet > (int)credit)
    					{
    						System.out.println("Your balance is not enough !!!. Please insert a valid balance");
    						System.out.print("How much do you want to start with? ");
    						bet = scan.nextInt();
    					}
    					while(bet > player.element().getMoney() && creditValid == 0 )
    					{
    						System.out.println("Your balance is not enough !!!. Please insert a valid balance");
    						System.out.print("How much do you want to start with? ");
    						bet = scan.nextInt();
    					}
    					if(player.element().whichclass() == 2 && (yn2.equalsIgnoreCase("y")||yn2.equalsIgnoreCase("YES")))
    					{
    						((superVIP) player.element()).setUsingBalance(true);
    						((superVIP) player.element()).setCreditBeingUsed(bet);
    					}
    					else
    					{
    						player.element().setMoneyBeingUsed(bet);
    					}
    					System.out.println("Adding " + player.element().getName() + " to the game\n");
    					playerInGame.add(player.remove());	

    					if(option > 4 || option < 1)
    					{
    						System.out.println("Invalid option. Please choose one of the valid option !!! ");
    						System.out.print("Option --> ");
    						option = scan.nextInt();
    					}
    					break;
    				}
    				if(option != 1)
    				{
    					break;
    				}
    				break;
    			}

    			case 2:
    			{// Remove a player from game
    		    		String removeName;
    		    		System.out.print("Which player you want to remove? " );
    		    		removeName = scan.next();
    		    		Queue<Player> temp = new LinkedList<Player>();
    		    		int i = 0;
    		    		while(playerInGame.peek() != null)
    		    		{
    		    			if(!playerInGame.element().getName().equals(removeName))
    		    			{
    		    				temp.add(playerInGame.element());
    		    				playerInGame.remove();
    		    			}
    		    			else // found it
    		    			{
    		    				playerInGame.element().displayStatus();
    		    				playerInGame.remove();
    		    				i++;
    		    			}
    		    		}
    		    		while(temp.peek()!= null)
    		    		{
    		    			playerInGame.add(temp.element());
    		    			temp.remove();
    		    		}
    		    		if(i == 0)
    		    		{
    		    			System.out.println("The name is not found or spelling wrong!!! Insert again");
    		    			System.out.println();
    		    		}
    		    		
    		    		break;
    			}
    			case 3:
    				// Play one round (just use the roulette game: bet, spin, etc)
    				if(playerInGame.isEmpty())
    				{
    					System.out.println("There is no one to play the game.\n");
    					break;
    				}
    				for(int i = 1; i <= numOfGames; i++)
    				{
    					if(index == i)
    					{
    						roundsEachGame[index]++;
    					}
    				}
    				for(Player p: playerInGame)
    				{
    					boolean keepBetting = true;
    					while(keepBetting)
    					{
    						p.makeBet(scan); 
    						numbets++;
    						if(p.whichclass()>0 && ((VIP)p).getTimesBet() <3)
    						{
    							System.out.println("Would you like to make another bet? (Y/N)");
            					String betResponse = scan.next();
            					if((betResponse.equalsIgnoreCase("n")||betResponse.equalsIgnoreCase("No")))
            					{
            						keepBetting = false;	
        							System.out.println("----------------------------------");
        						}
    						}
    						else
    						{
    							keepBetting = false;
    						}
        					
    					}
    					if(p.whichclass() == 2)
    					{
    						 ((superVIP) p).incTimesPlayed();
    					}
    					
    				}
    				betsEachGame.add(numbets);
    				spin();
    				
    				for(Player p: playerInGame)
    				{
    					p.payment();
    					if(p.whichclass() > 0)
    					{
    						((VIP) p).resetTimesBet();
    					}
    				}
    				numbets = 0;
    				System.out.println();
    				break;
    			case 4:
    				mainMenu();
    				mainMenuOption();// Return to main menu
    				break;
    			default:
    				System.out.println("Please enter a valid option!");
    				break;

    			}
    			if(option < 5 && option > 0)
    			{
    				System.out.print("Option --> ");
    				option = scan.nextInt();
    			}
    			if(option > 4 || option < 1)
    			{
    				System.out.print("Invalid option. Please choose one of the valid option !!! ");
    				System.out.print("Option --> ");
    				option = scan.nextInt();
    			}
    		}
    	}
    	catch(InputMismatchException e)
    	{
    		System.out.println("Please enter a valid option (1, 2, 3). Going back to Game Menu.");
    		gameMenuOption(gamename, index);
    	}
    }
    //=====================================================================
    //  Presents betting options
    //=====================================================================
    public static void betOptions()
    {
      	System.out.println("Betting Options:");
      	System.out.println("    1. Bet on black (even numbers)");
      	System.out.println("    2. Bet on red (odd numbers)");
      	System.out.println("    3. Bet on a number between " + MIN_NUM +
      			" and " + MAX_NUM);
      	System.out.println();
    }
    
    // Spins the wheel
    public static void spin()
    {
    	// can use nextRandom() for testing
    	Random generator = new Random();
    	String n = "";
    	String color1 = "";
    	int ballPosition = generator.nextInt(Roulette.MAX_POSITIONS);
    	
    	if(ballPosition % 2 == 0 && ballPosition != 0 && ballPosition != 1)
    	{
    		color = BLACK;
    		ballPosition = ballPosition - 2;
    		color1 = "Black";
    		n = ("" + ballPosition);
    		
    	}
    	else if(ballPosition == 0 || ballPosition == 1)
    	{
    		color = GREEN;
    		color1 = "Green";
    		if(ballPosition == 1)
    		{
    			n = "00";
    		}
    		else
    		{
    			n = ("" + ballPosition);
    		}
    	
    	}
    	else  if (ballPosition % 2 != 0 && ballPosition != 0 && ballPosition != 1)
    	{
    		color = RED;
    		ballPosition = ballPosition - 2;
    		color1 = "Red";
    		n = ("" + ballPosition);
    	}
    	ballpositions.add("" + color1 + " " + n);
    	System.out.println("Result of the spin: ");
    	System.out.println("\tColor:"+ color1);
    	System.out.println("\tNumber:" + n);
    }
    
    // Payoff method for number bet
    public static int payoff(int betAmt, int betType, int numberBet) // numberBet == number (Player)
    {
    	int pay = 0;
    	if(betType == 3)
    	{
    		if(numberBet == ballPosition)
    		{
    			pay = NUMBER_PAYOFF * betAmt;
    		}
    	}
    	else if(betType == 1)
    	{
    		if(color == BLACK)
    		{
    			pay = COLOR_PAYOFF * betAmt;
    		}
    	}
    	else if (betType == 2)
    	{
    		if(color == RED)
    		{
    			pay = COLOR_PAYOFF * betAmt;
    		}
    	}
    	return pay;
    }
    
    // Returns a simulated "random" value for testing
    // Assume a value between 0 and 36
    public static int nextRandom()
    {
    	int num = randValues[next];
    	next++;
    	next = next % randValues.length;	// back to 0 if needed
    	return num;
    }
	public static int getN() {
		return n;
	}
     
}





















