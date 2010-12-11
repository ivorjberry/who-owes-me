package whoowesme;

import com.google.appengine.api.users.User;

public class Bill{
	User PersonOwed;
	User PersonOwes;
	int amount;
	public Bill(User Owed, User Owes, int amt)
	{
		PersonOwed = Owed;
		PersonOwes = Owes;
		amount = amt;
	}
	
	public void addAmt(int amt)
	{
		amount += amt;
	}
	public int getAmt()
	{
		return amount;
	}
	public User getOwed()
	{
		return PersonOwed;
	}
	public User getOwes()
	{
		return PersonOwes;
	}
}