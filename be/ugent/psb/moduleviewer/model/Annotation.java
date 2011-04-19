package be.ugent.psb.moduleviewer.model;

import java.util.List;

/**
 * Annotates a list of genes or conditions.
 * 
 * T : class of objects to be annotated
 * U : class of optional continuous value.
 * 
 * @author thpar
 *
 * @param <T>
 * @param <U>
 */
public abstract class Annotation<T, U> {
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
	protected List<T> contents;
	
	/**
	 * Possibility to link a value to each object, rather than just 
	 * giving it an on/off statement.
	 */
	protected List<U> continuousValues;

	/**
	 * Whether or not this {@link Annotation} is using continuous values.
	 */
	protected boolean useContValues = false;
	
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
	
	
	abstract public void addItem(String itemId);
	abstract public void addItem(String itemId, U value);
	abstract public boolean hasItem(String itemId);

	public List<T> getItems() {
		return contents;
	}
	
	public boolean hasItem(Gene gene){
		return contents.contains(gene);
	}

}
