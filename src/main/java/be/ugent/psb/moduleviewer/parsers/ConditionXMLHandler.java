package be.ugent.psb.moduleviewer.parsers;

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

import be.ugent.psb.moduleviewer.Logger;
import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;

/**
 * Handles the XML that describes a {@link ConditionAnnotation} tree. Invoked by
 * the {@link TreeParser}
 * 
 * @author Thomas Van Parys
 * 
 */
public class ConditionXMLHandler extends DefaultHandler {
	/**
	 * The XML tags we're expecting. Enums are in all capitals, but will match
	 * case insensitive.
	 * 
	 * @author Thomas Van Parys
	 * 
	 */
	enum XMLTag {
		MODULENETWORK, MODULE, CONDITIONTREE, CHILD, CONDITION, NONTREECONDITIONS, NA;

		public static XMLTag getValue(String value) {
			try {
				return valueOf(value.toUpperCase());
			} catch (Exception e) {
				return NA;
			}
		}

	}

	/**
	 * Just keeping a list of known tags that are found in the XML file but
	 * aren't caught yet.
	 */
	Set<XMLTag> notCaught = new HashSet<XMLTag>();

	/**
	 * Did we go left or right? (or just started at the root)
	 * 
	 * @author Thomas Van Parys
	 * 
	 */
	enum Dir {
		ROOT, LEFT, RIGHT;
	}

	/**
	 * Keep track of the XML elements we're in
	 */
	private Stack<XMLTag> opened = new Stack<XMLTag>();

	/**
	 * Keep content of current XML element.
	 */
	private StringBuffer elementContent;

	/**
	 * Keep track of the path in the TreeNodes
	 */
	Stack<Dir> treePath = new Stack<Dir>();

	private ModuleNetwork modnet;
	private ProgressListener progListener;

	private Module mod;

	private ConditionNode rootNode;

	private ConditionNode node;

	private Logger logger;


	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet
	 *            the ModuleNetwork to fill
	 * @param progressListener
	 */
	public ConditionXMLHandler(ModuleNetwork modnet,
			ProgressListener progListener, Logger logger) {
		this.modnet = modnet;
		this.progListener = progListener;
		this.logger = logger;
	}

	@Override
	public void startDocument() throws SAXException {		
		
	}

	@Override
	public void endDocument() throws SAXException {
		for (XMLTag tag : notCaught) {
			logger.addEntry("Uncaught XML tag: "+tag);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		XMLTag el = opened.push(XMLTag.getValue(qName));
		elementContent = new StringBuffer();

		switch (el) {
		case MODULENETWORK:
			progListener.setMyProgress(10);
			break;
		case MODULE:
			String modId = new String();
			try {
				modId = attributes.getValue("id");
				String modName = attributes.getValue("name");
				this.mod = modnet.getModule(modId);
				this.mod.setName(modName);
			} catch (UnknownItemException e) {
				//TODO this could actually be skipped/recovered
				throw new SAXException("Referencing unknown module "+modId);
			}
			break;
		case CONDITIONTREE:
			treePath = new Stack<Dir>();
			treePath.push(Dir.ROOT);
			rootNode = null;
			break;
		case NONTREECONDITIONS: break;
		case CHILD:
			// child nodes have been created on parent entry
			// just point to the correct one now

			if (rootNode == null) {
				rootNode = new ConditionNode();
				node = rootNode;
			} else {
				switch (treePath.peek()) {
				case LEFT:
					node = (ConditionNode) node.left();
					break;
				case RIGHT:
					node = (ConditionNode) node.right();
					break;
				case ROOT:
					break;
				}
			}

			// read atts and init node
			String internalNodeString = attributes.getValue("node");
			/**
			 * Is the child node internal? (internal == not a leaf)
			 */
			boolean internalNode =  internalNodeString.equals("internal");
			if (internalNode) {
				node.setLeaf(false);
				// internal node has 2 children
				node.setLeft(new ConditionNode(node));
				node.setRight(new ConditionNode(node));
				treePath.push(Dir.LEFT);
			} else {
				node.setLeaf(true);
			}
			break;
		case CONDITION: 
			parseConditionAttributes(attributes);
			break;
		case NA:
			throw new SAXException("Unknown XML tag: " + qName);
		default:
			notCaught.add(el);
		}
	}

	private void parseConditionAttributes(Attributes attributes) throws SAXException{
		String condId = attributes.getValue("id");
		String condName = attributes.getValue("name");
		try {
			//retrieve parent tag
			XMLTag thisTag = opened.pop();
			XMLTag parentTag = opened.peek();
			opened.push(thisTag);
			switch(parentTag){
			case CHILD:
				Condition addingCondition;
				if (condId != null){
					addingCondition = modnet.getCondition(Integer.parseInt(condId));					
				} else {
					addingCondition = modnet.getCondition(condName);					
				}
				node.addCondition(addingCondition);
				break;
			case NONTREECONDITIONS:
				if (condId != null){
					mod.addNonTreeCondition(Integer.parseInt(condId));					
				} else {
					mod.addNonTreeCondition(condName);					
				}
				break;
			case NA:
			case MODULENETWORK:
			case MODULE:
			case CONDITION:
			case CONDITIONTREE:
			break;
			}
		} catch (NumberFormatException e) {
			throw new SAXException("Corrupt condition ID: "+condId);
		} catch (UnknownItemException e) {
			throw new SAXException("Unknown condition");
		}
		
	}


	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		XMLTag el = XMLTag.getValue(qName);
		//the closing tag we're reading should be the one on top of the stack. 
		if (el != opened.peek()) throw new SAXException("Incorrectly nested tags.");
		switch(el){
		case MODULENETWORK:
			break;
		case MODULE:
			//no need for action
			break;
		case CONDITIONTREE:
			parseRootNodeEnd();
			break;
		case NONTREECONDITIONS: break;
		case CHILD:
			//go back up in the tree.
			//if we completed a left child: off to the right
			//if we had a right child: branch completed
			Dir dir = treePath.pop();
			node = node.getParent();
			if (dir == Dir.LEFT){
				treePath.push(Dir.RIGHT);
			}			
			break;
			
		case CONDITION: break;
		case NA:
			throw new SAXException("Unknown tag: "+qName);
		default:
		}
	
		opened.pop();
	}

	private void parseRootNodeEnd() {
		this.mod.setConditionTree(rootNode);
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//accumulate the read characters until a new element is found
		elementContent.append(new String(ch, start, length));
	}

	
	
}
