package Practice;

import java.util.ArrayList;
import java.util.Iterator;

public class Iterator_Prac {
	public static void main(String[] args){
		Iterator_Prac practice = new Iterator_Prac();
		
		practice.prac();
	}
	
	
	
	private void prac(){
		
		ArrayList<String> count = new ArrayList<String>();
		
		count.add("one");
		count.add("two");
		count.add("three");
		
		Iterator<String> ite = count.iterator();
		
		while(ite.hasNext()){
			System.out.println(ite.next());
		}
		
		
	}
}
