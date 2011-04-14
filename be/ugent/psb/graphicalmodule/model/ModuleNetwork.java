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
	 * All conditions in this network. Maps condition name to the condition
	 * object.
	 */
	private Map<String, Condition> conditions;
	
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
	

	public void addCondition(String condName){
		Condition cond = new Condition(condName, conditions.size());
		this.conditions.put(condName, cond);
	}
	
	public Condition getCondition(String condId){
		return this.conditions.get(condId);
	}
	
	public List<Condition> getConditionList(){
		List<Condition> conds = new ArrayList<Condition>();
		for (Condition cond : conditions.values()){
			conds.add(cond);
		}
		Collections.sort(conds, new Comparator<Condition>(){
			@Override
			public int compare(Condition o1, Condition o2) {
				if (o1.getNumber() == o2.getNumber()) return 0;
				else if (o1.getNumber()< o2.getNumber()) return -1;
				else return 1;
			}
			
		});
		return conds;
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
