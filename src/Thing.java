import java.util.ArrayList;

/** This is the basic skeleton for things like weapons and armor. */
public class Thing
{
	private String name;
	private float weight;
	private int worth;
	
	public Thing()
	{
		this.setName("A Thing");
		this.setWeight(0);
		this.setWorth(12);
	}
	public Thing(String name, float weight, int worth)
	{
		this.setName(name);
		this.setWeight(weight);
		this.setWorth(worth);
	}
	private void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	private void setWeight(float weight)
	{
		this.weight = weight;
	}
	public float getWeight()
	{
		return weight;
	}
	public void setWorth(int worth)
	{
		this.worth = worth;
	}
	public int getWorth()
	{
		return worth;
	}
	
	public void pickUp(Person pickerUpper)
	{
		ArrayList<Thing> source = Game.map[pickerUpper.getX()][pickerUpper.getY()].getItems();
		//pickerUpper.getInventory().add();
		
	}
}
