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
public class AnnotationBlock {
	
	/**
	 * Type of data we're annotating (Genes or Conditions)
	 * @author thpar
	 *
	 */
	public enum DataType{
		GENES, CONDITIONS;
	}
	
	
	private Map<String, Annotation<?>> annotations = new HashMap<String, Annotation<?>>();
	private String blockName;
	private DataType type;
	private ModuleNetwork modnet;
	private Color color;

	
	
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

	/**
	 * Add an annotation to this block 
	 * @param annot
	 */
	public void addAnnotation(Annotation<?> annot){
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
	public Annotation<?> getAnnotation(String name){
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
	
	public Annotation<?> addNewAnnotation(String label){
		switch(type){
		case GENES:
			GeneAnnotation geneAnnot = new GeneAnnotation(label, modnet);
			this.addAnnotation(geneAnnot);
			return geneAnnot;
		case CONDITIONS:
			ConditionAnnotation condAnnot = new ConditionAnnotation(label, modnet);
			this.addAnnotation(condAnnot);
			return condAnnot;
		default: return null;
		}
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor(){
		return color;
	}

	
	public Collection<Annotation<?>> getAnnotations(){
		return this.annotations.values();
	}
	
	public int size(){
		return this.annotations.size();
	}
	
}
