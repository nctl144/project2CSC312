package project2;

import java.io.File;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import project2.servlet.Words;
import project2.servlet.Contest;

public class project2 {
	public static int port = 8080;
	
	public static void main(String[] args)
    		  throws LifecycleException, InterruptedException, ServletException {
		
		HashMap<String, String[][]> grids = new HashMap<String, String[][]>();
		
		String[][] grid1 = {
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"}
		};
		
		String[][] grid2 = {
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"}	
		};
		
		String[][] grid3 = {
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"},
			{"a", "e", "s", "q", "w"}	
		};

		grids.put("1", grid1);
		grids.put("2", grid2);
		grids.put("3", grid3);
		

		System.out.println(createUrl(1, 2, "A2"));
		

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
//	    tomcat.setPort(Integer.parseInt(port));
	

	    Context ctx = tomcat.addContext("/", new File(".").getAbsolutePath());
	
	    Tomcat.addServlet(ctx, "words", new Words() );
		Tomcat.addServlet(ctx, "contest", new Contest() );    

	    ctx.addServletMapping("/words", "words");
	    ctx.addServletMapping("/contest", "contest");
	    
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
		
		String scheme = "http";
		String netloc = "localhost";
		String path = "/wordfinder";
		String query = "contest=" + Integer.toString(contestNumber) + "&game=" + 
						Integer.toString(gameNumber) + "&pos=" + pos;
		String auth = null;
		String fragment = null;
		
		try {
			URI uri = new URI(scheme, auth, netloc, port, path, query, fragment);
			
			URL url = uri.toURL();
			return url.toString();
		} catch (Exception e) {
			return "";
		}
		
	}
}
