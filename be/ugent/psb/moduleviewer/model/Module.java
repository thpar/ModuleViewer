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
	 * Root nodes of the regulator trees
	 */
	private List<GeneNode> regulatorTrees = new ArrayList<GeneNode>();
		
	
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
	 * Returns the root node of the condition tree.
	 * If the condition tree is not set specifically for this module, the parent modnet is asked
	 * for a single leaf node containing all conditions. 
	 * 
	 * @return root node of the condition tree
	 */
	public ConditionNode getConditionTree() {
		if (this.conditionTree!=null){
			return conditionTree;			
		} else {
			return this.modnet.getConditionTree();
		}
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

	public List<GeneNode> getRegulatorTrees() {
		return regulatorTrees;
	}

	public void addRegulatorTree(GeneNode regulatorTree) {
		this.regulatorTrees.add(regulatorTree);
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

	
	public double getMeanAll() {
		List<GeneNode> trees = new ArrayList<GeneNode>();
		trees.add(geneTree);
		for (GeneNode rt : regulatorTrees){
			trees.add(rt);
		}
		double[] output = calcDataPoints(trees);
		return output[0];
	}

	public double getSigmaAll() {
		List<GeneNode> trees = new ArrayList<GeneNode>();
		trees.add(geneTree);
		for (GeneNode rt : regulatorTrees){
			trees.add(rt);
		}
		double[] output = calcDataPoints(trees);
		return output[1];
	}
	
	
	/**
	 * 
	 * @param tree
	 * @return [mean, sigma]
	 */
	private double[] calcDataPoints(List<GeneNode> trees) {
		double nrDataPoints = 0;
		double sumSquare = 0.0;
		double mean = 0.0;
		double sigma = 0.0;
		for (GeneNode tree : trees){
			for (Gene gene : tree.getGenes()){
				for (double value : gene.getData()) {
					if (!Double.isNaN(value)) {
						mean += value;
						sumSquare += Math.pow(value, 2);
						nrDataPoints++;
					}
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
