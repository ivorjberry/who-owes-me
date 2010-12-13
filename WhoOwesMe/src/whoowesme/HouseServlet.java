package whoowesme;

import javax.jdo.PersistenceManager;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import java.io.*;
import java.util.Date;
import whoowesme.Bill;

public class HouseServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		
		String houseName = req.getParameter("houseName");
		//int numPeople = Integer.parseInt(req.getParameter("numPeople"));
		
		//Store House content
		House thisHouse = new House(houseName);
		thisHouse.addBill(user.getNickname(), user.getNickname(), 0.0, "init");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(thisHouse);
		}finally{
			pm.close();
		}

		resp.sendRedirect("/whoowesme.jsp");
	}
}