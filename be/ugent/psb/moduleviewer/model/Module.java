package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;

public class Module {

	
	private ModuleNetwork modnet;
	private int id;
	private String name;
	
	/**
	 * Genes in this module
	 */
	private List<Gene> genes = new ArrayList<Gene>();

	/**
	 * The genes that are top regulators for this module 
	 */
	List<Gene> topRegulators = new ArrayList<Gene>();
	
	ConditionNode rootNode;
	
	private List<AnnotationBlock> annotationBlocks = new ArrayList<AnnotationBlock>();
	
	
	private double mean;
	private double sigma;
	private boolean changed = true;
	
	
	public Module(ModuleNetwork modnet, int id) {
		this.modnet = modnet;
		this.id = id;
	}
	
	public Module(ModuleNetwork modnet, int id, String name) {
		this.modnet = modnet;
		this.id = id;
		this.name = name;
	}
	
	public List<Gene> getGenes() {
		return genes;
	}


	/**
	 * Add a gene by id to this module. The gene has to exist in the ModuleNetwork already.
	 * 
	 * @param geneId
	 */
	public void addGene(String geneId) throws UnknownItemException{
		Gene gene = modnet.getGene(geneId);
		this.genes.add(gene);
	}

	
	public List<Gene> getTopRegulators() {
		return topRegulators;
	}

	public void setTopRegulators(List<Gene> topRegulators) {
		this.topRegulators = topRegulators;
	}

	/**
	 * Returns the root node of the condition tree
	 * @return
	 */
	public ConditionNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(ConditionNode rootNode) {
		this.rootNode = rootNode;
	}

	public void setSigma(int sigma) {
		this.sigma = sigma;
	}

	public int getId() {
		return id;
	}

	
	public AnnotationBlock getAnnotationBlock(String blockId){
		for (AnnotationBlock block : annotationBlocks){
			if (block.getBlockName().equals(blockId)) return block;
		}
		return null;
	}
	
	public List<AnnotationBlock> getAnnotationBlocks(){
		return annotationBlocks;
	}
	public List<AnnotationBlock> getAnnotationBlock(DataType type){
		List<AnnotationBlock> typeBlocks = new ArrayList<AnnotationBlock>();
		for (AnnotationBlock ab : annotationBlocks){
			if (ab.getType() == type){
				typeBlocks.add(ab);
			}
		}
		return typeBlocks;
	}
	public void addAnnotationBlock(AnnotationBlock ab){
		this.annotationBlocks.add(ab);
	}
	
	public ModuleNetwork getModuleNetwork() {
		return modnet;
	}
	
	
	public double getMean(){
		if (changed){
			calculateMeanAndSigma();
		}
		return mean;
	}
	public double getSigma(){
		if (changed){
			calculateMeanAndSigma();
		}
		return sigma;
	}

	private void calculateMeanAndSigma() {
		
		//get the mean for this module
		int nbval=0;
		for (Gene g : this.genes) {
			for (double value : g.getData()) {
				if (!Double.isNaN(value)) {
					this.mean += value;
					nbval++;
				}
			}
		}
		this.mean = this.mean / nbval;

		
		//get the sigma for this module
		nbval=0;
		for (Gene g : this.genes) {
			for (double value : g.getData()) {
				if (!Double.isNaN(value)) {
					this.sigma += Math.pow(this.mean - value,2);
					nbval++;
				}
			}
		}
		this.sigma = Math.sqrt(this.sigma / nbval);



		changed = false;
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
	
	
	
	
}
