package be.ugent.psb.moduleviewer.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Annotates a list of genes or conditions.
 * 
 * T : class of objects to be annotated
 * 
 * @author thpar
 *
 * @param <T> Type of contents ({@link Gene} or {@link Condition})
 */
public class Annotation<T> {
	/**
	 * Name of the list.
	 */
	protected String name;
	/**
	 * The module network this list is referring to.
	 */
	protected ModuleNetwork modnet;

	/**
	 * Genes, conditions, ... the objects to be annotated.
	 */
	protected Set<T> contents = new HashSet<T>();
	
		
	/**
	 * Create a new annotation list, linked to the modnet
	 * @param name
	 * @param modnet
	 */
	public Annotation(String name, ModuleNetwork modnet){
		this.name = name;
		this.modnet = modnet;
	}
	
	/**
	 * 
	 * @return the name of this list
	 */
	public String getName(){
		return name;
	}
	
	public void addItem(T item){
		this.contents.add(item);
	}
	
	
	public Set<T> getItems(){
		return contents;
	}
	
	
	public boolean hasItem(T item){
		return this.contents.contains(item);
	}

	
	
	@Override
	public String toString(){
		String out = new String();
		out+=this.name;
		out+="\t";
		for (T item : contents){
			out+=":"+item+":" + " ";
		}
		return out;
	}

	public ModuleNetwork getModnet() {
		return modnet;
	}
	
	
	
}
