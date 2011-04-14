package be.ugent.psb.moduleviewer.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GeneLinks {

	
	private String name;
	
	/**
	 * Map with "from" genes pointing to a list of "to" genes
	 */
	private Map<Gene, Set<Gene>> links = new HashMap<Gene, Set<Gene>>();

	private ModuleNetwork modnet;
	
	public GeneLinks(String name, ModuleNetwork modnet){
		this.name = name;
		this.modnet = modnet;
	}
	
	public String getName() {
		return name;
	}
	
	public void addLink(Gene from, Gene to){
		Set<Gene> toSet = links.get(from);
		if (toSet==null) toSet = new HashSet<Gene>();
		toSet.add(to);
	}
	public void addLink(String fromId, String toId){
		Gene from = modnet.getGene(fromId);
		Gene to = modnet.getGene(toId);
		addLink(from, to);
	}
	
	public Map<Gene, Set<Gene>> getLinks(){
		return links;
	}
}
