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


public class Contest extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setStatus( HttpServletResponse.SC_OK);
       
        //ArrayList<Integer> usedIDs = new ArrayList<Integer>();
        HashMap<Integer, Integer> usedIDs = new HashMap<>();
       
        int index = 0;
        int currentID;
        while (true) {
            int contestID = generateID();
            if (!usedIDs.containsValue(contestID)) {
                usedIDs.put(index++, contestID);
                currentID = contestID;
                break;
            }
        }
        
        //resp.sendRedirect("http://");
       
        /* for the timer
        int delay = 120 * 1000; //120 seconds * 1000 for miliseconds
        Timer timer = new Timer();
        timer.schedule(delay);//figure this out rn
       */
        
        
        ServletOutputStream out = resp.getOutputStream();
        out.write(currentID);
        try {
        Thread.sleep(120 * 1000);
        } throws InterruptException(e);
        out.flush();
        out.close(); 
        
    }
   
    public int generateID() {
        Random r = new Random();
        return r.nextInt(1000-1) + 1;
    }

}

