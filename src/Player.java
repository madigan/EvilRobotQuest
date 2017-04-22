
public class Player extends Person
{
	private int level;
	private int exp;
	
	public Player()
	{
		super();
		this.setRace("CYKBOT");
		this.setLevel(0);
		this.setExp(0);
	}
	public Player(String name, int s, int d, int i, int c, int cash, String race, int x, int y)
	{
		super(name, s, d, i, c, cash, race, x, y);
		this.setRace(race);
		this.setLevel(0);
		this.setExp(0);
	}
	public Player(Player other)
	{
		super(other);
		this.setRace(other.getRace());
		this.setLevel(other.getLevel());
		this.setExp(other.getExp());
	}
	private void setLevel(int level)
	{
		this.level = level;
	}
	private int getLevel()
	{
		return level;
	}
	private void setExp(int exp)
	{
		this.exp = exp;
	}
	/** @param The amount of experience to add. Also incriments the level if needed. */
	public void addExp(int exp)
	{
		this.exp += exp;
		if(this.getExp() >= (int)(100 + this.getLevel() + (Math.pow(this.getLevel(), 1.2) * 100)))
		{
			this.setExp(this.getExp() - (int)(100 + this.getLevel() + (Math.pow(this.getLevel(), 1.2) * 100)));
			this.setLevel(this.getLevel() + 1);
		}
	}
	private int getExp()
	{
		return exp;
	}

	public void setRace(String race)
	{	//TODO: Finish this, as I draw the new graphics.
		super.setRace(race);
		if(getRace().toUpperCase() == "CYKBOT")
		{
			this.setStrength(getStrength() - 1);
			this.setIntelligence(this.getIntelligence() + 2);
		}
		else if(getRace().toUpperCase() == "DYNAMO")
		{

		}
		else if(getRace().toUpperCase() == "TERMIGATER")
		{

		}
		else if(getRace().toUpperCase() == "ROBOHAMSTER")
		{

		}
		else if(getRace().toUpperCase() == "DISSECTOR")
		{

		}
		else if(getRace().toUpperCase() == "")
		{

		}
	}
}
