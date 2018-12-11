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
		buildServletMapping(ctx, tomcat);
		
//		Tomcat.addServlet(ctx, "solution", new Solution());
//		ctx.addServletMapping("/contest=1&game=1&solution", "solution");

	    tomcat.start();
	    tomcat.getServer().await();

	}

	public static void buildServletMapping(Context ctx, Tomcat tomcat) {
		// first, there are 3 games to add
		// second, there are 25 positions to add into the url
		String[] positionInOrder = {
				"A1", "B1", "C1", "D1", "E1",
				"A2", "B2", "C2", "D2", "E2",
				"A3", "B3", "C3", "D3", "E3",
				"A4", "B4", "C4", "D4", "E4",
				"A5", "B5", "C5", "D5", "E5",
		};

		// game number is from 1 -> 3
		for (int i = 1; i < 4; i ++) {
			for (int j = 0; j < positionInOrder.length; j ++) {
				String servletPath = createUrl(1, i, positionInOrder[j]);
				ctx.addServletMapping(servletPath, "charreq");
			}
		}
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
}
