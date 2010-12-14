package whoowesme;

import javax.jdo.PersistenceManager;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;


import java.io.*;
import java.util.*;

public class AddBillServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		
		double amount = Double.parseDouble(req.getParameter("amount"));
		String Owes = req.getParameter("owes");
		String whatFor = req.getParameter("itemName");
	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String house = "select from whoowesme.House";
		
		List<House> houseList = (List<House>)pm.newQuery(house).execute();
		House theHouse = null;
		
		if(!houseList.isEmpty())
		{
			for(House h:houseList)
			{
				int numBills = h.getNumBills();
				for(int i = 0; i < numBills; i++)
				{
					if(h.getPersonOwed(i).equalsIgnoreCase(user.getNickname())
							|| h.getPersonOwes(i).equalsIgnoreCase(user.getNickname()))
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
			return;
		}

		//This function add the amount to the bill if it exists,
		//makes a new one if it doesn't exist.
		theHouse.AddEditBill(user.getNickname(), Owes, amount, whatFor);

		try{
			pm.makePersistent(theHouse);
		}finally{
			pm.close();
		}

		resp.sendRedirect("/whoowesme.jsp");
	}
}