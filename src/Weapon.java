
public class Weapon extends Thing
{
	private String name;
	private float weight;
	private int cost;
	private String toHit;
	private String damage;
	private int bonusDamage;
	private String damageType;
	
	public Weapon(String name, float weight, int cost, String toHit, String damage, int bonusDamage, String damageType)
	{
		super(name, weight, cost);
		this.toHit = toHit;
		this.damage = damage;
		this.bonusDamage = bonusDamage;
		this.damageType = damageType;
	}
	
	public int getToHit()
	{
		return Dice.roll(this.toHit);
	}
	public int getDamage()
	{
		return Dice.roll(this.damage) + this.bonusDamage;
	}
	public String getDamageType()
	{
		return this.damageType;
	}
	public String toString()
	{
		String retVal = "\"" + this.getName() + "\" (" + this.getToHit() + "+" + this.bonusDamage + ")";
		return retVal;
	}
}
