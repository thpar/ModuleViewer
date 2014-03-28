package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.UnknownItemException.ItemType;

public class ModuleNetwork{

	/**
	 * All genes in this network. Maps gene id to gene object
	 */
	private Map<String, Gene> genes = new HashMap<String, Gene>();
	
	/**
	 * All genes in the network. Quick way of resolving a gene by Alias
	 */
	private Map<String, Gene> geneAliasses = new HashMap<String, Gene>();
	
	/**
	 * All conditions in this network. Maps condition name to the condition
	 * object.
	 */
	private Map<String, Condition> conditions = new HashMap<String, Condition>();
	
	/**
	 * The modules in which genes are organized
	 */
	private TreeMap<Integer, Module> modules = new TreeMap<Integer, Module>();
	
	/**
	 * All possible regulators?
	 */
	private List<Gene> regulators;
	
	
	/**
	 * Maps condition checklists by name
	 */
//	private Map<String, ConditionCheckList> conditionAnnotation = new HashMap<String, ConditionCheckList>();

	private double dataMean;

	private double dataSigma;

	private double dataMin;

	private double dataMax;

		
	private boolean dataChanged = true;

	private List<Condition> conditionList = new ArrayList<Condition>();
	
	/**
	 * Fallback condition tree: one single leaf, constructed while parsing the expression matrix 
	 */
	private ConditionNode conditionTree;

	/**
	 * The ID to be assigned to the next {@link AnnotationBlock} in this network.
	 * Will be incremented every time a new {@link AnnotationBlockFactory} is created.
	 */
	private int nextAnnotationBlockID = 0;

	private List<AnnotationBlock<?>> globalAnnotationBlocks = new ArrayList<AnnotationBlock<?>>();
	
	
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
	
	/**
	 * Adds a gene to to the complete gene list. 
	 * 
	 * @param geneId
	 * @return the newly added {@link Gene} object.
	 */
	public Gene addGene(String geneId, String alias){
		Gene gene = new Gene(geneId, alias);
		this.genes.put(geneId, gene);
		this.geneAliasses.put(alias, gene);
		return gene;	
	}
	
	
	/**
	 * Add a gene to the module network.
	 * 
	 * @param gene
	 */
	public void addGene(Gene gene){
		this.genes.put(gene.getName(), gene);
		if (gene.getAlias()!= null && !gene.getAlias().isEmpty()){
			this.geneAliasses.put(gene.getAlias(), gene);
		}
	}
	
	public void setGeneAlias(Gene gene, String alias){
		String oldAlias = gene.getAlias();
		if (oldAlias != null && !oldAlias.isEmpty() && 
				!oldAlias.equals(alias) && geneAliasses.containsKey(oldAlias)){
			geneAliasses.remove(oldAlias);
		}
		gene.setAlias(alias);
		geneAliasses.put(alias, gene);
	}
	
	/**
	 * Get a gene by gene id or alias.
	 * 
	 * @param geneId
	 * @return
	 */
	public Gene getGene(String geneId) throws UnknownItemException {
		if (!genes.containsKey(geneId) && !geneAliasses.containsKey(geneId)) 
			throw new UnknownItemException(UnknownItemException.ItemType.GENE, geneId);
		if (genes.containsKey(geneId)) return genes.get(geneId);
		else return geneAliasses.get(geneId);
	}
	
	public void addCondition(String condName){
		Condition cond = new Condition(condName, conditions.size());
		this.conditions.put(condName, cond);
		this.conditionList.add(cond);
	}
	
	public Condition getCondition(String condId) throws UnknownItemException{
		if (!this.conditions.containsKey(condId)) throw new UnknownItemException(ItemType.CONDITION, condId);
		else return this.conditions.get(condId);
	}
	
	public List<Condition> getConditionList(){
//		this.conditionList = new ArrayList<Condition>();
//		for (Condition cond : conditions.values()){
//			conditionList.add(cond);
//		}
//		Collections.sort(conditionList, new Comparator<Condition>(){
//			@Override
//			public int compare(Condition o1, Condition o2) {
//				if (o1.getNumber() == o2.getNumber()) return 0;
//				else if (o1.getNumber()< o2.getNumber()) return -1;
//				else return 1;
//			}
//			
//		});
		return this.conditionList;
	}
	
	public Condition getCondition(int conditionNumber) throws UnknownItemException{
		if (this.conditionList.size()<=conditionNumber) throw new UnknownItemException(ItemType.CONDITION, conditionNumber);
		else return this.conditionList.get(conditionNumber);
	}
	

	public List<Gene> getRegulators() {
		return regulators;
	}

	public void setRegulators(List<Gene> regulators) {
		this.regulators = regulators;
	}

	public void setModules(List<Module> modset) {
		for (Module mod : modset){
			this.addModule(mod);
		}
	}
	
	public void addModule(Module mod) {
		this.modules.put(mod.getId(), mod);
	}

	public Module getModule(int modId) throws UnknownItemException{
		if (!modules.containsKey(modId)) throw new UnknownItemException(UnknownItemException.ItemType.MODULE, modId);
		return modules.get(modId);
	}
	
