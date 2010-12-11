package whoowesme;

import javax.jdo.PersistenceManager;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import java.io.*;
import java.util.Date;

public class AddBillServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		
		int amount = Integer.parseInt(req.getParameter("amount"));
		String Owes = req.getParameter("owes");
		String whatFor = req.getParameter("itemName");
		Date date = new Date();
		
		//Need to find what house we are looking for;
		for(House h:)
		
		//Need to get Owes as a user or change Bill to string instead of user
		Bill b = new Bill(user, Owes, amount);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(b);
		}finally{
			pm.close();
		}

		resp.sendRedirect("/whoowesme.jsp");
	}
}