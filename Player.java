// Class Player for CSCI 145 Project 2 Fall 19
// Modified by: 

import java.util.*;

//   Class Player represents one roulette player.
abstract class Player
{
	protected static final int RELOAD_AMOUNT = 100;
    protected int bet, money, betType, number;
    protected String name;  
    protected int total = 0;
    private int timesWithdrawn = 0;
    protected double moneyUsed = 0;
   
    //  The Player constructor sets up  name and initial available money.
    public Player (String playerName, int initialMoney)
    {
		name = playerName;
      	money = initialMoney;
   	} 
    public void setInitialMoney(int m)
	 {
		money += m;
	 }
    //  Returns this player's name.
    public String getName()
    {
      	return name;
    } 

    //  Returns this player's current available money.
    public int getMoney()
    {
      	return money;
    } 
    
    public int getTotal() 
    {
    	return total;
    }
	 public double getMoneyBeingUsed()
	 {
		 return moneyUsed;
	 }
    //  Prompts the user and reads betting information.
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
    	}
    	System.out.println(name + "'s current Balance: $" + getMoneyBeingUsed());
  		Roulette.betOptions();
  		System.out.print("Enter a bet option, " + name + " (1, 2, or 3): ");
  		betType = scan.nextInt(); // 1 || 2 || 3
  		while(betType != 1 && betType != 2 && betType != 3)
  		{
  			System.out.println("Invalid option !!! - PLEASE! CHOOSE AGAIN. ");
  			System.out.print("Enter a bet option, " + name + " (1, 2, or 3): ");
  	  		betType = scan.nextInt();
  		}
      	System.out.print("How much to bet: ");
      	bet = scan.nextInt(); // insert money
      	number = 0;
      	while(bet < Roulette.MIN_BET || bet > moneyUsed)
      	{
      		System.out.println("Invalid money. Please insert again");
      		System.out.println("Current Balance: " + getMoneyBeingUsed());
      		System.out.println("Min Bet: " + Roulette.MIN_BET);
      		System.out.print("How much to bet: ");
          	bet = scan.nextInt(); 
      	}
      	if(betType == 3)
       	{
       		System.out.println("Enter the number you would like to bet on: ");
       		number = scan.nextInt();
       		while(number < Roulette.MIN_NUM || number > Roulette.MAX_NUM)
       		{
       			System.out.println("Enter the number you would like to bet on: ");
           		number = scan.nextInt();
       		}
       	}
      	moneyUsed = moneyUsed - bet;
    } 
    public void Reset() // money = 5 -> money = 105
    {
    	money += 1 * RELOAD_AMOUNT;
    }
    //  Determines if the player wants to play again.
    public boolean playAgain(Scanner scan)
    {
      	String answer;

      	System.out.print ("Play again, " + name + "? [y/n] ");
      	answer = scan.next();
      	return (answer.equals("y") || answer.equals("Y"));
    } 
    
    // payment method (determines winnings)
    public void payment()
    {
    	int casino = 0;
    	int win = Roulette.payoff(bet,betType,number);
    	String[] singleBetInfo = new String[4];//an array of (player, bamount,btype,pay)
    	singleBetInfo[0] = name;
    	singleBetInfo[1] =  Integer.toString(bet);
    	if(betType == 1)
    	{
    		singleBetInfo[2] = "B";
    	}
    	else if (betType == 2)
    	{
    		singleBetInfo[2] = "R";
    	}
    	else
    	{
    		singleBetInfo[2] =  Integer.toString(number);	
    	}
    	singleBetInfo[3] = Integer.toString(win);
    	Roulette.setCasino(bet);
    	Roulette.gameSummary.add(singleBetInfo);
    	if(win > 0)
    	{
    		moneyUsed += win;
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
    		win = bet * -1;
    		System.out.println(name + " lost " + (win));
    		total += win; 
    	}
    }
 
    public void withdraw()
    {
    	if(money - RELOAD_AMOUNT < 0)
    	{
    		System.out.println("Your balance is not enough to withdraw.");
    		Reset();
    		System.out.println("Reset is activated");
    		moneyUsed += RELOAD_AMOUNT;
    		timesWithdrawn++;
    		money -= RELOAD_AMOUNT;
    		
    	}
    	else
    	{
	    	moneyUsed += RELOAD_AMOUNT;
	    	timesWithdrawn++;
	    	money -= RELOAD_AMOUNT;
    	}
    }
    
    public abstract void displayStatus();
    public abstract int whichclass();
    
    public void setMoneyBeingUsed(int bet)
    {
    	{
   		 moneyUsed = bet;
   		 money -= bet;
   	 }
    }
}








