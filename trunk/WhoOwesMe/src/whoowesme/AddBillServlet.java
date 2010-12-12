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
		
		
		int amount = Integer.parseInt(req.getParameter("amount"));
		String Owes = req.getParameter("owes");
		String whatFor = req.getParameter("itemName");
	
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String house = "select from whoowesme.House";
		
		List<House> houseList = (List<House>)pm.newQuery(house).execute();
		House theHouse = null;
		
		/*  Old way.  Should just look through list of bills
		for(House h:houseList)
		{
			int numPeople = h.getNumPeople();
			for(int i = 0; i < numPeople; i++)
			{
				if(h.getUser(i) == user)
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
		*/
		
		for(House h:houseList)
		{
			int numBills = h.houseGrid.size();
			for(int i = 0; i < numBills; i++)
			{
				if(h.houseGrid.get(i).getOwed() == user.getNickname()
					|| h.houseGrid.get(i).getOwes() == user.getNickname())
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
		if(theHouse == null)
		{
			//House doesn't exist!
			//What should we do here?
			return;
		}
		boolean billExists = false;
		for(int i = 0; i < theHouse.houseGrid.size(); i++)
		{
			if(theHouse.houseGrid.get(i).getOwed() == user.getNickname() &&
				theHouse.houseGrid.get(i).getOwes() == Owes)
				{
					theHouse.houseGrid.get(i).addAmt(amount);
					billExists = true;
				}
		}
		if(!billExists)
		{
			theHouse.addBill(user.getNickname(), Owes, amount, whatFor);
		}

		try{
			pm.makePersistent(theHouse);
		}finally{
			pm.close();
		}

		resp.sendRedirect("/whoowesme.jsp");
	}
}