package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Links extra labels and values to a list of genes.
 * 
 * @author thpar
 *
 */
public class GeneAnnotation extends Annotation<Gene>{

	
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
		this.addItem(gene);
	}
	
	
	public List<String> getGeneIds(){
		List<String> geneIds = new ArrayList<String>();
		for(Gene gene : contents){
			geneIds.add(gene.getId());
		}
		return geneIds;
	}

	
	
	
	
	
}
