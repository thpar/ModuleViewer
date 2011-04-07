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
	 * All possible regulatores?
	 */
	private List<Gene> regulators;
	
	
	/**
	 * The original data matrix
	 */
	private double data[][];
	
	
	
	
}
