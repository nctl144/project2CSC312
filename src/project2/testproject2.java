package project2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import project2.project2;
import project2.servlet.Chars;
import project2.servlet.Contest;
import project2.servlet.Words;


public class testproject2 {
	
	@Test
	public void testGetWordList() {

		ArrayList<String> words = getChar("http://localhost:8080/words");
		String[] actualWords = {"zap", "zep", "zip", "zag", "zig"};
		
		for (int i = 0; i < words.size(); i++) {
			assertEquals(actualWords[i], words.get(i));
		}
	}
	
	@Test
	public void testNewContest() {
		ArrayList<String> contestNum = getChar("http://localhost:8080/newcontest");
		String listString = String.join("", contestNum);
		
		// this means a random contest has been generated
		assertEquals("Yourcontestidis", listString);
	}
	
	@Test
	public void testGetLetters() {

		String[] grid1 = {
			"z", "e", "s", "q", "w",
			"a", "e", "s", "q", "w",
			"p", "e", "s", "q", "w",
			"a", "e", "s", "q", "w",
			"a", "e", "s", "q", "w"
		}; 

		String[] positionInOrder = {
				"A1", "B1", "C1", "D1", "E1",
				"A2", "B2", "C2", "D2", "E2",
				"A3", "B3", "C3", "D3", "E3",
				"A4", "B4", "C4", "D4", "E4",
				"A5", "B5", "C5", "D5", "E5",
		};
		
		for (int i = 0; i < positionInOrder.length; i ++) {
			String currPos = positionInOrder[i];
			String url = "http://localhost:8080/contest=1&game=1&pos=" + currPos;

			String currChar = getChar(url).get(0);
			assertEquals(grid1[i], currChar);
		}
	}
	
	public ArrayList<String> getChar(String url) {
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