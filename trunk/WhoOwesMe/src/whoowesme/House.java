package whoowesme;

import java.util.*;
import javax.jdo.Transaction;
import com.google.appengine.api.users.*;
import javax.jdo.PersistenceManager;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.*;
import com.google.appengine.api.datastore.Key;
import whoowesme.Bill;

@PersistenceCapable
public class House {

	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent	
	public List<Bill> houseGrid;
	
	@Persistent
	String housename;
	
	public House(String hn){
		housename = hn;
		houseGrid = new ArrayList<Bill>();
		addBill(housename, housename, 0.0, "init");
	}

	public void addBill(String user, String billed, double amt, String item){
		
		Bill b = new Bill(user, billed, amt, item);
		houseGrid.add(b);
	}

	public List<Bill> getOwed(User in)
	{
		List<Bill> out = new ArrayList<Bill>();
		
		for(int i = 0; i < houseGrid.size(); i++)
		{
			
			if(houseGrid.get(i).getOwed() == in.getNickname())
			{
				out.add(houseGrid.get(i));
			}
		}
		
		return out;
	}
	
	public List<Bill> getOwes(User in)
	{
		List<Bill> out = new ArrayList<Bill>();
		
		for(int i = 0; i < houseGrid.size(); i++)
		{
			
			if(houseGrid.get(i).getOwes() == in.getNickname())
			{
				out.add(houseGrid.get(i));
			}
		}
		
		return out;
	}
	public int getNumBills()
	{
		return houseGrid.size();
	}
	public String getPersonOwed(int i)
	{
		return houseGrid.get(i).getOwed();
	}
	public String getPersonOwes(int i)
	{
		return houseGrid.get(i).getOwes();
	}
}

