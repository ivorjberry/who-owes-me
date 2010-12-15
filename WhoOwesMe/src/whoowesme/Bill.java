package whoowesme;

import com.google.appengine.api.users.*;
import com.google.appengine.api.datastore.*;



import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.text.*;
@PersistenceCapable
public class Bill{
	
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent
	String PersonOwed;
	@Persistent
	String PersonOwes;
	@Persistent
	double amount;
	@Persistent
	String whatFor;
	
	public Key getKey()
	{
		return key;
	}
	
	public Bill(String Owed, String Owes, double amt, String whFor)
	{
		PersonOwed = Owed;
		PersonOwes = Owes;
		amount = roundTwoDecimals(amt);
		whatFor = whFor;
	}
	
	public void addAmt(double amt)
	{
		amount += roundTwoDecimals(amt);
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
	public String getItem()
	{
		return whatFor;
	}
	public void changeItem(String in)
	{
		whatFor = in;
	}
	
	public double roundTwoDecimals(double d)
	{
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}