	public Collection<Module> getModules(){
		return modules.values();
	}
	/**
	 * Returns the previous module id in the list or the current module id if we're at the beginning.
	 * @param currentModId
	 * @return
	 */
	public int getPrevModuleId(int currentModId){
		Integer key = modules.lowerKey(currentModId);
		if (key!=null){
			return key;
		} else return currentModId;
	}
	/**
	 * Returns the next module id in the list or the current module id if we're at the end.
	 * @param currentModId
	 * @return
	 */
	public int getNextModuleId(int currentModId){
		Integer key = modules.higherKey(currentModId);
		if (key!=null){
			return key;
		} else return currentModId;
	}
	public boolean isFirstModule(int modId){
		return modules.lowerKey(modId) == null;
	}
	public boolean isLastModule(int modId){
		return modules.higherKey(modId) == null;
	}
	
	public int getFirstModuleId(){
		Integer[] moduleKeys = new Integer[0];
		moduleKeys = this.modules.keySet().toArray(moduleKeys);
		Arrays.sort(moduleKeys);
		Integer first = moduleKeys[0]; 
		return first;
	}
	
	/**
	 * Calculates basic stats for the data in this network (as seen in the fromXML method from LeMoNe ModuleNetwork)
	 * Mean, Sigma, Min and Max
	 */
	private void calcDataPoints() {
		double nrDataPoints = 0;
		double sumSquare = 0.0;
		this.dataMean = 0.0;
		this.dataSigma = 0.0;
		
		this.dataMin = Double.POSITIVE_INFINITY;
		this.dataMax = Double.NEGATIVE_INFINITY;
		for (Gene gene : genes.values()){
			for (double value : gene.getData()) {
				if (!Double.isNaN(value)) {
					this.dataMean += value;
					sumSquare += Math.pow(value, 2);
					nrDataPoints++;
					if (value < this.dataMin)
						this.dataMin = value;
					else if (value > this.dataMax)
						this.dataMax = value;
				}
			}
		}

		this.dataMean /= (double) nrDataPoints;
		this.dataSigma = Math.sqrt(sumSquare - nrDataPoints * Math.pow(this.dataMean, 2)) / Math.sqrt((double) nrDataPoints);
		dataChanged = false;
	}
	
	/**
	 * Calculate Mean and Sigma, as seen in the constructor of ModuleNetwork in Enigma.
	 * 
	 * @deprecated has to loop all data twice. Use {@link calcDataPoints()} instead
	 */
	private void calcDataPointsEnigmaStyle(){
		 double nrDataPoints = 0 ;
			for(Gene gene : genes.values()){
				for(double value : gene.getData()){
					if(!Double.isNaN(value)){
					  this.dataMean += value;
					  nrDataPoints += 1 ;
					}
				}
			}
			this.dataMean /= (nrDataPoints *1.0) ;
			for(Gene gene : genes.values()){
				for(double value : gene.getData()){
					if(!Double.isNaN(value)){
						this.dataSigma += Math.pow(this.dataMean - value, 2) ; 
					}	
				}
			}
			this.dataSigma = Math.sqrt(this.dataSigma/(nrDataPoints *1.0)) ;
	}
	
	
	public double getMean(){
		if (dataChanged){
			calcDataPoints();
		}
		return dataMean;
	}
	public double getSigma(){
		if (dataChanged){
			calcDataPoints();
		}
		return dataSigma;
	}
	public double getMin(){
		if (dataChanged){
			calcDataPoints();
		}
		return dataMin;
	}
	public double getMax(){
		if (dataChanged){
			calcDataPoints();
		}
		return dataMax;
	}

	public ConditionNode getConditionTree() {
		return conditionTree;
	}

	public void setConditionTree(ConditionNode conditionTree) {
		this.conditionTree = conditionTree;
	}

	public int getNextAnnotationBlockID() {
		return nextAnnotationBlockID;
	}

	public void incrementNextAnnotationBlockID(){
		this.nextAnnotationBlockID++;
	}

	public AnnotationBlock<?> getGlobalAnnotationBlock(int blockID) {
		for (AnnotationBlock<?> block : this.globalAnnotationBlocks){
			if(block.getBlockID() == blockID){
				return block;
			}
		}
		return null;
	}

	public void addGlobalAnnotationBlock(AnnotationBlock<?> ab) {
		this.globalAnnotationBlocks.add(ab);
	}
	
	public <Q> List<AnnotationBlock<Q>> getGlobalAnnotationBlocks(DataType type){
		List<AnnotationBlock<Q>> blockList = new ArrayList<AnnotationBlock<Q>>();
		for (AnnotationBlock<?> block : globalAnnotationBlocks){
			if (block.getDataType() == type){
				blockList.add((AnnotationBlock<Q>)block);
			}
		}
		return blockList;
	}

	public List<AnnotationBlock<?>> getGlobalAnnotationBlocks() {
		return globalAnnotationBlocks;
	}
	
	
	
}
