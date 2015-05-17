package Practice;

import java.util.ArrayList;

public class ArrayListClass {
	public static void main(String[] args){
		ArrayListClass practice = new ArrayListClass();
		practice.prac();
	}
	private void prac(){
		
		String str = "ASFDGSAGHDSFSDJSJ";
		
		char[] ch = str.toCharArray();
		
		ArrayList arr = new ArrayList();
		for(int i=0; i<str.length(); i++){
			arr.add(ch[i]);
			
		}
		for(int i =0; i<arr.size(); i++){
			System.out.print(" "+arr.get(i));
		}
	}
}
