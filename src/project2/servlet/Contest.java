package project2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import project2.game;


public class Contest extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        resp.setStatus( HttpServletResponse.SC_OK);
       
        //ArrayList<Integer> usedIDs = new ArrayList<Integer>();
        HashMap<Integer, Integer> usedIDs = new HashMap<>();

        int randomID = generateID();
        
        // if random id is in the hashmap -> create a new one
        while (usedIDs.containsValue(randomID)) {
            randomID = generateID();
        }
        
        // when it reaches here -> the id is unique
        usedIDs.put(randomID, 1);
        
        ServletOutputStream out = resp.getOutputStream();

        out.write(("Your contest id is " + Integer.toString(randomID)).getBytes());
        
        // create a new contest in the singleton
        game.newContest(randomID);
        

        out.flush();
        out.close(); 
        
    }
   
    public int generateID() {
        Random r = new Random();
        return r.nextInt(1000 - 1) + 1;
    }

}

