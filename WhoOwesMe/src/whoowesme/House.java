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
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent	
	Vector<Vector<Integer>> houseGrid;
	
	@Persistent
	List<User> housemates;
	
	@Persistent
	String housename;
	
	public House(String hn){
		housename = hn;
	}
	public void addHousemate(){}
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
	

}
