package whoowesme;

public class Bill{
	String PersonOwed;
	String PersonOwes;
	int amount;
	String whatFor;
	public Bill(String Owed, String Owes, int amt, String whFor)
	{
		PersonOwed = Owed;
		PersonOwes = Owes;
		amount = amt;
		whatFor = whFor;
	}
	
	public void addAmt(int amt)
	{
		amount += amt;
	}
	public int getAmt()
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