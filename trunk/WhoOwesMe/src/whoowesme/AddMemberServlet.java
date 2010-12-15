package whoowesme;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;


import java.io.*;
import java.util.*;

public class AddMemberServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		String user = userService.getCurrentUser().getNickname();
		
		String houseName = req.getParameter("housename");
		String newHouseMate = req.getParameter("name");
	
		
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

					if(h.getHouseName().equalsIgnoreCase(houseName))
					{
						theHouse = h;
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
	
			//This function add the amount to the bill if it exists,
			//makes a new one if it doesn't exist.
			theHouse.addHouseMate(newHouseMate);
	
			
			pm.makePersistent(theHouse);
			
			tx.commit();
			
			
		} finally {
			if (tx.isActive()) {
                tx.rollback();
            }
			pm.close();
			resp.sendRedirect("/whoowesme.jsp");
		}

		
	}
}