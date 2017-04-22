import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** 
 * @title Evil Robot Quest
 * @author Jonathan Lynn
 * @version 0.1 Alpha
 */

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener
{
	/** These variables determine the game size. */
	public static final int TILE_SIZE = 36;
	private static final int MAP_WIDTH = 13;
	
	/** These variables determine the map size and location. */
	private static final int MAP_X = TILE_SIZE / 2;
	private static final int MAP_Y = TILE_SIZE / 2;
	private static final int MAP_RADIUS = MAP_WIDTH / 2;
	
	/** These variables determine the navpad location. */
	private static final int NAVPAD_X = TILE_SIZE / 2;
	private static final int NAVPAD_Y = (TILE_SIZE * 10) + (TILE_SIZE / 2);
	
	/** These variables determine the toolbar location */
	private static final int TOOLBAR_X = TILE_SIZE * 4;
	private static final int TOOLBAR_Y = (TILE_SIZE * 10) + (TILE_SIZE / 2);
	
	/** This holds the current map. */
	public static Terrain[][] map;
	/** This determines whether or not to accept user input. Probably not used until we deal w/ threads. */
	public static boolean acceptInput = true;
	
	
	private String mapName;
	/** These hold the images being used by the game. Bad idea for character? */
	Image currentMap;
	Image character;
	Image gui;
	
	/** */
	Player user;
	Person[] monsterList;
	
	public static void main(String[] args)
	{
		Game screen = new Game();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(screen);
		frame.add(screen);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	public Game()
	{
		//Read the map
		map = this.readMap2D("map3", "@map3");
		
		//Randomly Generate Map
//		map = new Terrain[75][75];
//		mapName = "newTerrain";
//		MapGenerator.seedCorridor(20, 10, 2, 25, 0);
//		for(int i = 0; i < 75; i++)
//		{
//			for(int q = 0; q < 75; q++)
//			{
//				if(map[q][i] == null)
//					map[q][i] = new Terrain(2, 0, Terrain.BLANK);
//			}
//		}
		
		//Load the map and character images.
		currentMap = new ImageIcon(this.getClass().getResource(mapName + ".png")).getImage();
		character = new ImageIcon(this.getClass().getResource("newCharacters.png")).getImage();
		
		//Temporarily set the player up
		user = new Player("John-bot", 12, 10, 11, 9, 15, "CYKBOT", 1, 1);
		monsterList = new Person[5];
		monsterList[0] = user;
		//Get painting!
		repaint();
	}
	public void paint(Graphics g)
	{
		//Create the Graphics2D object
		Graphics2D g2d = (Graphics2D)g;
		
		//Render the interface
		//Render the map
		for(int i = 0; i < 15; i++)
		{
			g2d.setColor(new Color(i * 10, i * 10, i * 10));
			g2d.drawRect(MAP_X - i, MAP_Y - i, MAP_WIDTH * TILE_SIZE + 2 * i, MAP_WIDTH * TILE_SIZE + 2 * i);	
		}
		int srcX = 0;	//Source Coordinates
		int srcY = 0;
		int desX = 0;	//Destination Coordinates
		int desY = 0;
		for(int y = 0; y < MAP_WIDTH; y++)
		{
			for(int x = 0; x < MAP_WIDTH; x++)
			{
				//Draw the tile. This is rather messy.
				//Calculate the destination, using the map offset and the x/y values.
				desX = MAP_X + (x * TILE_SIZE);
				desY = MAP_Y + (y * TILE_SIZE);
				//Is the tile within the map bounds?
				if((user.getX() + x - MAP_RADIUS) >= 0 && (user.getX() + x - MAP_RADIUS) < map.length && 
					(user.getY() + y - MAP_RADIUS) >= 0 && (user.getY() + y - MAP_RADIUS) < map.length)
				{
					//This is complicated.
					srcX = (map[user.getX() + x - MAP_RADIUS][user.getY() + y - MAP_RADIUS].getSrcX()) * TILE_SIZE;
					srcY = (map[user.getX() + x - MAP_RADIUS][user.getY() + y - MAP_RADIUS].getSrcY()) * TILE_SIZE;
					g2d.drawImage(currentMap, desX, desY, desX + TILE_SIZE, desY + TILE_SIZE,
							      srcX, srcY, srcX + TILE_SIZE, srcY + TILE_SIZE, null);
				}
				else	//If not, draw a blank image (the first image of every tileset.
				{
					//Paint a blank tile.
					g2d.drawImage(currentMap, desX, desY, desX + TILE_SIZE, desY + TILE_SIZE, 0, 0, TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
		
		//Render the Player
		desX = (MAP_RADIUS) * TILE_SIZE + MAP_X;
		desY = (MAP_RADIUS) * TILE_SIZE + MAP_Y;
 		g2d.drawImage(character, desX, desY, desX + TILE_SIZE, desY + TILE_SIZE, 0, 36, 36, 72, null);
 		//user.draw(desX, desY, "Walk", g2d);
	}
	public void keyTyped(KeyEvent e) {} 	//Doesn't do anything.
	public void keyPressed(KeyEvent e) {} 	//Doesn't do anything.
	
	/** This method gets key input from the user, and calls the appropriate functions. */
	public void keyReleased(KeyEvent e)
	{
		acceptInput = false;
		int deltaX = 0;
		int deltaY = 0;
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				deltaY--;
				break;
			case KeyEvent.VK_RIGHT:
				deltaX++;
				break;
			case KeyEvent.VK_DOWN:
				deltaY++;
				break;
			case KeyEvent.VK_LEFT:
				deltaX--;
				break;
		}
		user.moveRelative(deltaX, deltaY);
		repaint();
		acceptInput = true;
	}
	
	/** This method takes two coordinates and checks to see if there is a monster there.
	 * @return whether or not a monster is there. */
	public boolean checkForMonster(int newX, int newY)
	{
		boolean retVal = false;
		for(int i = 0; i < monsterList.length; i++)
		{
			if(map[newX][newY] == map[monsterList[i].getX()][monsterList[i].getY()])
			{
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	
	/** This long, complicated, really messy method reads the .map file. Needs cleaning. */
	public Terrain[][] readMap2D(String fileName, String tag)
	{
		Scanner input;
		//Create the new file.
		File source = new File(fileName + ".map");
		
		//Information holders.
		String line = "";
		String[] elements;
		String[][] subElements = null;
		//Return value.
		Terrain[][] retVal = new Terrain[0][0];
		if(source.exists())
		{
			try
			{
				input = new Scanner(source);
				//Locate the map via the supplied tag. This is so that multiple maps
				//can theoretically be stored in a single file.
				while(!line.contains(tag))
				{
					line = input.next();
					System.out.println(line);
				}
				//Get the map dimensions (NOTE: These allow for rectangular maps in 
				//						  future, but for now they should only be square.
				retVal = new Terrain[input.nextInt()][input.nextInt()];
				//Get the map name.
				mapName = input.nextLine().trim();
				
				//Get the tile information.
				for(int y = 0; y < retVal.length; y++)
				{
					line = input.nextLine();	//Read the line of Terrain tiles.
					elements = line.split(":");	//Split up the line into individual tiles.
					subElements = new String[elements.length][3];	//Size the subElements array.
					for(int i = 0; i < elements.length; i++)
					{
						//Fill subElements with the x, y, name values for the
						//individual Terrain tiles.
						subElements[i] = elements[i].trim().split(",");
					}
					for(int x = 0; x < elements.length; x++)
					{
						//Send the x, y, and name values to the Terrain pseudo-constructor,
						//which returns a new Terrain object to store in retVal;
						retVal[x][y] = Terrain.readTerrain(
								Integer.parseInt(subElements[x][0]), 
								Integer.parseInt(subElements[x][1]),
								subElements[x][2].toLowerCase());
					}
				}
				//Close the scanner.
				input.close();
				
			}	//If the file cannot be found.
			catch (FileNotFoundException e)
			{
				System.out.println("ERROR: File suffers from nonexistancy disorder.");
			}
		}
		return retVal;
	}
}
