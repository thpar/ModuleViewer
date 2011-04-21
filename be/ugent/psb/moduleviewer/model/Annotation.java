package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Annotates a list of genes or conditions.
 * 
 * T : class of objects to be annotated
 * U : class of optional continuous value.
 * 
 * @author thpar
 *
 * @param <T> Type of contents ({@link Gene} or {@link Condition})
 */
abstract public class Annotation<T> {
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
	protected List<Double> continuousValues;

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
	
	public void addItem(T item){
		this.contents.add(item);
	}
	
	public void addItem(String itemId, Double value) {
		this.addItem(itemId);
		this.continuousValues.add(value);	
	}
	
	abstract public void addItem(String itemId);
	
	public List<T> getItems(){
		return contents;
	}
	
	public boolean hasItem(T item){
		return this.contents.contains(item);
	}

	public boolean isUseContValues() {
		return useContValues;
	}

	public void setUseContValues(boolean useContValues) {
		this.useContValues = useContValues;
		if (useContValues){
			this.continuousValues = new ArrayList<Double>();
		}
	}

	
	@Override
	public String toString(){
		String out = new String();
		out+=this.name;
		out+="\t";
		for (T item : contents){
			out+=item + " ";
		}
		return out;
	}
	
}