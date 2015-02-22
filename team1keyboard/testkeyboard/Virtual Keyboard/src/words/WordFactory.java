package words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dictionary.Dictionary;

public class WordFactory {
	private static HashMap<String,Word> words = new HashMap<String,Word>();
	public static Word createWord(ArrayList<String> word) throws IOException{
		String s = setString(word);
		s= s.toLowerCase();
		Dictionary.getInstance().isSetup();
		if(!words.containsKey(s)){
			Word w = new Word(word);
			words.put(w.toString(),w);
			return words.get(w.toString());
		}else{
			if(words.containsKey(s)){
				return words.get(s);
			}
			return null;
		}
	}
	public static Word createWord(String w) throws IOException{;
		w= w.toLowerCase();
		Dictionary.getInstance().isSetup();
		if(!words.containsKey(w)){
			Word wrd = new Word(w);
			words.put(wrd.toString(),wrd);
			return words.get(wrd.toString());
		}else{
			if(words.containsKey(w)){
				return words.get(w);
			}
			return null;
		}
	}
	private static String setString(ArrayList<String> str) {
		// TODO Auto-generated method stub
		if(str.isEmpty() || str == null){
			throw new IllegalArgumentException("The word array is empty or null");
		}
		String word = "";
		for(int i = 0; i < str.size(); i++){
			word += str.get(i);
		}
		return word;
	}
}
