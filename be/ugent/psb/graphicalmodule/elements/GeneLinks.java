package be.ugent.psb.graphicalmodule.elements;

import java.util.Map;
import java.util.Map.Entry;

import be.ugent.psb.modulegraphics.elements.ConnectArrows;



/**
 * An element that needs a list with gene order, and a mapping
 * with gene ids from -> to to create links between the rows of genes
 * 
 * @author thpar
 *
 */
public class GeneLinks extends ConnectArrows{

	/**
	 * 
	 * @param genes list of genes, in the order they are drawn
	 * @param links links between those genes (from gene ID to set of gene IDs)
	 */
	public GeneLinks(Map<String, int[][]> linkSets){
		for (Entry<String, int[][]> ent : linkSets.entrySet()){
			String id = ent.getKey();
			int[][] links = ent.getValue();
			int counter = 0;
			for (int[] toGenes : links){
				if (toGenes!=null){
					for(int toGene : toGenes){
						this.addEdge(id, counter, toGene);
					}
				}
				counter++;
			}
		}
	}

}
