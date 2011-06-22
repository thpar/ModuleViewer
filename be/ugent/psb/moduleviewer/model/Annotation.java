package be.ugent.psb.moduleviewer.model;

import java.util.Collection;
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
	
//	protected List<Color> colors = new ArrayList<Color>();

		
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
	

	
//	public void addItem(T item, Color color) throws UnknownItemException {
//		this.addItem(item);
//		this.colors.add(color);
//		
//	}
	
	public Set<T> getItems(){
		return contents;
	}
	
//	public List<Color> getColors(){
//		return colors;
//	}
	
//	public T getItemAt(int pos){
//		return contents.get(pos);
//	}
//	
//	public Color getColorAt(int pos){
//		return colors.get(pos);
//	}
	
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
