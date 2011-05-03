package be.ugent.psb.moduleviewer.parsers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionAnnotation;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;

/**
 * Handles the XML that describes a {@link ConditionAnnotation} tree. Invoked by
 * the {@link ConditionTreeParser}
 * 
 * @author thpar
 * 
 */
public class TreeXMLHandler extends DefaultHandler {
	/**
	 * The XML tags we're expecting. Enums are in all capitals, but will match
	 * case insensitive.
	 * 
	 * @author thpar
	 * 
	 */
	enum XMLTag {
		MODULENETWORK, MODULES, MODULE, REGRESSIONTREE, CHILD, NA;

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

	private ConditionNode rootNode;

	private ConditionNode node;

	private List<Condition> conditionList;

	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet
	 *            the ModuleNetwork to fill
	 * @param progressListener
	 */
	public TreeXMLHandler(ModuleNetwork modnet,
			ProgressListener progListener) {
		this.modnet = modnet;
		conditionList = modnet.getConditionList();
		this.progListener = this.progListener;
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Started reading");

		
		// even init when no parameters will be read,
		// to conform to the DOM method

		// modnet.parameters = new HashMap<String, String>();

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
		case MODULES:
			break;
		case MODULE:
			int modId = Integer.parseInt(attributes.getValue("id"));
			String modName = attributes.getValue("name");
			this.mod = new Module(modnet, modId, modName);
			break;
		case REGRESSIONTREE:
			treePath = new Stack<Dir>();
			treePath.push(Dir.ROOT);
			rootNode = null;
			break;
		case CHILD:
			// child nodes have been created on parent entry
			// just point to the correct one now

			if (rootNode == null) {
				// rootNode = new TreeNode(mod, modnet.normalGammaPrior);
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
				}
			}

			// read atts and init node
			int numChildren = Integer.parseInt(attributes.getValue("numChildren"));
//			String name = attributes.getValue("Name");
			switch (numChildren) {
			case 0:
				node.setLeaf(true);
				parseTreeNodeAtts(attributes);
				break;
			case 2:
				node.setLeaf(false);
				// internal node has 2 children
				node.setLeft(new ConditionNode(node));
				node.setRight(new ConditionNode(node));
				// node.testSplits = new ArrayList<Split>();
				// node.testSplitsRandom = new ArrayList<Split>();
				treePath.push(Dir.LEFT);
				break;
			default:
				throw new SAXException("Impossible to have " + numChildren
						+ " child nodes.");
			}

			break;
		case NA:
			throw new SAXException("Unknown tag: " + qName);
		default:
			notCaught.add(el);
		}
	}

	private void parseTreeNodeAtts(Attributes attributes) {
//		node.leafDistribution.score = Double.parseDouble(attributes.getValue("score"));
		String conds = attributes.getValue("type");
		List<Condition> condList = new ArrayList<Condition>();
		StringTokenizer tokens = new StringTokenizer(conds,";");
		while(tokens.hasMoreTokens()){
			int condNumber = Integer.parseInt(tokens.nextElement().toString());
			condList.add(conditionList.get(condNumber));
//			condList.add(Integer.parseInt(tokens.nextElement().toString()));
		}
//		node.leafDistribution.condSet = condList;
		node.setConditions(condList);
		// compute statistics and score as if node was leaf
//		node.statistics();
//		node.leafDistribution.bayesianScore();	
	}

	
	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		XMLTag el = XMLTag.getValue(qName);
		//the closing tag we're reading should be the one on top of the stack. 
		if (el != opened.peek()) throw new SAXException("Incorrectly nested tags.");
		switch(el){
		case MODULENETWORK:
			break;
		case MODULES:
			progListener.setMyProgress(80);
//			modnet.moduleSets.add(modset);
//			modnet.moduleSet = modset;
//			modnet.setModules(modset);
			System.out.println("Done reading Modules");
			break;
		case MODULE:
			modnet.addModule(mod);
			break;
		case REGRESSIONTREE:
			parseRootNodeEnd();
			break;
		case CHILD:
			//go back up in the tree.
			//if we completed a left child: off to the right
			//if we had a right child: branch completed
			Dir dir = treePath.pop();
//			node = node.parentNode;
			node = node.getParent();
			if (dir == Dir.LEFT){
				treePath.push(Dir.RIGHT);
			}			
			break;
		case NA:
			throw new SAXException("Unknown tag: "+qName);
		default:
//			System.err.println("Tag end not caught: "+el);
		}
	
		opened.pop();
	}

	private void parseRootNodeEnd() {
		this.mod.setRootNode(rootNode);
	}

	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//accumulate the read characters until a new element is found
		elementContent.append(new String(ch, start, length));
	}

	
	
}
