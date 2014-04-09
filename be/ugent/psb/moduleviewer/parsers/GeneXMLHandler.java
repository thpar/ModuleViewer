package be.ugent.psb.moduleviewer.parsers;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
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
 * @author thpar
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
	 * @author thpar
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
	 * @author thpar
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
	


	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet
	 *            the ModuleNetwork to fill
	 * @param progressListener
	 */
	public GeneXMLHandler(ModuleNetwork modnet,
			ProgressListener progListener, GeneType geneType) {
		this.modnet = modnet;
		this.progListener = progListener;
		this.geneType = geneType;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Started reading");


	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Done reading");
		System.out.println("Tags not parsed:");
		for (XMLTag tag : notCaught) {
			System.out.println(tag);
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
			int modId = Integer.parseInt(attributes.getValue("id"));
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
						e.printStackTrace();
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

	private void parseGeneAttributes(Attributes attributes) {
		String name = attributes.getValue("name");
		String alias = attributes.getValue("alias");
		try {
			Gene addingGene = modnet.getGene(name);
			if (alias!=null && !alias.isEmpty()){
				modnet.setGeneAlias(addingGene, alias);
			}
			node.addGene(addingGene);
		} catch (UnknownItemException e) {
			e.printStackTrace();
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
			System.out.println("Done reading Modules");
			break;
		case MODULE:
			modnet.addModule(mod);
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
//			System.err.println("Tag end not caught: "+el);
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
