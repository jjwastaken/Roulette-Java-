public class Regular extends Player
{

	public Regular(String playerName, int initialMoney)
	{
		super(playerName, initialMoney);
	}
	public int whichclass()
	{
		return 0;
	}
	 
	public void displayStatus()
    {
    	if (getTotal() > 0)
   		{
    		System.out.println(getName() + " left the game with winning amount of $" + getTotal() );
   		}
    	else
    	{
    		System.out.println(getName() + " left the game with losing amount of $" + (getTotal() * -1));
    	}
    	System.out.println();
    	setInitialMoney((int)moneyUsed);
    	
    	
    	System.out.println();
    }

}




