package project2.servlet;

import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import project2.game;

public class Topscore extends HttpServlet {
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ServletOutputStream out = resp.getOutputStream();
        
        // http://localhost:8080//solution?contest=&game=<1 to 3>&solution=
        Map<Integer, Long> highScore = game.getHighScore();
        
        for (Map.Entry<Integer, Long> currGame : highScore.entrySet()) {
        	String contestId = Integer.toString(currGame.getKey());
        	String time = Long.toString(currGame.getValue());
		    out.write(("contest id: " + contestId + ", time in milliseconds: " + time + "\n").getBytes());    
		}
        
        out.flush();
        out.close();
        
    }
}