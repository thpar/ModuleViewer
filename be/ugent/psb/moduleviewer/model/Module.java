package be.ugent.psb.moduleviewer.model;

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
	
	ConditionNode rootNode;
	
	
	/**
	 * @todo what is this?
	 */
	private List<Gene> parents;
	
	private List<GeneCheckList> checkLists = new ArrayList<GeneCheckList>();
	private List<GeneLinks> linkLists = new ArrayList<GeneLinks>();
	
	
	private double mean;
	private double sigma;
	private boolean changed = true;
	
	//TODO splits... do we need this info?
	
	public Module(ModuleNetwork modnet, int id) {
		this.modnet = modnet;
		this.id = id;
	}

	public List<Gene> getGenes() {
		return genes;
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

	public ConditionNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(ConditionNode rootNode) {
		this.rootNode = rootNode;
	}

	public void setSigma(int sigma) {
		this.sigma = sigma;
	}

	public int getId() {
		return id;
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
	
	
	public double getMean(){
		if (changed){
			calculateMeanAndSigma();
		}
		return mean;
	}
	public double getSigma(){
		if (changed){
			calculateMeanAndSigma();
		}
		return sigma;
	}

	private void calculateMeanAndSigma() {
		
		//get the mean for this module
		int nbval=0;
		for (Gene g : this.genes) {
			for (double value : g.getData()) {
				if (!Double.isNaN(value)) {
					this.mean += value;
					nbval++;
				}
			}
		}
		this.mean = this.mean / nbval;

		
		//get the sigma for this module
		nbval=0;
		for (Gene g : this.genes) {
			for (double value : g.getData()) {
				if (!Double.isNaN(value)) {
					this.sigma += Math.pow(this.mean - value,2);
					nbval++;
				}
			}
		}
		this.sigma = Math.sqrt(this.sigma / nbval);



		changed = false;
	}
	
	
}
