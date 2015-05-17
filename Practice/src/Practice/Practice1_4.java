package Practice;

public class Practice1_4 {

	public static void main(String[] args){
		String Input="Mr John Smith  ";
		int Length=13;
		Practice1_4 P1_4 = new Practice1_4();
		char[] ch_input=new char[100];
		ch_input=Input.toCharArray();
		ch_input[99]=0;
		P1_4.Replace_Space2(ch_input,Length);
		
	}
	private void Replace_Space(String S_input, int S_length){
		char[] ch_output = new char[S_length*3];
		char[] ch_input=S_input.toCharArray();
		int lastposition=0;
		ch_output[S_length*3]='\0';
		for (int i=0; i<S_length; i++){
			if (ch_input[i]==' '){
				ch_output[lastposition]='%';
				ch_output[lastposition+1]='2';
				ch_output[lastposition+2]='0';
				lastposition+=3;
			}
			ch_output[lastposition]=ch_input[i];
			lastposition++;
			
		}
		String output=new String(ch_output);
		System.out.print(output);
	//	return output;
		
	}
	private void Replace_Space2(char[] ch_input, int S_length){
		
		int spacecount=0,newlength,i;
		for(i=0;i<S_length;i++){
			if(ch_input[i]==' '){
				spacecount++;
			}
		}
		newlength=S_length+spacecount*2;
		ch_input[newlength]='\0';       //mark the end
		for(i=S_length-1;i>=0;i--){
			if(ch_input[i]==' '){
				ch_input[newlength-1]='0';
				ch_input[newlength-2]='2';
				ch_input[newlength-3]='%';
				newlength-=3;
			}
			ch_input[newlength-1]=ch_input[i];
			newlength--;
//			Directory.Exists("d");
		}
		System.out.print(ch_input);
	
	}
	
}
