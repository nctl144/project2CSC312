package project2.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.System.out;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Chars extends HttpServlet {
	
	String[][] grid1 = {
		{"z", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"p", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"}
	};
		
	String[][] grid2 = {
		{"a", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"z", "e", "p", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"}	
	};
	
	String[][] grid3 = {
		{"a", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"a", "e", "s", "q", "w"},
		{"a", "e", "z", "i", "p"},
		{"a", "e", "s", "q", "w"}	
	};
		
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	resp.setStatus( HttpServletResponse.SC_OK);
    	
    	ServletOutputStream output = resp.getOutputStream();
    	HashMap<String, String[][]> grids = new HashMap<String, String[][]>();
		
		grids.put("1", grid1);
		grids.put("2", grid2);
		grids.put("3", grid3);

		String gameNum = req.getParameter("game");
		String position = req.getParameter("pos");
		
		boolean isValidRequest = isBadRequest(gameNum, position);
		if (!isValidRequest) {
			resp.setStatus(HttpServletResponse.SC_GONE);
			output.flush();
	        output.close();
		}
		
		String charRequested = getCharFromGrid(position, grids.get(gameNum));
		output.write(charRequested.getBytes());

        output.flush();
        output.close();
        
    }
    
    public String getCharFromGrid(String pos, String[][] grids) {
    	HashMap<String, Integer> charToIndex = new HashMap<String, Integer>();
    	
    	charToIndex.put("A", 0);
    	charToIndex.put("B", 1);
    	charToIndex.put("C", 2);
    	charToIndex.put("D", 3);
    	charToIndex.put("E", 4);
    	
    	int row = Integer.parseInt(pos.substring(1, 2)) - 1;
    	int col = charToIndex.get(pos.substring(0, 1));

    	return grids[row][col];
    }
    
    public boolean isBadRequest(String gameNumber, String position) {
    	String[] positionInOrder = {
				"A1", "B1", "C1", "D1", "E1",
				"A2", "B2", "C2", "D2", "E2",
				"A3", "B3", "C3", "D3", "E3",
				"A4", "B4", "C4", "D4", "E4",
				"A5", "B5", "C5", "D5", "E5",
		};
    	
    	boolean validPos = false;
    	boolean validGameNum = false;

    	// check if the pos is valid
    	for (int i = 0; i < positionInOrder.length; i ++) {
    		if (positionInOrder[i].equals(position)) {
    			validPos = true;
    		}
    	}
    	
    	if (Integer.parseInt(gameNumber) <= 3 && Integer.parseInt(gameNumber) >= 1) {
    		validGameNum = true;
    	}
  
    	if (!validPos || !validGameNum) {
    		return false;
    	}
    	return true;
    }

}