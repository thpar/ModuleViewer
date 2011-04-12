package be.ugent.psb.graphicalmodule.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleNetwork {

	/**
	 * All genes in this network. Maps gene id to gene object
	 */
	private Map<String, Gene> genes;
	
	/**
	 * All conditions or experiments in this network. Maps experiment name to the experiment object.
	 */
	private Map<String, Experiment> conditions;
	
	/**
	 * The modules in which genes are ordered {@link something}
	 */
	private List<Module> modules;
	
	/**
	 * All possible regulators?
	 */
	private List<Gene> regulators;
	
	
	/**
	 * Maps condition checklists by name
	 */
	private Map<String, ConditionCheckList> conditionAnnotation = new HashMap<String, ConditionCheckList>();
	
	
	
	/**
	 * Adds a gene to to the complete gene list. 
	 * 
	 * @param geneId
	 * @return the newly added {@link Gene} object.
	 */
	public Gene addGene(String geneId){
		Gene gene = new Gene(geneId);
		this.genes.put(geneId, gene);
		return gene;	
	}
	
	public void addGene(Gene gene){
		this.genes.put(gene.getId(), gene);
	}
	
	/**
	 * Get a gene by gene id.
	 * 
	 * @param geneId
	 * @return
	 */
	public Gene getGene(String geneId){
		return genes.get(geneId);
	}
	

	public void addExperiment(String expName){
		Experiment exp = new Experiment(expName, conditions.size());
		this.conditions.put(expName, exp);
	}
	
	public Experiment getExperiment(String expId){
		return this.conditions.get(expId);
	}
	
	public List<Experiment> getExperimentList(){
		List<Experiment> exps = new ArrayList<Experiment>();
		for (Experiment exp : conditions.values()){
			exps.add(exp);
		}
		Collections.sort(exps, new Comparator<Experiment>(){
			@Override
			public int compare(Experiment o1, Experiment o2) {
				if (o1.getNumber() == o2.getNumber()) return 0;
				else if (o1.getNumber()< o2.getNumber()) return -1;
				else return 1;
			}
			
		});
		return exps;
	}
	
	public void addConditionCheckList(ConditionCheckList ccl){
		this.conditionAnnotation.put(ccl.getName(), ccl);
	}

	public List<Gene> getRegulators() {
		return regulators;
	}

	public void setRegulators(List<Gene> regulators) {
		this.regulators = regulators;
	}

	public void setModules(List<Module> modset) {
		this.modules = modset;
	}

	public List<Module> getModules() {
		return modules;
	}
	
	
	
	
}
