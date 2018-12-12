package project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class game {

	private static final long DELAY = 120 * 1000;
	
	private static long start;
	private Integer id;
	private static Integer requestCount = 0;
	
	//to prevent anyone from creating it directly
	private game(int id) {
		this.id = id;
		this.start = System.currentTimeMillis();
	}

	public Integer getId(){
		return id;
	}
	
	public static boolean isExpired() {
		return (System.currentTimeMillis() - start ) > DELAY;
	}
	
	public static long timeElapsed() {
		return System.currentTimeMillis() - start;
	}
	
	private static Map<Integer, game> games = Collections.synchronizedMap( new HashMap<Integer, game>() );
	private static Map<Integer, Long> highScore = Collections.synchronizedMap( new HashMap<Integer, Long>() );

	// This code is from lecture 8. However, it has been modified for the use of this project
	static {
		TimerTask task = new TimerTask() {
	        public void run() {
	            ArrayList<game> gamesToBeRemoved = new ArrayList<game>();
	            for ( game game : games.values() ) {
	            	if ( game.isExpired() ) {
	            		System.out.println( "Game id:" + game.getId() + " is expired");
	            		gamesToBeRemoved.add( game );
	            	} else {
	            		System.out.println( "Game id:" + game.getId() + " is NOT expired");
	            	}
	            }
	            
	            for ( game game : gamesToBeRemoved ) {
            		System.out.println( "Game id:" + game.getId() + " is removed !!!! ");
	            	games.remove(  game.getId() );
	            }
	        }
	    };
	    Timer timer = new Timer("Timer");
	     
	    long delay = 1000L;
	    timer.scheduleAtFixedRate(task,
                delay,
                delay ); 
	}
	
	public static void newContest(int contestNum) {
		game game = new game(contestNum);
		
		games.put(game.getId(), game);
	}
	
	public static game getGame( int id ) {
		game game = games.get( id );
		
		if ( game != null ) {
			return game.isExpired() ? null : game;
		}
		return game;
	}
	
	public static boolean ifInGame(int contestNum) {
		if (games.containsKey(contestNum)) {
			return true;
		}
		return false;
	}
	
	public static void addTopScore(int contestNum, long timeElapsed) {
		// add the input to the hash map
		highScore.put(contestNum, timeElapsed);
		
		for (Map.Entry<Integer, Long> currGame : highScore.entrySet()) {
			System.out.println("This is the key");
		    System.out.println(currGame.getKey());
		}
		
		// time to pop the smallest one
		if (highScore.size() > 5) {
			int minContest = 0;
			Long minTime = Collections.min(highScore.values());
			
			for (Map.Entry<Integer, Long> currGame : highScore.entrySet()) {
			    Long currTime = currGame.getValue();
			    if (currTime == minTime) {
			    	minContest = currGame.getKey();
			    }
			}
			
			highScore.remove(minContest);
		}
	}
	
	public static Map<Integer, Long> getHighScore() {
		return highScore;
	}
	
	public static void incrementRequestCount() {
		requestCount++;
	}
	
	public static int getRequestCount() {
		return requestCount;
	}
}
