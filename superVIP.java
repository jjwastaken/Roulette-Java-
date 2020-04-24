import java.util.Scanner;
public class superVIP extends VIP
{
	private double balance,crdt = 0;
	private boolean useBalance = false;
	int timesPlayed = 0, timesWidrawnCredit = 0;
	
	private int count;
	public superVIP(String playerName, int initialMoney, int dits, double bal)
	{
		super(playerName, initialMoney, dits);
		balance = bal;
		timesPlayed = 0;
	}
	 public int whichclass()
	 {
	    	return 2;
	 }
	
	 public double getBalance()
	 {
		return balance;
	 }
	 public void setBalance(double bal)
	 {
		 balance = bal;
	 }
	 public void setCreditBeingUsed(double bet)
	 {
		 crdt = bet;
		 balance -= bet;
	 }
	 public double getCreditBeingUsed()
	 {
		 return crdt;
	 }
	 public void incTimesPlayed()
	 {
		 timesPlayed++;
	 }
	 public int bonus()
	 {
		 if (timesPlayed > 5)
		 {
			 return 5;
		 }
		 else if(timesPlayed >= 3 && timesPlayed <= 5)
		 {
			 return 1;
		 }
		 else 
		 {
			 return 0;
		 }
	 } 
	 public void makeBet(Scanner scan)
	 {
		
		if(useBalance)
		{
			String response = "y";
	    	if(crdt < Roulette.MIN_BET)
	    	{
	    		System.out.println(name + " does not have enough money to continue. Would you like to withdraw $" 
	    	+ RELOAD_AMOUNT + " from your credit? (Y/N)");
	    		response = scan.next().toUpperCase();
	    		if (response.equals("Y") || response.equals("YES"))
	    			{
	    			withdrawFromCredit();
	    			if (crdt <= 0)
	    				return;
	    			System.out.println("New Balance: " + crdt);
	    			}
	    		
	    		else
	    			return;
	    	}
	    	System.out.println(name + "'s current Balance: $" + getCreditBeingUsed());//crdt
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
	      	bets[timesBet][2] = 0;
	      	while(bets[timesBet][0] < Roulette.MIN_BET || bets[timesBet][0] > crdt)
	      	{
	      		System.out.println("Invalid money. Please insert again");
	      		System.out.println("Current Balance: " + getCreditBeingUsed());
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
	      	crdt -= bets[timesBet][0];
	      	timesBet++;
	      	
		}
		else
		{
			super.makeBet(scan);
		}
		
	 }
	 public void payment()
	 {
		
		 
		 if(useBalance)
		 {
			 for(int i=0;i<3;i++)
			 {
				 if(bets[i][0]!=0)
				 //System.out.println("bets :" + bets[i][0]);
				 //System.out.println("bet type :" + bets[i][1]);
				 //System.out.println("bet number :" + bets[i][2]);
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
				    	Roulette.setCasino(bets[i][0]);
						if(win > 0)
						{
							crdt += win;
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
		 
		 
		 else
		 {
			 super.payment();
		 }
		 
	 }
	 public boolean usingBalance()
	 {
		 return useBalance;
	 }
	 public void setUsingBalance(boolean u)
	 {
		 useBalance = u;
	 }
	
	 private void withdrawFromCredit()
	 {
		 if(balance > RELOAD_AMOUNT)
		 {
			 balance -= RELOAD_AMOUNT;
			 crdt += RELOAD_AMOUNT; 
			 timesWidrawnCredit++;
		 }
		 else
		 {
			 System.out.println("Not enough funds available.");
		 }
		
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
		 	
		 	int cashB = (int)(absTotal * 0.05) + this.bonus();
		 	Roulette.setCashB(cashB);
		 System.out.println("You received $" + cashB + " of cash back.");
		 System.out.print("Do you want your money in your credit balance? ");
		 Scanner sc = new Scanner(System.in);
		 String ans = sc.next();
		 boolean valid = false;
		 if (ans.equals("y") || ans.equals("n"))
		 {
			 valid = true;
		 }
		 while(!valid) // false
		 {
			 System.out.println("Please insert \"y\" or \"n\" ");
			 System.out.print("Do you want your money in your credit balance? ");
			 ans = sc.next();
		 }
		 if (ans.equals("y"))
		 {
			 double newB = cashB + balance + crdt;
			 setBalance(newB);
			 System.out.println("You now have $"+ newB + " in your account.");
			 
		 }
		 else if (ans.equals("n"))
		 {
			 System.out.println("Here your $"+ cashB + " of cash back.");
		 } 
		 System.out.println();
		 
		 setInitialMoney((int)moneyUsed);
		 System.out.println();
	 }
}






