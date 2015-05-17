package Practice;

import java.util.HashMap;
import java.util.Map;

public class Hash_Prac {
	
	public static void main (String[] args){
		Hash_Prac prac = new Hash_Prac();
		prac.practice();
	}
	
	private void practice(){
		
		int[] input = {1,2,6,8,9,3,7};
		
		Map <Integer,Integer> hash_map = new HashMap<Integer,Integer>();
		
		for (int i=0; i<input.length; i++){
			hash_map.put( input[i], i );
		}
		
 		System.out.println(hash_map.get(6));
 		
 		
 		
 		Map <Integer,String> map = new HashMap<>();
 		map.put(3, "abc");
 		map.put(3, "bcd");
 		System.out.println(map.get(3));
		
	}
}
