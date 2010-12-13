package whoowesme;

public class Bill{
	private String PersonOwed;
	private String PersonOwes;
	private double amount;
	private String whatFor;
	public Bill(String Owed, String Owes, double amt, String whFor)
	{
		PersonOwed = Owed;
		PersonOwes = Owes;
		amount = amt;
		whatFor = whFor;
	}
	
	public void addAmt(double amt)
	{
		amount += amt;
	}
	public double getAmt()
	{
		return amount;
	}
	public String getOwed()
	{
		return PersonOwed;
	}
	public String getOwes()
	{
		return PersonOwes;
	}
}