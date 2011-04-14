package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

public class ConditionCheckList {
	/**
	 * Name of the list.
	 */
	private String name;
	
	
	/**
	 * List of condition objects
	 */
	private List<Condition> conds;

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
	public void addCondition(String condId){
		Condition exp = modnet.getCondition(condId);
		this.conds.add(exp);
	}
	public void addConditions(List<String> condIdList){
		for(String cond : condIdList){
			addCondition(cond);
		}
	}
	public List<Condition> getConditions(){
		return conds;
	}
	public List<String> getConditionIds(){
		List<String> geneIds = new ArrayList<String>();
		for(Condition cond : conds){
			geneIds.add(cond.getName());
		}
		return geneIds;
	}
	
	
	public boolean hasCondition(Condition cond){
		return conds.contains(cond);
	}
	public boolean hasCondition(String condId){
		return this.getConditionIds().contains(condId);
	}
	
	
}
