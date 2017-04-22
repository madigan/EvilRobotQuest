import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/** This class is the foundation for all the players and monsters. */
public class Person
{
	private String name;
	private int strength;
	private int dexterity;
	private int intelligence;
	private int constitution;
	private int cash;
	private String race;
	private int x;
	private int y;
	
	private int maxHealth;
	private int health;
	private boolean visible;
	private Image srcImage;
	private ArrayList<Thing> inventory = new ArrayList<Thing>();
	
	public Person()
	{
		this.setName("Joe");
		this.setStrength(10);
		this.setDexterity(10);
		this.setIntelligence(10);
		this.setConstitution(10);
		this.setCash(0);
		this.setRace("CYKBOT");
		this.setX(0);
		this.setY(0);
		
		this.updateMaxHealth();
		this.setHealth(this.getMaxHealth());
		this.setVisible(true);
		this.setSrcImage(this.getRace());
	}
	public Person(String name, int s, int d, int i, int c, int cash, String race, int x, int y)
	{
		this.setName(name);
		this.setStrength(s);
		this.setDexterity(d);
		this.setIntelligence(i);
		this.setConstitution(c);
		this.setCash(cash);
		this.setRace(race);
		this.setX(x);
		this.setY(y);
		
		this.updateMaxHealth();
		this.setHealth(this.getMaxHealth());
		this.setVisible(true);
		this.setSrcImage(this.getRace());
	}
	public Person(Person other)
	{
		this.setName(other.getName());
		this.setStrength(other.getStrength());
		this.setDexterity(other.getDexterity());
		this.setIntelligence(other.getIntelligence());
		this.updateMaxHealth();
		this.setRace(other.getRace());
		this.setX(other.getX());
		this.setY(other.getY());
		
		this.setHealth(this.getMaxHealth());
		this.setCash(other.getCash());
		this.setVisible(other.isVisible());
		this.setSrcImage(other.getSrcImage());
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setHealth(int health)
	{
		this.health = health;
	} 
	public int getHealth()
	{
		return health;
	}
	public void updateMaxHealth()
	{
		this.maxHealth = this.getConstitution() + (this.getStrength() / 3);
	}
	public int getMaxHealth()
	{
		return maxHealth;
	}
	public void setCash(int cash)
	{
		this.cash = cash;
	}
	public int getCash()
	{
		return cash;
	}
	public void setStrength(int strength)
	{
		this.strength = strength;
	}
	public int getStrength()
	{
		return strength;
	}
	public void setDexterity(int dexterity)
	{
		this.dexterity = dexterity;
	}
	public int getDexterity()
	{
		return dexterity;
	}
	public void setIntelligence(int intelligence)
	{
		this.intelligence = intelligence;
	}
	public int getIntelligence()
	{
		return intelligence;
	}
	public void setConstitution(int constitution)
	{
		this.constitution = constitution;
	}
	public int getConstitution()
	{
		return constitution;
	}
	public void changeHealth(int health)
	{
		if(this.health + health >= this.maxHealth)
		{
			this.health = maxHealth;
		}
		else if(this.health + health <= 0)
		{
			this.health = 0;
		}
		else
		{
			this.health += health;
		}
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getX()
	{
		return x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getY()
	{
		return y;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	public boolean isVisible()
	{
		return visible;
	}
	public void setRace(String race)
	{
		this.race = race;
	}
	public String getRace()
	{
		return race;
	}
	public void setSrcImage(Image srcImage)
	{
		this.srcImage = srcImage;
	}
	public void setSrcImage(String fileName)
	{
		this.srcImage = new ImageIcon(this.getClass().getResource(fileName + ".png")).getImage();
	}
	public Image getSrcImage()
	{
		return srcImage;
	}
	public ArrayList<Thing> getInventory()
	{
		return this.inventory;
	}
	
	/** @return Whether the person is dead or not. */
	public boolean isDead()
	{
		boolean isDead = false;
		if(this.health == 0)
		{
			isDead = true;
		}
		return isDead;
	}

	public void driver()
	{
		if(this.isDead())
		{
			//TODO: Drop stuff.
			
			//Remove the person.
			Game.map[this.getX()][this.getY()].setOccupant(null);
		}
	}
	/** @return A string representation of the person. TODO: Make this more complete. Is it needed? */
	public String toString()
	{
		String output;
		output = "Name: " + this.name + "\n";
		output += "Health: " + this.health + "/" + this.maxHealth + "\n";
		output += "Cash: " + this.cash + "\n";
		return output;
	}
	
	/** This checks to see if the location the person wants to move to is
	 * indeed a valid place to move. If so, it moves the person there.
	 * This function should be provided with the exact coordinates.
	 * @param newX The new X position.
	 * @param newY The new Y position. */
	public void move(int newX, int newY)
	{	//Make sure the target location is inside the map.
		if(newX >= 0 && newX < Game.map.length && newY >= 0 && newY < Game.map.length)
		{	//Make sure the new location is passable and without another occupant.
			if(Game.map[newX][newY].isPassable() && !Game.map[newX][newY].hasOccupant())
			{
				Game.map[newX][newY].setOccupant(Game.map[this.getX()][this.getY()].getOccupant());
				Game.map[this.getX()][this.getY()].setOccupant(null);
				this.setX(newX);
				this.setY(newY);
			}
			else if(Game.map[newX][newY].hasOccupant())
			{
				this.attack(newX, newY);
			}
		}
	}
	
	/** This checks to see if the location the person wants to move to is
	 * indeed a valid place to move. If so, it moves the person there.
	 * This function should be provided with a relative position.
	 * @param deltaX The change in X position.
	 * @param deltaY The change in Y position. */
	public void moveRelative(int deltaX, int deltaY)
	{
		deltaX += this.getX();
		deltaY += this.getY();
		if(deltaX >= 0 && deltaX < Game.map.length && deltaY >= 0 && deltaY < Game.map.length)
		{
			if(Game.map[deltaX][deltaY].isPassable() && !Game.map[deltaX][deltaY].hasOccupant())
			{
				Game.map[deltaX][deltaY].setOccupant(Game.map[this.getX()][this.getY()].getOccupant());
				Game.map[this.getX()][this.getY()].setOccupant(null);
				this.setX(deltaX);
				this.setY(deltaY);
			}
			else if(Game.map[deltaX][deltaY].hasOccupant())
			{
				this.attack(deltaX, deltaY);
			}
		}
		
	}
	
	public void attack(int x, int y)
	{
		if(Game.map[x][y].hasOccupant())
		{
			int damage = 0;
			damage = (int) (this.getStrength() + (Math.random() * this.getDexterity()));
			Game.map[x][y].getOccupant().changeHealth(-1 * damage);
		}
	}
	
	/** This method is used to draw the person.
	 * @param dx The x coordinate of where to draw on the JPanel.
	 * @param dy The y coordinate of where to draw on the JPanel.
	 * @param pose The "Action" being performed (Walk, Attack, Die, etc.)
	 * @param g2d The Graphics2D object used to draw. */
	public void draw(int dx, int dy, String pose, Graphics2D g2d)
	{
		int sx = 0;
		int sy = 0;
		if(pose.equalsIgnoreCase("walk"))
		{
			sx = 0;
			sy = 0;
		}
		else if(pose.equalsIgnoreCase("attack"))
		{
			sx = 0;
			sy = 1 * Game.TILE_SIZE;
		}
		else if(pose.equalsIgnoreCase("death"))
		{
			sx = 0;
			sy = 2 * Game.TILE_SIZE;
		}
			
		g2d.drawImage(this.getSrcImage(), dx, dy, dx + Game.TILE_SIZE, dy + Game.TILE_SIZE,
										  sx, sy, sx + Game.TILE_SIZE, sy + Game.TILE_SIZE, null);
	}
}
