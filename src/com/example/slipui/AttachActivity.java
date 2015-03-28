package com.example.slipui;

import java.io.File;












import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AttachActivity extends Activity {
    
	EditText attach;
	Document document;
	Element root;
	private static int num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activiy_attach);
		
		File file=new File("/Users/hanxu/documents/data.xml");
		

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		root = document.createElement("note");
		document.appendChild(root);
		
		
		
		attach = (EditText) findViewById(R.id.edit_text);
		
		
		Button attach_button = (Button) findViewById(R.id.attach_button);
		
		attach_button.setOnClickListener( new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String input="";
				input+=attach.getText();
				num++;
				Element item = document.createElement("item");
				
				Attr content = document.createAttribute("content");
				content.setValue(input);
				
				Attr latt = document.createAttribute("latt");
				content.setValue("00.00");
				
				Attr longti = document.createAttribute("longti");
				content.setValue("00.00");
				
				Attr like = document.createAttribute("like");
				content.setValue("0");
				
				item.setAttributeNode(content);
				item.setAttributeNode(latt);
				item.setAttributeNode(longti);
				item.setAttributeNode(like);
				root.appendChild(item);
				
			}
			
		});
		

		Button build_button = (Button) findViewById(R.id.build);
		
		build_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				
				TransformerFactory tf = TransformerFactory.newInstance();
				try{
					Transformer transformer =  tf.newTransformer();
					DOMSource source =new DOMSource(document);
					transformer.setOutputProperty(OutputKeys.ENCODING,"gb123");
					transformer.setOutputProperty(OutputKeys.INDENT,"yes");
					PrintWriter pw = new PrintWriter(new FileOutputStream("/Users/hanxu/documents/data.xml"));  
		            StreamResult result = new StreamResult(pw);  
		            transformer.transform(source, result);  
				} catch (TransformerConfigurationException e) {  
					e.printStackTrace(); 
	            } catch (IllegalArgumentException e) {  
	            	e.printStackTrace();  
	            }   
	             catch (TransformerException e) {  
	            	 e.printStackTrace();  
	            } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
			}
		});
		
		
		
		
	}
}
