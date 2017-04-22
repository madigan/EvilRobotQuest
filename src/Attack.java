import java.util.ArrayList;
public class Attack
{
	private static ArrayList<String> typeList = new ArrayList<String>();
	private String name;
	private String hitMessage;
	private String missMessage;
	private String type;
	private int damage;
	private int variance;
	
	@SuppressWarnings("unused")
	private Attack()
	{
		//We don't want to use this constructor.
	}
	public Attack(String name, String hitMessage, String missMessage, String type, int damage, int variance)
	{
		this.setName(name);
		this.setHitMessage(hitMessage);
		this.setMissMessage(missMessage);
		this.setType(type);
		this.setDamage(damage);
		this.setVariance(variance);
	}
	
	private void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	private void setHitMessage(String hitMessage)
	{
		this.hitMessage = hitMessage;
	}
	public String getHitMessage()
	{
		return hitMessage;
	}
	private void setMissMessage(String missMessage)
	{
		this.missMessage = missMessage;
	}
	public String getMissMessage()
	{
		return missMessage;
	}
	private void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return type;
	}
	private void setDamage(int damage)
	{
		this.damage = damage;
	}
	public int getDamage()
	{
		return damage;
	}
	private void setVariance(int variance)
	{
		this.variance = variance;
	}
	public int getVariance()
	{
		return variance;
	}
	
	public boolean isHit(String type)
	{
		boolean hit = false;
		return hit;
	}
	public int findDamage(String type)
	{
		int damage;
		if(this.getType() == "fire" && type == "water")
		{
			damage = -1 * (this.getDamage() - (int)(Math.random() * this.getVariance() * 3));
		}
		else if(this.getType() == "water" && type == "fire")
		{
			damage = -1 * (this.getDamage() + (int)(Math.random() * this.getVariance() * 3));
		}
		else
		{
			damage = -1 * (this.getDamage() + (int)(Math.random() * this.getVariance()));
		}
		return damage;
	}
	public void damage()
	{
		//Later, this could be used to ding up the attack.
	}
}
