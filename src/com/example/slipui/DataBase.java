package com.example.slipui;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DataBase {
	final private int SIZE=100;
	final private int END=SIZE-1;
	
	private static int num;
	private static boolean empty;
	private static boolean full;
	
	private static Pad pad[];
	
	
	DataBase(){
		pad = new Pad[SIZE];
		num=0;
		empty=false;
		full=false;
		for (int i=0; i<SIZE ; i++){
			pad[i] = new Pad();
//			pad[i].setData("this is what I want to say"+i);
		}	
		getXML();
	}
	public String peek(){
		String output="full";
		
		if(num<SIZE){
		output = pad[num+1].getData();
		}else{

			full=true;

		}
		
		return output;
	}
			
	public String current(){
		String output="full";
		
		if(num<SIZE){
		output = pad[num].getData();

		
		}else{

			full=true;

		}
		if (num==0){
			output="try again";
		}
		return output;
	}
	
	public String next(){
		String output="full";
		
		if(num<SIZE){
			num++;
		output = pad[num].getData();
		
		
		}else{

			full=true;

		}
		
		return output;
		
	}
	
	public String last(){
		String output="empty";
		
		if(num>0){
			num--;
			output = pad[num].getData();
			
		}else{
			
			empty=true;
		}
		return output;
		
	}
	
	
	public void like(){
		if(!empty&&!full){
			pad[num].likeNow();
		}
	}
	
	public int getLike(){
		if(!empty&&!full){
			return pad[num].getLike();
		}
		return 0;
	}
	
	

	private void getXML() {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			InputStream is = new FileInputStream("/NewFile.xml");
			
			Document document = builder.parse(is);
			String str = document.getElementsByTagName("Content").item(2).getFirstChild().getNodeValue();
			pad[3].setData(str);
			document.normalize();
			
			
			
			Element root = document.getDocumentElement();
			
			NodeList list = root.getChildNodes();
			
			
				for(int i=0; i<list.getLength();i++){
					System.out.println(str);
					if(str!=null){
							pad[i].setData(str);}
					else {
						pad[i].setData("fuck");
					}
					
				}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}




class Pad {
	
	private int latitude;
	private int longitude;
	private int like;
	private int report;
	private boolean should_delete;
	private String data;



	
	Pad(){
		like=0;
		report=0;
		should_delete=false;
		data="";
		this.getLocation();
	}
	
	private void getLocation(){
		latitude=0;
		longitude=0;
	}
	
	public void likeNow(){
		like++;
	}
	
	public void reportNow(){
		report++;
		if(report>99){
			should_delete=true;
		}
	}
	
	public boolean setData(String input){
		if (input.isEmpty()){
			return false;
		}
		if (input.length()>200){
			return false;
		}
		data=input;
		return true;
	}
	
	public String getData(){
		
		return data; 
	}
	
	public int getLike(){
		return like;
	}
	

	
	
	
	
}






