package whoowesme;

import javax.jdo.PersistenceManager;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.List;

public class GroceryEmptyList extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String query = "select from whoowesme.Grocery";
		pm.newQuery(query).deletePersistentAll();
		
		
		resp.sendRedirect("/whoowesme.jsp");
	}
}