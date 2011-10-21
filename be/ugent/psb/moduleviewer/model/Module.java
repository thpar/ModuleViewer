package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;

public class Module {

	
	private ModuleNetwork modnet;
	private int id;
	private String name;
	
	
	/**
	 * Root node of the condition tree
	 */
	ConditionNode conditionTree;
	/**
	 * Conditions that are not incorporated in the tree structure
	 */
	private List<Condition> nonTreeConditions = new ArrayList<Condition>();
	
	
	/**
	 * Root node of the gene tree
	 */
	private GeneNode geneTree;
	
	/**
	 * Root node of the regulator tree
	 */
	private GeneNode regulatorTree;
	
	private double meanGenes;
	private double meanRegs;
	private double sigmaGenes;
	private double sigmaRegs;
	private double meanAll;
	private double sigmaAll;
	
	//TODO implement caching. For now, we treat every request as a new one.
	private boolean dataChanged = true;
	
	private List<AnnotationBlock<?>> annotationBlocks = new ArrayList<AnnotationBlock<?>>();
	
	
	
	public Module(ModuleNetwork modnet, int id) {
		this.modnet = modnet;
		this.id = id;
	}
	
	public Module(ModuleNetwork modnet, int id, String name) {
		this.modnet = modnet;
		this.id = id;
		this.name = name;
	}
	


	/**
	 * Returns the root node of the condition tree
	 * @return
	 */
	public ConditionNode getConditionTree() {
		return conditionTree;
	}

	public void setConditionTree(ConditionNode rootNode) {
		this.conditionTree = rootNode;
	}

	public GeneNode getGeneTree() {
		return geneTree;
	}

	public void setGeneTree(GeneNode geneTree) {
		this.geneTree = geneTree;
	}

	public GeneNode getRegulatorTree() {
		return regulatorTree;
	}

	public void setRegulatorTree(GeneNode regulatorTree) {
		this.regulatorTree = regulatorTree;
	}

	public int getId() {
		return id;
	}

	
	public AnnotationBlock<?> getAnnotationBlock(int blockId){
		for (AnnotationBlock<?> block : annotationBlocks){
			if (block.getBlockID()==blockId) return block;
		}
		return null;
	}
	
	public List<AnnotationBlock<?>> getAnnotationBlocks(){
		return annotationBlocks;
	}
	
	public <Q> List<AnnotationBlock<Q>> getAnnotationBlocks(DataType type){
		List<AnnotationBlock<Q>> typeBlocks = new ArrayList<AnnotationBlock<Q>>();
		for (AnnotationBlock<?> ab : annotationBlocks){
			if (ab.getDataType() == type){
				typeBlocks.add((AnnotationBlock<Q>)ab);
			}
		}
		return typeBlocks;
	}
	public void addAnnotationBlock(AnnotationBlock<?> ab){
		this.annotationBlocks.add(ab);
	}
	
	public ModuleNetwork getModuleNetwork() {
		return modnet;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		if (name==null || name.isEmpty()) return "module "+this.id;
		else return this.name;
	}

	public void addNonTreeCondition(int condId) throws UnknownItemException{
		Condition cond = modnet.getCondition(condId);
		this.nonTreeConditions.add(cond);
		
	}

	public List<Condition> getNonTreeConditions() {
		return nonTreeConditions;
	}


	
	public double getMeanGenes() {
		if (dataChanged){
			double[] output;
			output = calcDataPoints(this.geneTree);
			this.meanGenes = output[0];
			this.sigmaGenes = output[1];
		}
		return this.meanGenes;
	}
	public double getSigmaGenes() {
		if (dataChanged){
			double[] output;
			output = calcDataPoints(this.geneTree);
			this.meanGenes = output[0];
			this.sigmaGenes = output[1];
		}
		return this.sigmaGenes;
	}
	
	
	public double getMeanRegs() {
		if (dataChanged){
			double[] output;
			output = calcDataPoints(this.regulatorTree);
			this.meanRegs = output[0];
			this.sigmaRegs = output[1];
		}
		return this.meanRegs;
	}

	public double getSigmaRegs() {
		if (dataChanged){
			double[] output;
			output = calcDataPoints(this.regulatorTree);
			this.meanRegs = output[0];
			this.sigmaRegs = output[1];
		}
		return this.sigmaRegs;
	}
	
	public double getMeanAll() {
		if (dataChanged){
			double[] output;
			GeneNode allTree = new GeneNode();
			allTree.setLeft(geneTree);
			allTree.setRight(regulatorTree);
			output = calcDataPoints(allTree);
			this.meanAll = output[0];
			this.sigmaAll = output[1];
		}
		return this.meanAll;
	}

	public double getSigmaAll() {
		if (dataChanged){
			double output[];
			GeneNode allTree = new GeneNode();
			allTree.setLeft(geneTree);
			allTree.setRight(regulatorTree);
			output = calcDataPoints(allTree);
			this.meanAll = output[0];
			this.sigmaAll = output[1];
		}
		return this.sigmaAll;
	}
	
	/**
	 * 
	 * @param tree
	 * @return [mean, sigma]
	 */
	private double[] calcDataPoints(GeneNode tree) {
		double nrDataPoints = 0;
		double sumSquare = 0.0;
		double mean = 0.0;
		double sigma = 0.0;
		for (Gene gene : tree.getGenes()){
			for (double value : gene.getData()) {
				if (!Double.isNaN(value)) {
					mean += value;
					sumSquare += Math.pow(value, 2);
					nrDataPoints++;
				}
			}
		}
		mean /= (double) nrDataPoints;
		sigma = Math.sqrt(sumSquare - nrDataPoints * Math.pow(mean, 2)) / Math.sqrt((double) nrDataPoints);
		double[] output = new double[2];
		output[0] = mean;
		output[1] = sigma;
		return output;
	}
	
	
	
}
