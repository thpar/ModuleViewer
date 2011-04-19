package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Links extra labels and values to a list of genes.
 * 
 * @author thpar
 *
 */
public class GeneAnnotation<T> extends Annotation<Gene, T>{

	
	public GeneAnnotation(String name, ModuleNetwork modnet) {
		super(name, modnet);
	}

	/**
	 * Add a gene by gene id
	 * 
	 * @param geneId 
	 */
	@Override
	public void addItem(String itemId){
		Gene gene = modnet.getGene(itemId);
		this.contents.add(gene);
	}
	
	/**
	 * Add a gene by id and link a value to it.
	 * 
	 * @param geneId
	 * @param value
	 */
	@Override
	public void addItem(String geneId, T value){
		this.addItem(geneId);
		this.continuousValues.add(value);
	}
	
	public List<String> getGeneIds(){
		List<String> geneIds = new ArrayList<String>();
		for(Gene gene : contents){
			geneIds.add(gene.getId());
		}
		return geneIds;
	}
	
	@Override
	public boolean hasItem(String itemId){
		return this.getGeneIds().contains(itemId);
	}
	
	
}
