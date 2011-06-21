package be.ugent.psb.moduleviewer.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Annotates a list of genes or conditions.
 * 
 * T : class of objects to be annotated
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
	protected List<T> contents = new ArrayList<T>();
	
	protected List<Color> colors = new ArrayList<Color>();

	
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
	
	
	
	abstract public void addItem(String itemId) throws UnknownItemException;
	
	public void addItem(String itemId, Color color) throws UnknownItemException {
		this.addItem(itemId);
		this.colors.add(color);
		
	}
	
	public List<T> getItems(){
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
	
}
