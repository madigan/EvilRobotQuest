import java.util.ArrayList;

public class Terrain
{
	private int srcX;
	private int srcY;
	private boolean passable;
	private String name;
	private float defense;
	private ArrayList<Thing> items;
	private Person occupant;
	
	public static final Terrain BLANK = new Terrain(0, 0, false, "Blank", 1);
	public static final Terrain GRASS = new Terrain(0, 0, true, "Grass", 1);
	public static final Terrain CLIFF = new Terrain(0, 0, false, "Cliff", 1);
	public static final Terrain WATER = new Terrain(0, 0, false, "Water", 1);
	public static final Terrain RIVER = new Terrain(0, 0, true, "River", 0.5f);
	
	public Terrain()
	{
		this.setSrcX(0);
		this.setSrcY(0);
		this.setPassable(false);
		this.setName("Blank");
		this.setDefense(1);
		
		this.items = new ArrayList<Thing>();
	}
	public Terrain(int x, int y)
	{
		this();
		this.setSrcX(x);
		this.setSrcY(y);
	}
	public Terrain(int x, int y, boolean passable, String name, float defense)
	{
		this(x, y);
		this.setPassable(passable);
		this.setName(name);
		this.setDefense(defense);
	}
	public Terrain(Terrain other)
	{
		this();
		this.setSrcX(other.getSrcX());
		this.setSrcY(other.getSrcY());
		this.setPassable(other.isPassable());
		this.setName(other.getName());
		this.setDefense(other.getDefense());
	}
	public Terrain(int x, int y, Terrain other)
	{
		this(x, y);
		this.setPassable(other.isPassable());
		this.setName(other.getName());
		this.setDefense(other.getDefense());
	}
	
	/** I really would rather have this as a constructor, but I suppose this works.
	 * This method creates a Terrain object using a pair of x,y coordinates and a
	 * string which holds the name/type of the desired Terrain.
	 * @return A Terrain objection created from the coordinates and a name String.
	 * @param x This is the column of the Terrain object in the source image.
	 * @param y This is the row of the Terrain object in the source image.
	 * @param name This is the name, or type of Terrain. */
	public static Terrain readTerrain(int x, int y, String name)
	{
		Terrain retVal;
		if(name.equalsIgnoreCase("blank"))
		{
			retVal = new Terrain(x, y, Terrain.BLANK);
		}
		else if(name.equalsIgnoreCase("grass"))
		{
			retVal = new Terrain(x, y, Terrain.GRASS);
		}
		else if(name.equalsIgnoreCase("cliff"))
		{
			retVal = new Terrain(x, y, Terrain.CLIFF);
		}
		else if(name.equalsIgnoreCase("water"))
		{
			retVal = new Terrain(x, y, Terrain.WATER);
		}
		else if(name.equalsIgnoreCase("river"))
		{
			retVal = new Terrain(x, y, Terrain.RIVER);
		}
		else
		{
			retVal = new Terrain(x, y, Terrain.BLANK);
		}
		return retVal;
	}
	
	/** @param srcX Sets the column in the source image which holds this terrain. */
	public void setSrcX(int srcX)
	{
		this.srcX = srcX;
	}
	/** @return The column in the source image which holds this terrain. */
	public int getSrcX()
	{
		return srcX;
	}
	/** @param srcY Sets the row in the source image which holds this terrain. */
	public void setSrcY(int srcY)
	{
		this.srcY = srcY;
	}
	/** @return The row in the source image which holds this terrain. */
	public int getSrcY()
	{
		return srcY;
	}
	/** @param passable Used to set whether the Terrain tile is passable or not. */
	private void setPassable(boolean passable)
	{
		this.passable = passable;
	}
	/** @return Whether this Terrain object is passable or not. */
	public boolean isPassable()
	{
		return passable;
	}
	/** @param name Used to set the name of the Terrain object. */
	private void setName(String name)
	{
		this.name = name;
	}
	/** @return The name of this Terrain object. */
	public String getName()
	{
		return name;
	}
	/** @param defense Sets the defense value of the Terrain object. */
	private void setDefense(float defense)
	{
		this.defense = defense;
	}
	/** @return defense the defense value of the Terrain object. */
	public float getDefense()
	{
		return defense;
	}
	/** @param newItem The new item to be added to the items list. */
	public void addItem(Thing newItem)
	{
		this.items.add(newItem);
	}
	/** @return The items contained in the Terrain object. */
	public ArrayList<Thing> getItems()
	{
		return new ArrayList<Thing>(this.items);
	}
	/** Only works if there isn't already an occupant.
	 * @param occupant the tile's new occupant. */
	public void setOccupant(Person occupant)
	{
		if(this.occupant == null)
			this.occupant = occupant;	//Memory Leak. Fix.
		else
			System.out.println("Unfortunately, the current space-time continuum does not allow two objects to simultaniusly occupy the same position. You're lame. Try something else.");
	}
	/** @return The current occupant of the Terrain object. */
	public Person getOccupant()
	{
		return occupant;	//Memory Leak. Fix.
	}
	/** @return Whether or not the tile is occupied. */
	public boolean hasOccupant()
	{
		boolean retVal = false;
		if(this.getOccupant() != null)
			retVal = true;
		return retVal;
	}
	
}
