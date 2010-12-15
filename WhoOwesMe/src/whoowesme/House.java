package whoowesme;

import java.util.*;
import com.google.appengine.api.users.*;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.io.*;
import com.google.appengine.api.datastore.Key;
import whoowesme.Bill;
import whoowesme.Grocery;

@PersistenceCapable
public class House {

	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent	
	public List<Bill> houseGrid;
	
	@Persistent
	public List<Grocery> groceryList;
	
	@Persistent
	public List<String> houseMates;
	
	@Persistent
	public String houseName;
	
	public House(String username, String housename){
		houseName = housename;
		houseMates = new ArrayList<String>();
		houseMates.add(username);
		houseGrid = new ArrayList<Bill>();
		groceryList = new ArrayList<Grocery>();
	}

	public String getHouseName()
	{
		return houseName;
	}
	public void addHouseMate(String name)
	{
		houseMates.add(name);
	}
	public String getHouseMate(int index)
	{
		return houseMates.get(index);
	}
	public List<String> getAllHouseMates()
	{
		return houseMates;
	}
	public int getNumHouseMates()
	{
		return houseMates.size();
	}
	public void addBill(String user, String billed, double amt, String item){
		
		Bill b = new Bill(user, billed, amt, item);
		houseGrid.add(b);
	}
	public void addGrocery(String user, String content, Date date)
	{
		Grocery g = new Grocery(user, content, date);
		groceryList.add(g);
	}
	public void emptyGrocery()
	{
		groceryList.clear();
	}
	public void AddEditBill(String owed, String owes, double amt, String item)
	{
		boolean billFound = false;
		for(Bill b:houseGrid)
		{
			if(b.getOwed().equalsIgnoreCase(owed) && b.getOwes().equalsIgnoreCase(owes))
			{
				b.addAmt(amt);
				b.changeItem(item);
				billFound = true;
				break;
			}
		}
		if(!billFound)
		{
			addBill(owed, owes, amt, item);
		}
	}

	public List<Bill> getOwed(User in)
	{
		List<Bill> out = new ArrayList<Bill>();
		
		for(int i = 0; i < houseGrid.size(); i++)
		{
			Bill b = houseGrid.get(i);
			if(b.getOwed().equalsIgnoreCase(in.getNickname()) && 
					b.getAmt() != 0)
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
			Bill b = houseGrid.get(i);
			
			if(b.getOwes().equalsIgnoreCase(in.getNickname()) && 
					b.getAmt() != 0)
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
	public List<Grocery> getGrocery()
	{
		return groceryList;
	}
}

