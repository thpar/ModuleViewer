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
}
