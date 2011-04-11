package be.ugent.psb.graphicalmodule.model;

import java.util.ArrayList;
import java.util.List;

public class ConditionCheckList {
	/**
	 * Name of the list.
	 */
	private String name;
	
	
	/**
	 * List of gene objects
	 */
	private List<Experiment> exps;

	/**
	 * The module network this list is referring to.
	 */
	private ModuleNetwork modnet;
	
	public ConditionCheckList(String name, ModuleNetwork modnet){
		this.name = name;
		this.modnet = modnet;
	}
	
	/**
	 * 
	 * @return list name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Add a condition by id
	 * 
	 * @param geneId 
	 */
	public void addExperiment(String expId){
		Experiment exp = modnet.getExperiment(expId);
		this.exps.add(exp);
	}
	public void addExperiments(List<String> expIdList){
		for(String exp : expIdList){
			addExperiment(exp);
		}
	}
	public List<Experiment> getExperiments(){
		return exps;
	}
	public List<String> getExperimentIds(){
		List<String> geneIds = new ArrayList<String>();
		for(Experiment exp : exps){
			geneIds.add(exp.getName());
		}
		return geneIds;
	}
	
	
	public boolean hasExperiment(Experiment exp){
		return exps.contains(exp);
	}
	public boolean hasExperiment(String expId){
		return this.getExperimentIds().contains(expId);
	}
	
	
}
