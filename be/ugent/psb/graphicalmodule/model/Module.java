package be.ugent.psb.graphicalmodule.model;

import java.util.List;

public class Module {

	
	private ModuleNetwork parentNetwork;
	private int id;
	
	/**
	 * Genes in this module
	 */
	private List<Gene> genes;

	/**
	 * The genes that are top regulators for this module 
	 */
	List<Gene> topRegulators;
	
	TreeNode rootNode;
	
	
	/**
	 * @todo what is this?
	 */
	private List<Gene> parents;
	
	private int mean;
	private int sigma;
	
	//TODO extra info... checklists...
	//TODO splits... do we need this info?
	
	public Module(ModuleNetwork parentNetwork, int id) {
		this.parentNetwork = parentNetwork;
		this.id = id;
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	public List<Gene> getTopRegulators() {
		return topRegulators;
	}

	public void setTopRegulators(List<Gene> topRegulators) {
		this.topRegulators = topRegulators;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public int getSigma() {
		return sigma;
	}

	public void setSigma(int sigma) {
		this.sigma = sigma;
	}

	public ModuleNetwork getParentNetwork() {
		return parentNetwork;
	}

	public int getId() {
		return id;
	}

	public int getMean() {
		return mean;
	}
	

	
	
	
}
