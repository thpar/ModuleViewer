package be.ugent.psb.graphicalmodule.model;

import java.util.List;

public class ModuleNetwork {

	/**
	 * All genes in this network
	 */
	private List<Gene> genes;
	
	/**
	 * All conditions or experiments in this network
	 */
	private List<Experiment> conditions;
	
	/**
	 * The modules in which genes are ordered {@link something}
	 */
	private List<Module> modules;
	
	/**
	 * All possible regulators?
	 */
	private List<Gene> regulators;
	
	
	/**
	 * The original data matrix
	 */
	private double data[][];
	
	
	/**
	 * 
	 * @param geneId
	 */
	public void addGene(String geneId){
		this.genes.add(new Gene(geneId));
	}
	
	public void addExperiment(String expName){
		Experiment exp = new Experiment(expName, conditions.size());
		this.conditions.add(exp);
	}
	
	
	
	
	
}
