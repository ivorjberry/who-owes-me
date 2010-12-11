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

@PersistenceCapable
public class House {
	private int numPeople;
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent	
	Vector<Bill> houseGrid;
	
	@Persistent
	List<User> housemates;
	
	@Persistent
	String housename;
	
	public House(String hn){
		housename = hn;
		numPeople = 0;
	}
	public void addHousemate(){
		numPeople++;
	}
	public void addBill(User user, User billed, Integer amt, String item){
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction txn = pm.currentTransaction();
		try{
			txn.begin();
			//put in the servlet??
			txn.commit();
		}finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
	}
	public int getNumPeople()
	{
		return numPeople;
	}
	public User getUser(int index)
	{
		return housemates.get(index);
	}
	public List<Bill> getOwed(User in)
	{
		List<Bill> out = new ArrayList<Bill>();
		
		for(int i = 0; i < houseGrid.size(); i++)
		{
			
			if(houseGrid.get(i).PersonOwed == in)
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
			
			if(houseGrid.get(i).PersonOwes == in)
			{
				out.add(houseGrid.get(i));
			}
		}
		
		return out;
	}

	

}

