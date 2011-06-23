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
	 * Root node of the regulator tree
	 */
	private GeneNode regulatorTree;
	
	
	
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
	 * Returns the root node of the condition tree
	 * @return
	 */
	public ConditionNode getConditionTree() {
		return conditionTree;
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

	public GeneNode getRegulatorTree() {
		return regulatorTree;
	}

	public void setRegulatorTree(GeneNode regulatorTree) {
		this.regulatorTree = regulatorTree;
	}

	public int getId() {
		return id;
	}

	
	public AnnotationBlock<?> getAnnotationBlock(String blockId){
		for (AnnotationBlock<?> block : annotationBlocks){
			if (block.getBlockName().equals(blockId)) return block;
		}
		return null;
	}
	
	public List<AnnotationBlock<?>> getAnnotationBlocks(){
		return annotationBlocks;
	}
	public List<AnnotationBlock<?>> getAnnotationBlocks(DataType type){
		List<AnnotationBlock<?>> typeBlocks = new ArrayList<AnnotationBlock<?>>();
		for (AnnotationBlock<?> ab : annotationBlocks){
			if (ab.getType() == type){
				typeBlocks.add(ab);
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
	
	
	
	
}
