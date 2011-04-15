package be.ugent.psb.moduleviewer.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import be.ugent.psb.moduleviewer.actions.LoadModulesAction.ProgressListener;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;




/**
 * Event handler for LeMoNe XML.
 * Each encountered tag triggers an event, which is kept on the stack. 
 * The content and attributes of each tag are read and parsed as needed.
 * 
 * @author thpar
 *
 */
public class XMLModuleHandler extends DefaultHandler{
	
	/**
	 * The XML tags we're expecting.
	 * Enums are in all capitals, but will match case insensitive.
	 * 
	 * @author thpar
	 *
	 */
	enum XMLTag{
		MODULENETWORK,
		ALGORITHM, PARAMETER,
		EXPERIMENTS, DATAMATRIX, DATATYPE,
		REGULATORS, REGULATOR, RANDOMREGULATOR,
		NORMALGAMMAPRIOR, NUMMODULESETS,
		MODULESET, NUMMODULES, MODULE,
		GENES, GENE,
        REGULATORWEIGHTS, REGULATIONTREES,
        ROOT, TREENODE,
		NA;
		
		public static XMLTag getValue(String value){
			try{
				return valueOf(value.toUpperCase());
			} catch (Exception e){
				return NA;
			}
		}
		
	}
	
	
	// --- DEVEL ---
	/**
	 * Just keeping a list of known tags that are found in the XML file
	 * but aren't caught yet.
	 */
	Set<XMLTag> notCaught = new HashSet<XMLTag>();
	
	
	// --- ATTS for XML PARSING ---	
	
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
	/**
	 * Did we go left or right? (or just started at the root)
	 * 
	 * @author thpar
	 *
	 */
	enum Dir{
		ROOT, LEFT, RIGHT;
	}
	
	// --- BUILDING THE MODNET --
	/**
	 * The ModuleNetwork we're reading here
	 */
	private ModuleNetwork modnet;

	/**
	 * While reading modules of a set, they are kept here, 
	 * until they are written to the module network. 
	 */
	private ArrayList<Module> modset;

	/**
	 * The module we're building.
	 */
	private Module mod;
	
	/**
	 * Rootnode of current tree
	 */
	private ConditionNode rootNode;

	/**
	 * The node we're building
	 */
	private ConditionNode node;

	/**
	 * Number of Regulation trees found in total
	 */
	private int treeCount = 0;
	
	private ProgressListener progListener;
	
	
	public ProgressListener getProgListener() {
		return progListener;
	}
	public void setProgListener(ProgressListener progListener) {
		this.progListener = progListener;
	}
	
	
	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet the ModuleNetwork to fill
	 * @param progressListener 
	 */
	public XMLModuleHandler(ModuleNetwork modnet) {
		this.modnet = modnet;
	}



	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet the ModuleNetwork to fill
	 * @param progressListener 
	 */
	public XMLModuleHandler(ModuleNetwork modnet, ProgressListener progressListener) {
		this.modnet = modnet;
		this.progListener = progressListener;
	}

	
	
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Started reading");
		
