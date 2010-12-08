package whoowesme;

import javax.jdo.PersistenceManager;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.appengine.api.users.*;
import java.io.*;
import java.util.Date;

public class GroceryServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String content = req.getParameter("msg");
		Date date = new Date();
		// Store the grocery entry 
		Grocery g = new Grocery(user, content, new Date());
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			pm.makePersistent(g);
		}finally{
			pm.close();
		}
		
		resp.sendRedirect("/whoowesme.jsp");
	}
}