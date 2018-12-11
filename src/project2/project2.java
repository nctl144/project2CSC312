// Chau Tung Lam Nguyen and Sam Coveney
package project2;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import project2.servlet.Words;
import project2.servlet.Chars;
import project2.servlet.Contest;
import project2.servlet.Solution;


public class project2 {
	public static int port = 8080;

	public static void main(String[] args)
    		  throws LifecycleException, InterruptedException, ServletException {

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);


	    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());
	    
	    // add the contest servlet
	    Tomcat.addServlet(ctx, "contest", new Contest());    
		ctx.addServletMapping("/newcontest", "contest");
		
		// add the list-of-words servlet
	    Tomcat.addServlet(ctx, "words", new Words());
		ctx.addServletMapping("/words", "words");
		
		// add the return-the-char servlet
		Tomcat.addServlet(ctx, "charreq", new Chars());
		ctx.addServletMapping("/wordfinder", "charreq");
		
		// add the solution servlet
		Tomcat.addServlet(ctx, "solution", new Solution());
		ctx.addServletMapping("/solution", "solution");

	    tomcat.start();
	    tomcat.getServer().await();

	}
}
