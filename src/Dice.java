/** This is a simple Dice simulator. */
import java.util.Random;

public class Dice
{
	private static Random rnd = new Random();
	/** @param numberOfDice The number of dice to be rolled.
	 * @param diceRange The range of each individual die. */
	public static int roll(int numberOfDice, int diceRange)
	{
		int retVal = 0;
		for(int i = 0; i < numberOfDice; i++)
		{
			retVal += rnd.nextInt(diceRange) + 1;
		}
		return retVal;
	}
	/** Takes a standard die notation (1d6, 2d4, etc.) 
	 * @param The dice value. */
	public static int roll(String input) //Takes something like "1d6"
	{
		/** Split up the input value. */
		int numberOfDice = Integer.parseInt(input.split("d")[0]);
		int diceRange = Integer.parseInt(input.split("d")[1]);
		/** Send the split values to the roll function. */
		return roll(numberOfDice, diceRange);
	}
}
