import java.util.Scanner;

public class VIP extends Regular
{
	private int ditgits;
	protected int credit, timesBet = 0;
	int[][] bets = new int[3][3];
	public VIP(String playerName, int initialMoney, int dits)
	{
		super(playerName, initialMoney);
		ditgits = dits;
	}
	 public int whichclass()
	 {
	    	return 1;
	 }
	 public int getTimesBet()
	 {
		 return timesBet;
	 }
	 public void resetTimesBet()
	 {
		 timesBet =0;
	 }
	 public double getMoneyBeingUsed()
	 {
		 return moneyUsed;
	 }
	 public void displayStatus()
	 {
		
		 double absTotal = 0;
		 	if (getTotal() > 0)
	   		{
	    		System.out.println(getName() + " left the game with winning amount of $" + getTotal() );
	    		absTotal += getTotal();
	   		}
	    	else
	    	{
	    		System.out.println(getName() + " left the game with losing amount of $" + (getTotal() * -1));
	    		absTotal = getTotal()*-1;
	    	}
		 	int cashB = (int)(absTotal * 0.05);
		 	Roulette.setCashB(cashB);
		 	System.out.println("A cash back of $" +cashB + " is credited to your account " + ditgits);
		 	credit += cashB;
		 	System.out.println();
		 	setInitialMoney((int)moneyUsed);
		 	System.out.println();
	 }
	 public void makeBet(Scanner scan)
	 {
		 String response = "y";
	    	if(moneyUsed < Roulette.MIN_BET)
	    	{
	    		System.out.println(name + " does not have enough money to continue. Would you like to withdraw $100? (Y/N)");
	    		response = scan.next().toUpperCase();
	    		if (response.equals("Y") || response.equals("YES"))
	    			{
	    			withdraw();
	    			System.out.println("New Balance: " + moneyUsed);
	    			}
	    		
	    		else
	    			return;
	    	} //(int betAmt, int betType, int numberBet)
	    	System.out.println(name + "'s current Balance: $" + getMoneyBeingUsed());
	  		Roulette.betOptions();
	  		System.out.print("Enter a bet option, " + name + " (1, 2, or 3): ");
	  		bets[timesBet][1] = scan.nextInt(); // 1 || 2 || 3
	  		while(bets[timesBet][1] != 1 && bets[timesBet][1] != 2 && bets[timesBet][1] != 3)
	  		{
	  			System.out.println("Invalid option !!! - PLEASE! CHOOSE AGAIN. ");
	  			System.out.print("Enter a bet option, " + name + " (1, 2, or 3): ");
	  	  		bets[timesBet][1] = scan.nextInt();
	  		}
	      	System.out.print("How much to bet: ");
	      	bets[timesBet][0] = scan.nextInt(); // insert money
	      	bets[timesBet][2] = 0; //the betting number
	      	while(bets[timesBet][0] < Roulette.MIN_BET || bets[timesBet][0] > moneyUsed)
	      	{
	      		System.out.println("Invalid money. Please insert again");
	      		System.out.println("Current Balance: " + getMoney());
	      		System.out.println("Min Bet: " + Roulette.MIN_BET);
	      		System.out.print("How much to bet: ");
	      		bets[timesBet][0] = scan.nextInt(); 
	      	}
	      	if(bets[timesBet][1] == 3)
	       	{
	       		System.out.println("Enter the number you would like to bet on: ");
	       		bets[timesBet][2] = scan.nextInt();
	       		while(bets[timesBet][2] < Roulette.MIN_NUM || bets[timesBet][2] > Roulette.MAX_NUM)
	       		{
	       			System.out.println("Enter the number you would like to bet on: ");
	       			bets[timesBet][2] = scan.nextInt();
	       		}
	       	}
	      	moneyUsed = moneyUsed - bets[timesBet][0];
	      	timesBet++;		 
	 }

	 public void payment()
	 {
		 for(int i=0;i<3;i++)
		 {
			 if(bets[i][0] != 0)
			 {
				 	int casino = 0;
				 	int win = Roulette.payoff(bets[i][0],bets[i][1],bets[i][2]);
				 	String[] singleBetInfo = new String[4];//an array of (player, bamount,btype,pay)
			    	singleBetInfo[0] = name;
			    	singleBetInfo[1] =  Integer.toString(bets[i][0]);
			    	if(bets[i][1] == 1)
			    	{
			    		singleBetInfo[2] = "B";
			    	}
			    	else if (bets[i][1] == 2)
			    	{
			    		singleBetInfo[2] = "R";
			    	}
			    	else
			    	{
			    		singleBetInfo[2] =  Integer.toString(bets[i][2]);	
			    	}
			    	singleBetInfo[3] = Integer.toString(win);
			    	Roulette.gameSummary.add(singleBetInfo);
			    	Roulette.setCasino( bets[i][0]);
					if(win > 0)
					{
						money += win;
						if(win == 350)
			    			casino = casino - win + 10;
			    		else 
			    			casino -= win;
						Roulette.setCasino(casino);
						win = win / 2;
						System.out.println(name + " won " + (win));
						total += win;
					}
					else // win == 0
					{
						win = bets[i][0] * -1;
						System.out.println(name + " lost " + (win));
						total += win;
					}  
			 }

		 }
	 }
}






