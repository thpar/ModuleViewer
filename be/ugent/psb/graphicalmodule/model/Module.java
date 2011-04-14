package be.ugent.psb.graphicalmodule.model;

import java.util.ArrayList;
import java.util.List;

public class Module {

	
	private ModuleNetwork modnet;
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
	
	private List<GeneCheckList> checkLists = new ArrayList<GeneCheckList>();
	private List<GeneLinks> linkLists = new ArrayList<GeneLinks>();
	
	
	private int mean;
	private int sigma;
	
	//TODO splits... do we need this info?
	
	public Module(ModuleNetwork modnet, int id) {
		this.modnet = modnet;
		this.id = id;
	}

	public List<Gene> getGenes() {
		return genes;
	}

	public void setGenes(List<Gene> genes) {
		this.genes = genes;
	}

	/**
	 * Add a gene by id to this module. The gene has to exist in the ModuleNetwork already.
	 * 
	 * @param geneId
	 */
	public void addGene(String geneId){
		Gene gene = modnet.getGene(geneId);
		this.genes.add(gene);
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

	public int getId() {
		return id;
	}

	public int getMean() {
		return mean;
	}

	public List<GeneCheckList> getCheckLists() {
		return checkLists;
	}
	public List<String> getCheckListNames(){
		List<String> list = new ArrayList<String>();
		for (GeneCheckList cl : checkLists){
			list.add(cl.getName());
		}
		return list;
	}

	public List<GeneLinks> getLinkLists() {
		return linkLists;
	}
	
	public void addCheckList(GeneCheckList cl){
		this.checkLists.add(cl);
	}

	public void addLinkList(GeneLinks gl){
		this.linkLists.add(gl);
	}

	public ModuleNetwork getModuleNetwork() {
		return modnet;
	}
	
	
	
	
	
}
