package Practice;

import java.util.EmptyStackException;

public class Practice3_1 {
	final static int First_Stack=0;
	final static int Second_Stack=1;
	final static int Third_Stack=2;
	
	public static void main(String[] args){
		Practice3_1 P3_1 = new Practice3_1();
		Stack3 Stack = new Stack3(100);
		int[] Num = new int[100];
		for(int i=0; i<100; i++){
			Num[i]=i+1;
			Stack.push(Num[i],First_Stack);
		}
//		System.out.print(Stack.pop(First_Stack));
		for(int i=0; i<50; i++){
			Stack.pop(First_Stack);
		}
		System.out.print(Stack.peek(First_Stack));
		for(int i=0; i<100; i++){
			Num[i]=i+2;
			Stack.push(Num[i],Second_Stack);
		}
//		System.out.print(Stack.pop(First_Stack));
		for(int i=0; i<50; i++){
			Stack.pop(Second_Stack);
		}
		System.out.print(Stack.peek(Second_Stack));

		System.out.print(Stack.peek(Third_Stack));
	}
	
}

class Stack3{
	int num;
	int[] Arr;
	
	Stack3(int Size){
		Arr=new int[(Size+1)*3];
		
		Arr[0]=0;
		Arr[1]=1;
		Arr[2]=2;
	
	}
	public int pop(int num){
		if(Arr[num]!=num){
			int output=Arr[Arr[num]];
			Arr[num]-=3;
			return output;
		}
		throw new EmptyStackException();	
	}
	public void push(int data, int num){
		
		Arr[num]+=3;
		Arr[Arr[num]]=data;	
		}
	public int peek(int num){
		if(Arr[num]!=num){
		return Arr[Arr[num]];
		}
		throw new EmptyStackException();	
	}
		
}