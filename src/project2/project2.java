package project2;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import project2.servlet.Words;

public class project2 {
	
	public static void main(String[] args)
    		  throws LifecycleException, InterruptedException, ServletException {

		Tomcat tomcat = new Tomcat();
	    tomcat.setPort(8888);
	

	    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());
	
	    Tomcat.addServlet(ctx, "words", new Words() );
		    

	    ctx.addServletMapping("/words", "words");
	    tomcat.start();
	    tomcat.getServer().await();
	}
	
	public static boolean isValidRequest(int game, String column, int row) {
		ArrayList<String> columnLetters = new ArrayList<String>();
		
		if (game < 1 || game > 3 || !columnLetters.contains(column) 
			|| row < 1 || row > 5) {
			return false;
		}
			
		return true;
	}
}
