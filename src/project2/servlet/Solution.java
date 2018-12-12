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
        
        solutions.put("1", "zap");
        solutions.put("2", "zep");
        solutions.put("3", "zip");
        
        // http://localhost:8080//solution?contest=&game=<1 to 3>&solution=
        String gameNum = req.getParameter("game");
		String solutionSubmitted = req.getParameter("solution");
		String contestNum = req.getParameter("contest");
		String solution = solutions.get(gameNum);
		
		if (!game.ifInGame(Integer.parseInt(contestNum))) {
			out.write("Your contest is expired, please create a new one".getBytes());
		}
		
		if (solutionSubmitted.equals(solution)) {
			out.write("The solution is correct!".getBytes());
			// add this solution to the topscore
			game.addTopScore(Integer.parseInt(contestNum), game.timeElapsed());
		} else {
			out.write("The solution is incorrect!".getBytes());
		}

        out.flush();
        out.close();
    }
}