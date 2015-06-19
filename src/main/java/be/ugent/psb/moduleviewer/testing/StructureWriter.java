package be.ugent.psb.moduleviewer.testing;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
