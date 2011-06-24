package be.ugent.psb.moduleviewer.model;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author thpar
 *
 */
public class AnnotationBlock<T> {
	
	/**
	 * Type of data we're annotating (Genes or Conditions)
	 * @author thpar
	 *
	 */
	public enum DataType{
		GENES, CONDITIONS;
	}
	
	
	private Map<String, Annotation<T>> annotations = new HashMap<String, Annotation<T>>();
	
	
	private String blockName;
	private String blockType;
	private DataType type;
	private ModuleNetwork modnet;
	
	/**
	 * Use one single color for all points of the annotation
	 */
	private Color color;


	private boolean itemSpecificColored = false;

	
	
	/**
	 * 
	 * @param blockName name of this block
	 * @param modnet Module Network for gene and condition lookups
	 */
	public AnnotationBlock(String blockName, ModuleNetwork modnet, DataType type){
		this.blockName = blockName;
		this.type=type;
		this.modnet = modnet;
	}
	
	public AnnotationBlock(String blockName, ModuleNetwork modnet, DataType type, boolean geneSpecificColored){
		this(blockName, modnet, type);
		this.itemSpecificColored = geneSpecificColored;
	}

	/**
	 * Add an annotation to this block 
	 * @param annot
	 */
	public void addAnnotation(Annotation<T> annot){
		this.annotations.put(annot.getName(), annot);
	}
	
	/**
	 * 
	 * @return name of this block
	 */
	public String getBlockName() {
		return blockName;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return the annotation list or null if none is found
	 */
	public Annotation<T> getAnnotation(String name){
		return annotations.get(name);
	}

	public DataType getType() {
		return type;
	}
	
	
	
	@Override
	public String toString(){
		String out = new String();
		out+=this.type+" Block: "+this.blockName + System.getProperty("line.separator");
		for (Annotation<?> an : annotations.values()){
			out+=an.toString() + System.getProperty("line.separator");
		}
		
		return out;
	}
	
	public Annotation<T> addNewAnnotation(String label){
		Annotation<T> annot;
		if (itemSpecificColored){
			annot = new ColoredAnnotation<T>(label, modnet);
		} else {
			annot = new Annotation<T>(label, modnet);
		}
		this.addAnnotation(annot);
		return annot;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor(){
		return color;
	}

	
	public Collection<Annotation<T>> getAnnotations(){
		return this.annotations.values();
	}
	
	public int size(){
		return this.annotations.size();
	}
	
	public boolean isItemSpecificColored(){
		return itemSpecificColored ;
	}

	public String getBlockType() {
		return blockType;
	}

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}
	
	
}
