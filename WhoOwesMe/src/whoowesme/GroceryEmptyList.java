package whoowesme;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.*;
import java.util.Date;
import java.util.List;

public class GroceryEmptyList extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getNickname();
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String house = "select from whoowesme.House";
		
		Transaction tx = pm.currentTransaction();
		try{
		
			tx.begin();
			List<House> houseList = (List<House>)pm.newQuery(house).execute();
			House theHouse = null;
			
			if(!houseList.isEmpty())
			{
				for(House h:houseList)
				{
					int numBills = h.getNumBills();
					for(int i = 0; i < numBills; i++)
					{
						if(h.getPersonOwed(i).equalsIgnoreCase(user)
								|| h.getPersonOwes(i).equalsIgnoreCase(user))
						{
							theHouse = h;
							break;
						}
					}
					if(theHouse != null)
					{
						break;
					}
				}
			}
			if(theHouse == null)
			{
				//House doesn't exist!
				//What should we do here?
				//This should never happen, but just leaving a return here
				//will exit as gracefully as we can if it does mess up
				return;
			}
			// Store the grocery entry 
			theHouse.emptyGrocery();
			
			pm.makePersistent(theHouse);
			
				
			
			tx.commit();
		} finally{
			if (tx.isActive()) {
                tx.rollback();
            }
			pm.close();
		}
		
		resp.sendRedirect("/whoowesme.jsp");
	}
}