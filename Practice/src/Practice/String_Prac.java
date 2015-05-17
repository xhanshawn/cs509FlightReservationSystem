package Practice;

public class String_Prac {

	public static void main(String[] args){
		
		String_Prac practice = new String_Prac();
		practice.test("2015 May 08 11:18 EDT");
	}
	
	public void test(String input){
		String[] result = input.split(" ");
		
		for (int i=0 ; i<result.length; i++){
			if (i==2){System.out.println(Integer.parseInt(result[i]));}
			System.out.println(result[i]);
			
		}
		String [] time = result[3].split(":");
		System.out.println(time[0]);
		System.out.println(time[1]);
	
		StringBuffer buf = new StringBuffer();
		
		for(int i=0 ; i< result.length; i++){
			
			buf.append(result[i]);
			
		}

		System.out.println(buf.toString());
		System.out.println(buf.toString().substring(0, 1));

	}
	
	
}


