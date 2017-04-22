import java.awt.Point;
import java.util.Random;
public class MapGenerator
{
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	public static final Terrain WALL = new Terrain(0, 1, Terrain.CLIFF);
	public static final Terrain CORRIDOR = new Terrain(1, 0, Terrain.GRASS);
	
	private static int recursionCounter = 0;
	private static Random r = new Random();
	
	private static boolean isWall(int variance)
	{
		boolean retVal = false;
		if(r.nextInt(variance) == 0)
		{
			retVal = true;
		}
		return retVal;
	}
	
	private static int changeDirection(int direction, int increment)
	{
		int retVal = 0;
		retVal = direction + increment;
		if(retVal < 0)
			retVal += 4;
		else if(retVal > 3)
			retVal -= 4;
		return retVal;
	}
	
	private static void safetySet(int x, int y, Terrain newTile)
	{
		if(x >= 0 && x < Game.map.length && y >= 0 && y < Game.map.length)
		{
			Game.map[x][y] = new Terrain(newTile);
		}
	}
	private static boolean safetyCheck(int x, int y)
	{
		boolean retVal = false;
		if(x >= 0 && x < Game.map.length && y >= 0 && y < Game.map.length)
		{
			if(Game.map[x][y] == null)
			{
				retVal = true;
			}
			else
			{
				System.out.println(Game.map[x][y].getName());
			}
		}
		return retVal;
	}
	
	public static void seedCorridor(int length, int variance, int direction, int x, int y)
	{
		recursionCounter++;
		System.out.println("test: " + recursionCounter);
		//A little safety measure.
		if(recursionCounter > 30)
			return;
		int lCounter = 0;
		int rCounter = 0;
		
		int xOff = 0;
		int yOff = 0;
		int xLeft = 0;
		int yLeft = 0;
		int xRight = 0;
		int yRight = 0;
		
		//Determine the coordinates to the left and right of the current square.
		switch(direction)
		{
			case NORTH:
				xLeft = x - 1;
				yLeft = y;
				xRight = x + 1;
				yRight = y;
				break;
			case EAST:
				xLeft = x;
				yLeft = y - 1;
				xRight = x;
				yRight = y + 1;
				break;
			case SOUTH:
				xLeft = x + 1;
				yLeft = y;
				xRight = x - 1;
				yRight = y;
				break;
			case WEST:
				xLeft = x;
				yLeft = y + 1;
				xRight = x;
				yRight = y - 1;
				break;
			default:
				System.out.println("BAD THINGS HAPPENING!");
				break;
		}
		if(safetyCheck(x, y))
			safetySet(x, y, new Terrain(WALL));
		if(safetyCheck(xLeft, yLeft))
			safetySet(xLeft, yLeft, new Terrain(WALL));
		if(safetyCheck(xRight, yRight))
			safetySet(xRight, yRight, new Terrain(WALL));
		
		
		for(int i = 1; i < length; i++)
		{
			//Orients the algorithm
			switch(direction)
			{
				case NORTH:
					xOff = x;
					yOff = y - i;
					xLeft = xOff - 1;
					yLeft = yOff;
					xRight = xOff + 1;
					yRight = yOff;
					break;
				case EAST:
					xOff = x + i;
					yOff = y;
					xLeft = xOff;
					yLeft = yOff - 1;
					xRight = xOff;
					yRight = yOff + 1;
					break;
				case SOUTH:
					xOff = x;
					yOff = y + i;
					xLeft = xOff + 1;
					yLeft = yOff;
					xRight = xOff - 1;
					yRight = yOff;
					break;
				case WEST:
					xOff = x - i;
					yOff = y;
					xLeft = xOff;
					yLeft = yOff + 1;
					xRight = xOff;
					yRight = yOff - 1;
					break;
				default:
					System.out.println("BAD THINGS HAPPENING!");
					break;
			}

			if(safetyCheck(xOff,yOff))
			{
				//Set the corridor square.
				safetySet(xOff, yOff, new Terrain(CORRIDOR));
				
				//Set the left border
				if(safetyCheck(xLeft, yLeft))
				{
					if(lCounter > 0)
					{
						safetySet(xLeft, yLeft, new Terrain(WALL));
						lCounter--;
					}
					else if(isWall(variance))
					{
						safetySet(xLeft, yLeft, new Terrain(CORRIDOR));
						seedCorridor(r.nextInt(10) + 3, r.nextInt(10) + 3, changeDirection(direction, -1), xLeft, yLeft);
						lCounter++;
					}
					else
					{
						safetySet(xLeft, yLeft, new Terrain(WALL));
					}
				}
				
				//Set the right border
				if(safetyCheck(xRight, yRight));
				{
					if(rCounter > 0)
					{
						safetySet(xRight, yRight, new Terrain(WALL));
						rCounter--;
					}
					else if(isWall(variance))	//If the side isn't supposed to be a wall...
					{
						safetySet(xRight, yRight, new Terrain(CORRIDOR));
						seedCorridor(r.nextInt(10) + 3, r.nextInt(10) + 3, changeDirection(direction, 1), xRight, yRight);
						rCounter++;
					}
					else	//If the side is supposed to be a wall, make it so.
					{
						safetySet(xRight, yRight, new Terrain(WALL));
					}
				}
			}
			else
			{
				break; //@TODO: Sides?
			}
		}
		
		//Orients the algorithm
		switch(direction)
		{
			case NORTH:
				xOff = x;
				yOff = y - 1;
				xLeft = xOff - 1;
				yLeft = yOff;
				xRight = xOff + 1;
				yRight = yOff;
				break;
			case EAST:
				xOff = x + 1;
				yOff = y;
				xLeft = xOff;
				yLeft = yOff - 1;
				xRight = xOff;
				yRight = yOff + 1;
				break;
			case SOUTH:
				xOff = x;
				yOff = y + 1;
				xLeft = xOff + 1;
				yLeft = yOff;
				xRight = xOff - 1;
				yRight = yOff;
				break;
			case WEST:
				xOff = x - 1;
				yOff = y;
				xLeft = xOff;
				yLeft = yOff + 1;
				xRight = xOff;
				yRight = yOff - 1;
				break;
			default:
				System.out.println("BAD THINGS HAPPENING!");
				break;
		}
		if(safetyCheck(xOff, yOff)) safetySet(xOff, yOff, new Terrain(WALL));
		if(safetyCheck(xLeft, yLeft)) safetySet(xLeft, yLeft, new Terrain(WALL));
		if(safetyCheck(xRight, yRight)) safetySet(xRight, yRight, new Terrain(WALL));
		System.out.println("testOUT: " + recursionCounter);
		if(recursionCounter > 0) recursionCounter--;
	}
}
