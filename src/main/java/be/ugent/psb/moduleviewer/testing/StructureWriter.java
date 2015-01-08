package be.ugent.psb.moduleviewer.testing;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StructureWriter extends DefaultHandler {
	
	
	/**
	 * Keep for every level the found elements
	 */
	private Stack<Set<String>> foundStack = new Stack<Set<String>>();
	
	/**
	 * elements that are still
	 */
	private Stack<String> openStack = new Stack<String>();
	
	
	@Override
	public void startDocument() throws SAXException {
		foundStack.push(new HashSet<String>());
	}


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		//go a stack level higher
		
		//add a new element to our Collection if we didn't encounter it yet on this level 
		if (!foundStack.peek().contains(qName)){
			foundStack.peek().add(qName);
			foundStack.push(new HashSet<String>());
			System.out.println(getMargin(openStack) +"<"+qName+">");
			openStack.push(qName);
	
		}		
		
		//check if the element has attributes and add them to the Collection
//		for (int i=0; i<attributes.getLength(); i++){
//			elements.get(qName).add(attributes.getQName(i));
//		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		if (openStack.peek().equals(qName)){
			openStack.pop();
			System.out.println(getMargin(openStack) +"</"+qName+">");
			foundStack.pop();

		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

	}
	
	private String getMargin(Stack<String> stack){
		StringBuffer margin = new StringBuffer();
		for (int i = 0;i<stack.size();i++){
			margin.append("|");
		}
		return new String(margin);
	}
	

}
