package project2;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

import org.junit.Test;

import project2.project2;


public class testproject2 {
	
	@Test
	public void testGetWordList() {
		
		project2 project1Instance = new project2();

		ArrayList<String> words = getChar("http://localhost:8080/words");
		String[] actualWords = {"zap", "zep", "zip", "zag", "zig"};
		
		for (int i = 0; i < words.size(); i++) {
			assertEquals(actualWords[i], words.get(i));
		}
	}
	
//	@Test
//	public void testGetWordList() {
//		
//		project2 project1Instance = new project2();
//
//		ArrayList<String> words = getChar("http://localhost:8080/words");
//		String[] actualWords = {"zap", "zep", "zip", "zag", "zig"};
//		
//		for (int i = 0; i < words.size(); i++) {
//			assertEquals(actualWords[i], words.get(i));
//		}
//	}
	
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