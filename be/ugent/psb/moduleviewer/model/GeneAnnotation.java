package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Links extra labels and values to a list of genes.
 * 
 * @author thpar
 *
 */
public class GeneAnnotation {

	/**
	 * Name of the list.
	 */
	private String name;
	
	
	/**
	 * List of gene objects
	 */
	private List<Gene> genes;

	/**
	 * The module network this list is referring to.
	 */
	private ModuleNetwork modnet;
	
	public GeneAnnotation(String name, ModuleNetwork modnet){
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
	 * Add a gene by gene id
	 * 
	 * @param geneId 
	 */
	public void addGene(String geneId){
		Gene gene = modnet.getGene(geneId);
		this.genes.add(gene);
	}
	public void addGenes(List<String> geneIdList){
		for(String gene : geneIdList){
			addGene(gene);
		}
	}
	public List<Gene> getGenes(){
		return genes;
	}
	public List<String> getGeneIds(){
		List<String> geneIds = new ArrayList<String>();
		for(Gene gene : genes){
			geneIds.add(gene.getId());
		}
		return geneIds;
	}
	
	
	public boolean hasGene(Gene gene){
		return genes.contains(gene);
	}
	public boolean hasGene(String geneId){
		return this.getGeneIds().contains(geneId);
	}
	
	
}
