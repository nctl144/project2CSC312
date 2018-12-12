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

import project2.game;


public class Solution extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ServletOutputStream out = resp.getOutputStream();
        
        HashMap<String, String> solutions = new HashMap<String, String>();
        HashMap<String, String> positions = new HashMap<String, String>();
        
        solutions.put("1", "zap");
        solutions.put("2", "zep");
        solutions.put("3", "zip");
        
        positions.put("1", "A1:A3");
        positions.put("2", "A3:C3");
        positions.put("3", "C4:E4");
        
        // http://localhost:8080//solution?contest=&game=<1 to 3>&solution=
        String gameNum = req.getParameter("game");
		String solutionSubmitted = req.getParameter("solution");
		String contestNum = req.getParameter("contest");
		String solution = solutions.get(gameNum);
		String position = positions.get(gameNum);
		
		if (!game.ifInGame(Integer.parseInt(contestNum))) {
			out.write("Your contest is expired, please create a new one".getBytes());
			resp.setStatus(HttpServletResponse.SC_GONE);
			out.flush();
			out.close();
		}
		
		if (solutionSubmitted.equals(solution)) {
			resp.setStatus(HttpServletResponse.SC_OK);
			out.write("The solution is correct!\n".getBytes());
			out.write(("Game: " + gameNum + ", position: " + position + ", solution: " + solution).getBytes());
			out.write(("it took you: " + game.timeElapsed() + " milliseconds and " + Integer.toString(game.getRequestCount()) + " request(s) to solve the problem").getBytes());

			// add this solution to the topscore
			game.addTopScore(Integer.parseInt(contestNum), game.timeElapsed());
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			out.write("The solution is incorrect!".getBytes());
		}

        out.flush();
        out.close();
    }
}