package whoowesme;

import com.google.appengine.api.users.*;
import com.google.appengine.api.datastore.*;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Grocery {
	@PrimaryKey
	@Persistent(valueStrategy= IdGeneratorStrategy.IDENTITY)
	Key key;
	
	@Persistent
	User author;
	
	@Persistent
	String msg;
	
	@Persistent
	Date date;
	public Grocery(User a, String m, Date d){
		author = a;
		msg= m;
		date = d;
	}
	public Key getKey(){ return key; }
	public User getAuthor(){ return author; }
	public String getMsg(){ return msg; }
	public Date getDate() { return date; }
	public void setAuthor(User a){ author = a;}
	public void setMsg(String m){ msg= m;}
	public void setDate(Date d){ date = d;}
}