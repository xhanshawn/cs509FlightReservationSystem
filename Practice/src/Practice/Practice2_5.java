package Practice;

import java.util.LinkedList;



public class Practice2_5 {
	public static void main(String[] args){
		Practice2_5 P2_5 = new Practice2_5();
		P2_5.Num();
		
	}
	public void Num(){

		LinkedListNode m,head,next;
		m=new LinkedListNode(0) ;
		int output1=10000000;
		m.data=output1%10;
		head=m;
		output1=output1/10;
//		while(output1>0){
//			next=new LinkedListNode(0);
//			next.data=output1%10;
//			m.next=next;
//			m=next;
//			output1=output1/10;	
//			System.out.println(output1);
//		}
		while(output1>0){
			head.appendToTail(output1%10);
			output1=output1/10;
		}
//		m.next=null;
		head.Print();
		
		
	}
	
}

class LinkedListNode {
	LinkedListNode next = null;
	int data;
	public LinkedListNode(int d){
		data=d;
	}
	void appendToTail(int d){
		LinkedListNode end = new LinkedListNode(d);
		LinkedListNode n = this;
		while(n.next!=null){
			n=n.next;
		}
		n.next = end;	
	}
	public void Print(){
		LinkedListNode n;
		n=this;
		while(n.next!=null){
			System.out.printf("%d->",n.data);
			n=n.next;
		}
		System.out.print(n.data);
	}
	
}