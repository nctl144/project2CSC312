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

	public static boolean isValidRequest(int game, String column, int row) {
		ArrayList<String> columnLetters = new ArrayList<String>();

		if (game < 1 || game > 3 || !columnLetters.contains(column)
			|| row < 1 || row > 5) {
			return false;
		}

		return true;
	}

	public static String charAtLocation(HashMap<String, String[][]> map,
			int gameNumber, String col, int colNumber) {
		String[][] currGrid = map.get(Integer.toString(gameNumber));

		HashMap<String, Integer> colMap = new HashMap<String, Integer>();

		colMap.put("A", 0);
		colMap.put("B", 1);
		colMap.put("C", 2);
		colMap.put("D", 3);
		colMap.put("E", 4);

		int colIndex = colMap.get(col);

		return currGrid[colIndex][colNumber];
	}

	/*
	 * This will create the path for the servlet mapping
	 * contest=&game=<1 to 3>&pos=<A1 to E5>
	 * http://localhost:8080/wordfinder?contest=&game=<1 to 3>&pos=
	 */
	public static String createUrl(int contestNumber, int gameNumber,
			String pos) {
		String servletPath = new String();


		String path = "/";
		String query = "contest=" + Integer.toString(contestNumber) + "&game=" +
						Integer.toString(gameNumber) + "&pos=" + pos;


		servletPath += path + query;
		return servletPath;

	}
	
	public static ArrayList<String> getChar(String url) {
		ArrayList<String> result = new ArrayList<String>();
		
		try {
			
			URL current_url = new URL(url);
			HttpURLConnection connected = (HttpURLConnection) current_url.openConnection(); 
			int response = connected.getResponseCode(); 
			InputStream s = connected.getInputStream(); 
			int i;
			char c;
			
			String word = new String();
			
			while((i = s.read()) != -1 && response == 200) {
			    // converts integer to character
			    c = (char)i;
			    if (c == '\n') {
			    	result.add(word);
			    	word = "";
			    } else if (Character.isAlphabetic(c)) {
			    	word += Character.toString(c);
			    }
			}
			
			// add the last word
			if (!word.isEmpty()) {
				result.add(word);
			}
			
		} catch (IOException e) {
			ArrayList<String> error = new ArrayList<String>();
			error.add("The code returned error");
			return error;
		}
		
		// return empty ArrayList if we are unsuccessful 
		return result; 
	}
}