		//even init when no parameters will be read, 
		//to conform to the DOM method
		
//		modnet.parameters = new HashMap<String, String>();
	
	}
	@Override
	public void endDocument() throws SAXException {
		System.out.println("Done reading");
		System.out.println("Tags not parsed:");
		for (XMLTag tag : notCaught){
			System.out.println(tag);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		XMLTag el = opened.push(XMLTag.getValue(qName));
		elementContent = new StringBuffer();

		switch(el){
		case MODULENETWORK:
			progListener.setMyProgress(10);
			break;
		case PARAMETER:
			progListener.setMyProgress(15);
			parseAlgorithmParam(attributes);
			break;
		case EXPERIMENTS:
			progListener.setMyProgress(30);
			System.out.println("Reading experiments");
			break;
		case DATAMATRIX:
			progListener.setMyProgress(30);
			System.out.println("Reading DataMatrix");
			break;
		case DATATYPE:
			System.out.println("Reading DataType");
			parseDataType(attributes);
			break;
		case REGULATORS:
			progListener.setMyProgress(35);
			System.out.println("Reading Regulators");
//			modnet.setRegulators(new ArrayList<Gene>());
			break;
		case REGULATOR:
			if (hasParents(XMLTag.REGULATORS)){
				parseRegulator(attributes);
			} else if (hasParents(XMLTag.REGULATORWEIGHTS)){
				parseRegulatorWeight(attributes);
			} else if (hasParents(XMLTag.TREENODE)){
				parseRegulatorTreeNode(attributes);
			}
			break;
		case RANDOMREGULATOR:
			if (hasParents(XMLTag.TREENODE)){
				parseRandomRegulatorTreeNode(attributes);
			}
			break;
		case NORMALGAMMAPRIOR:
			setNormalGammaPrior(attributes);
			break;
		case NUMMODULESETS:
//			int numberOfModuleSets = Integer.parseInt(attributes.getValue("value"));
//			modnet.moduleSets = new ArrayList<ArrayList<Module>>(numberOfModuleSets);			
			break;
		case MODULESET:
			progListener.setMyProgress(45);
			System.out.println("Reading ModuleSet");
			break;
		case NUMMODULES:
			int numberOfModules= Integer.parseInt(attributes.getValue("value"));
			this.modset = new ArrayList<Module>(numberOfModules);
			break;
		case MODULE:
			int mod_id = Integer.parseInt(attributes.getValue("id"));
//			this.mod = new Module(modnet, mod_id, modnet.normalGammaPrior);
			this.mod = new Module(modnet, mod_id);
			break;
		case GENES:
			//only elements with atts. parsed in GENE-start
			break;
		case GENE:
//			int gene_id = Integer.parseInt(attributes.getValue("id"));
			String geneId = attributes.getValue("name");
//			this.mod.genes.add(modnet.geneSet.get(gene_id));
			this.mod.addGene(geneId);
			break;
		case REGULATORWEIGHTS:
			//only elements with atts. parsed in REGULATOR-start
			break;
		case REGULATIONTREES:
			break;
		case ROOT:
			treePath = new Stack<Dir>();
			treePath.push(Dir.ROOT);
			rootNode = null;
			break;
		case TREENODE:			
			//child nodes have been created on parent entry
			//just point to the correct one now
			
			if (rootNode==null){
//				rootNode = new TreeNode(mod, modnet.normalGammaPrior);
				rootNode = new ConditionNode();
				node = rootNode;
			} else {
				switch(treePath.peek()){
				case LEFT: node = (ConditionNode)node.left();
					break;
				case RIGHT: node = (ConditionNode)node.right();
					break;
				}
			}
			
			//read atts and init node
			parseTreeNodeAtts(attributes);
			int numChildren = Integer.parseInt(attributes.getValue("numChildren"));
			switch(numChildren){
			case 0: node.setLeaf(true);
				break;
			case 2: node.setLeaf(false);
				//internal node has 2 children
				node.setLeft(new ConditionNode(node));
				node.setRight(new ConditionNode(node));
				//node.testSplits = new ArrayList<Split>();
				//node.testSplitsRandom = new ArrayList<Split>();
				treePath.push(Dir.LEFT);
				break;
			default: throw new SAXException("Impossible to have "+numChildren+" child nodes.");
			}
			
			break;
		case NA:
			throw new SAXException("Unknown tag: "+qName);
		default:
			notCaught.add(el);
		}
	}
	
	

	@Override
	public void endElement(String uri, String localName, String qName)
	throws SAXException {
		XMLTag el = XMLTag.getValue(qName);
		//the closing tag we're reading should be the one on top of the stack. 
		if (el != opened.peek()) throw new SAXException("Incorrectly nested tags.");
		switch(el){
		case EXPERIMENTS:
			parseExperiments(elementContent);
			System.out.println("Done reading experiments");
			break;
		case DATAMATRIX:
			parseDataMatrix(elementContent);
			calcDataPoints();
			System.out.println("Done reading DataMatrix");
			break;
		case DATATYPE:
			System.out.println("Done reading DataType");
			break;
		case REGULATORS:
			setNormalizedDataAndSplitValue();
			System.out.println("Done reading Regulators");
			break;
		case REGULATOR:
			
			break;
		case RANDOMREGULATOR:
			break;
		case NORMALGAMMAPRIOR:
			break;
			
		case MODULESET:
			progListener.setMyProgress(80);
//			modnet.moduleSets.add(modset);
//			modnet.moduleSet = modset;
			modnet.setModules(modset);
			System.out.println("Done reading ModuleSet");
			break;
		case MODULE:
			this.modset.add(mod);
			break;
		case GENES:
			break;
		case GENE:
			break;
		case REGULATORWEIGHTS:
			break;
		case REGULATIONTREES:
			break;
		case ROOT:
			parseRootNodeEnd();
			break;
		case TREENODE:
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



	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		//accumulate the read characters until a new element is found
		elementContent.append(new String(ch, start, length));
	}

	
	/**
	 * Check if the parent of the current element is what we 
	 * think it is.
	 * 
	 * Top element: size-1 : current open element
	 * Parent element: size-2 : parent element
	 * 
	 * @param el
	 * @return
	 */
	private boolean hasParents(XMLTag el){
		return opened.get(opened.size()-2)== el;
	}
	
	/**
	 * Checks if the given list is a path from the current element (not included) 
	 * towards the root.
	 * 
	 * @param els a parent list, ordered from parent towards root
	 * @return
	 */
	private boolean hasParents(List<XMLTag> els){
		boolean ok = true;
		int depth = opened.size()-2;
		for (XMLTag el : els){
			if (opened.get(depth) != el){
				ok = false;
			}
			depth--;
		}
		return ok;
	}
	
	
	private void parseAlgorithmParam(Attributes attributes) {
		String name = attributes.getValue("name");
		String value = attributes.getValue("value");
//		this.modnet.parameters.put(name, value);
	}
	
	
	/**
	 * Take the tab delimited line with experiments and add them all 
	 * to the conditionSet of the ModuleNetwork.
	 * 
	 * @param expString
	 */
	private void parseExperiments(StringBuffer expBuffer) {
		String expString = new String(expBuffer);
		String[] expLine = expString.split("\\t");
//		modnet.conditionSet = new ArrayList<Experiment>(expLine.length);
		int i = 0;
		for (String exp : expLine) {
//			modnet.conditionSet.add(new Experiment(exp, i++));
			modnet.addCondition(exp);
		}
		
	}
	
	/**
	 * The content of the DataMatrix element is a tab delimited file (one line/entry).
	 * Parse this file and add it to the ModuleNetwork
	 * 
	 * @param elementContent2
	 */
	private void parseDataMatrix(StringBuffer dataMatrix) {
		String data = new String(dataMatrix);
		String[] data1 = data.split("\\n");
		String[] data2 = data1[1].split("\\t");
		int numGenes = data1.length - 1;
//		modnet.numGenes = numGenes;
		int numCond = data2.length - 3;
//		modnet.numCond = numCond;
//		modnet.setData(new double[numGenes][numCond]);
		
//		modnet.geneSet = new ArrayList<Gene>(numGenes);
//		modnet.geneMap = new HashMap<String, Gene>();
		for (int i = 0; i < numGenes; i++) {
			double[] geneData = new double[numCond];
			
			data2 = data1[i + 1].split("\\t");
			//gene name/description/number
			Gene newGene = new Gene(data2[0]);
			modnet.addGene(newGene);
//			modnet.geneMap.put(modnet.geneSet.get(i).name, modnet.geneSet.get(i));
			for (int j = 0; j < numCond; j++) {
				try {
					geneData[j] = Double.parseDouble(data2[j + 3]);
				} catch (InputMismatchException e) {
					geneData[j] = Double.NaN;
				}
			}
//			newGene.realData = modnet.data[i];
			newGene.setData(geneData);
		}
		
	}
	
	/**
	 * Calculate mean, sigma, min and max
	 */
	private void calcDataPoints() {
//		double nrDataPoints = 0;
//		double sumSquare = 0.0;
//		modnet.dataMean = 0.0;
//		modnet.dataSigma = 0.0;
//		modnet.dataMin = Double.POSITIVE_INFINITY;
//		modnet.dataMax = Double.NEGATIVE_INFINITY;
//		for (int i = 0; i < modnet.data.length; i++) {
//			for (int j = 0; j < modnet.data[i].length; j++) {
//				if (!Double.isNaN(modnet.data[i][j])) {
//					modnet.dataMean += modnet.data[i][j];
//					sumSquare += Math.pow(modnet.data[i][j], 2);
//					nrDataPoints += 1;
//					if (modnet.data[i][j] < modnet.dataMin)
//						modnet.dataMin = modnet.data[i][j];
//					else if (modnet.data[i][j] > modnet.dataMax)
//						modnet.dataMax = modnet.data[i][j];
//				}
//			}
//		}
//
//		modnet.dataMean /= (double) nrDataPoints;
//		modnet.dataSigma = Math.sqrt(sumSquare - nrDataPoints * Math.pow(modnet.dataMean, 2)) / Math.sqrt((double) nrDataPoints);
//
//		
	}
	private void parseDataType(Attributes atts) {
//		String type = atts.getValue("value");
//		if (type!=null) {
//			if (type.equals("RATIO"))
//				modnet.dataType = DataType.RATIO;
//			else if (type.equals("AFFY"))
//				modnet.dataType = DataType.AFFY;
//		} else { // set default to RATIO
//			modnet.dataType = DataType.RATIO;
//		}
//		System.out.printf(modnet.dataType.toString() + 
//				" dataMean %f, dataSigma %f, dataMin %f, dataMax %f, numGenes %d\n", 
//				modnet.dataMean, modnet.dataSigma, modnet.dataMin, modnet.dataMax, modnet.numGenes);

	}
	
	private void parseRegulator(Attributes atts) {
		int regnum = Integer.parseInt(atts.getValue("id"));
		boolean flag = Boolean.parseBoolean(atts.getValue("discrete"));
//		modnet.regul1atorSet.add(modnet.geneSet.get(regnum));
		
//		modnet.geneSet.get(regnum).setDiscrete(flag);
	}
	
	private void setNormalizedDataAndSplitValue() {
		// set normalized data and possible split values
//		for (Gene g : modnet.regulatorSet) {
//			g.setNormData(modnet.data);
//			g.possibleSplitValues = new ArrayList<Double>();
//			for (double x : modnet.data[g.number])
//				g.possibleSplitValues.add(x);
//		}
		
	}
	
	private void setNormalGammaPrior(Attributes atts) {
//		modnet.normalGammaPrior = new double[4];
//		modnet.normalGammaPrior[0] = Double.parseDouble(atts.getValue("lambda0"));
//		modnet.normalGammaPrior[1] = Double.parseDouble(atts.getValue("mu0"));
//		modnet.normalGammaPrior[2] = Double.parseDouble(atts.getValue("alpha0"));
//		modnet.normalGammaPrior[3] = Double.parseDouble(atts.getValue("beta0"));
	}

	private void parseRegulatorWeight(Attributes atts) {
		String regname = atts.getValue("name");
		double weight = Double.parseDouble(atts.getValue("weight"));
//		mod.regulatorWeights.put(modnet.geneMap.get(regname), weight);
//		System.out.println(regname+ " "+weight);
	}
	
	
	private void parseRootNodeEnd() {
//		for (TreeNode nd : this.rootNode.getInternalNodes()) {
//			for (Split splt : nd.testSplits) {
//				splt.computeSign();
//				splt.computeBeta();
//			}
//		}
//		this.mod.hierarchicalTrees.add(this.rootNode);
//		this.mod.hierarchicalTree = this.rootNode;
		this.mod.setRootNode(rootNode);
//		System.out.println("Done reading root");
		
	}
	
	//TODO replace by enums
	private final String INTERNAL = "internal";
	private final String LEAF = "leaf";
	
	private void parseTreeNodeAtts(Attributes attributes){
//		node.leafDistribution.score = Double.parseDouble(attributes.getValue("score"));
		String expts = attributes.getValue("condSet");
		ArrayList<Condition> condList = new ArrayList<Condition>();
		StringTokenizer tokens = new StringTokenizer(expts,";");
		List<Condition> expList = modnet.getConditionList();
		while(tokens.hasMoreTokens()){
			int expNumber = Integer.parseInt(tokens.nextElement().toString());
			condList.add(expList.get(expNumber));
//			condList.add(Integer.parseInt(tokens.nextElement().toString()));
		}
//		node.leafDistribution.condSet = condList;
		node.setConditions(condList);
		// compute statistics and score as if node was leaf
//		node.statistics();
//		node.leafDistribution.bayesianScore();	
	}
	
	
	
	private void parseRegulatorTreeNode(Attributes attributes) {
		String regname = attributes.getValue("name");
		double entropy = Double.parseDouble(attributes.getValue("entropy"));
		double splitvalue = Double.parseDouble(attributes.getValue("splitValue"));
		// look up regulator name
//		for (Gene reg : node.module.moduleNetwork.regulatorSet){
//			if (reg.name.equals(regname)){
//				Split splt = new Split(node, reg, splitvalue, entropy);
//				node.testSplits.add(splt);
//				node.regulationSplit = splt;
//				break;
//			}
//		}
		
	}
	private void parseRandomRegulatorTreeNode(Attributes attributes) {
		String regname = attributes.getValue("name");
		double entropy = Double.parseDouble(attributes.getValue("entropy"));
		double splitvalue = Double.parseDouble(attributes.getValue("splitValue"));
		// look up regulator name
//		for (Gene reg : node.module.moduleNetwork.regulatorSet){
//			if (reg.name.equals(regname)){
//				Split splt = new Split(node, reg, splitvalue, entropy);
//				node.testSplitsRandom.add(splt);
//				break;
//			}
//		}
		
	}

}
