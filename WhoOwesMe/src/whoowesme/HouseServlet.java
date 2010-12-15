package whoowesme;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import java.io.*;
import java.util.Date;
import java.util.List;

import whoowesme.Bill;

public class HouseServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String houseName = req.getParameter("housename");
		String userName = user.getNickname();
		
		//query database, check if name is taken
		//if is taken, put a (i) after it
		//int numPeople = Integer.parseInt(req.getParameter("numPeople"));
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String house = "select from whoowesme.House";
		
		Transaction tx = pm.currentTransaction();
		try{
		
			tx.begin();
			List<House> houseList = (List<House>)pm.newQuery(house).execute();
			
			int i = 1;
			while(true){
				boolean taken = false;
				for(House h:houseList)
				{
					if(h.getHouseName().equalsIgnoreCase(houseName))
					{
						taken = true;
					}
				}
				if(taken == false)
				{
					break;
				}
				else
				{
					houseName = houseName + "(" + i + ")";
					i++;
				}
			}
			
			//Store House content
			House thisHouse = new House(userName, houseName);
		
		
			pm.makePersistent(thisHouse);
			tx.commit();
		}finally{
			if (tx.isActive()) {
                tx.rollback();
            }
			pm.close();
		}

		resp.sendRedirect("/whoowesme.jsp");
	}
}