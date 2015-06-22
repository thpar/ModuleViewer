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
import be.ugent.psb.moduleviewer.model.DuplicateModuleIdException;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;

/**
 * Handles the XML that describes a {@link GeneAnnotation} tree. Invoked by
 * the {@link TreeParser}
 * 
 * The genes described in the XML file are grouped by module and organised into a tree structure.
 * They either represent genes or regulators.
 * 
 * @author Thomas Van Parys
 * 
 */
public class GeneXMLHandler extends DefaultHandler {
	
	enum GeneType{
		GENES, REGULATORS;
	}
	
	
	/**
	 * The XML tags we're expecting. Enums are in all capitals, but will match
	 * case insensitive.
	 * 
	 * @author Thomas Van Parys
	 * 
	 */
	enum XMLTag {
		MODULENETWORK, MODULE, GENETREE, REGULATORTREE, CHILD, GENE, NA;

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

	/**
	 * top of the tree for the current module
	 */
	private GeneNode rootNode;
	/**
	 * node we're now working on
	 */
	private GeneNode node;

	/**
	 * Parsing genes or regulators?
	 */
	private GeneType geneType;

	private Logger logger;
	


	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet
	 *            the ModuleNetwork to fill
	 * @param progressListener
	 */
	public GeneXMLHandler(ModuleNetwork modnet,
			ProgressListener progListener, GeneType geneType, Logger logger) {
		this.modnet = modnet;
		this.progListener = progListener;
		this.geneType = geneType;
		this.logger = logger;
	}

	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {
		for (XMLTag tag : notCaught) {
			logger.addEntry("Uncaught tag: "+tag);
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
			String modId = attributes.getValue("id");
			String name = attributes.getValue("name");
				switch(geneType){
				case GENES:
					this.mod = new Module(modnet, modId, name);
					break;
				case REGULATORS:
					try {
						this.mod = modnet.getModule(modId);
						break;
					} catch (UnknownItemException e) {
						throw new SAXException("Unknown module: "+modId);
					}
				}
			break;
		case GENETREE:
		case REGULATORTREE:
			treePath = new Stack<Dir>();
			treePath.push(Dir.ROOT);
			rootNode = null;
			break;
		case CHILD:
			// child nodes have been created on parent entry
			// just point to the correct one now

			if (rootNode == null) {
				rootNode = new GeneNode();
				node = rootNode;
			} else {
				switch (treePath.peek()) {
				case LEFT:
					node = (GeneNode) node.left();
					break;
				case RIGHT:
					node = (GeneNode) node.right();
					break;
				case ROOT:
					break;
				}
			}

			// read atts and init node
			String nodeTypeString = attributes.getValue("node");
			boolean internalNode = nodeTypeString.equals("internal");
			if (internalNode) {
				node.setLeaf(false);
				// internal node has 2 children
				node.setLeft(new GeneNode(node));
				node.setRight(new GeneNode(node));
				
				treePath.push(Dir.LEFT);
			} else {
				node.setLeaf(true);
			}
			
			break;
		case GENE:
			parseGeneAttributes(attributes);
			break;
		case NA:
			throw new SAXException("Unknown tag: " + qName);
		default:
			notCaught.add(el);
		}
	}

	private void parseGeneAttributes(Attributes attributes) throws SAXException{
		String name = attributes.getValue("name");
		String alias = attributes.getValue("alias");
		try {
			Gene addingGene = modnet.getGene(name);
			if (alias!=null && !alias.isEmpty()){
				modnet.setGeneAlias(addingGene, alias);
			}
			node.addGene(addingGene);
		} catch (UnknownItemException e) {
			throw new SAXException("Unknown gene");
		}
		
	}


	
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		XMLTag el = XMLTag.getValue(qName);
		//the closing tag we're reading should be the one on top of the stack. 
		if (el != opened.peek()) throw new SAXException("Incorrectly nested tags.");
		switch(el){
		case MODULENETWORK:
			progListener.setMyProgress(80);
			break;
		case MODULE:
			if (geneType == GeneType.GENES){
				try {
					modnet.addModule(mod);
				} catch (DuplicateModuleIdException e) {
					logger.addEntry(e, "Duplicate module id");
				}				
			}
			break;
		case GENETREE:
		case REGULATORTREE:
			parseRootNodeEnd();
			break;
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
		case GENE: break;
		case NA:
			throw new SAXException("Unknown tag: "+qName);
		default:
		}
	
		opened.pop();
	}

	private void parseRootNodeEnd() {
		switch(geneType){
		case GENES:
			this.mod.setGeneTree(rootNode);			
			break;
		case REGULATORS:
			this.mod.addRegulatorTree(rootNode);			
			break;
		}
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//accumulate the read characters until a new element is found
		elementContent.append(new String(ch, start, length));
	}

	
	
}
