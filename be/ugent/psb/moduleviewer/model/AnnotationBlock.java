package be.ugent.psb.moduleviewer.model;

import java.util.HashMap;
import java.util.Map;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;

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
		GENE, CONDITION;
	}
	/**
	 * Type of values that can come with the data
	 * @author thpar
	 *
	 */
	public enum ValueType{
		NONE, DOUBLE, INT, STRING;
	}
	
	
	private Map<String, Annotation<?>> annotations = new HashMap<String, Annotation<?>>();
	private String blockName;
	private DataType type;
	
	
	/**
	 * 
	 * @param blockName name of this block
	 * @param modnet Module Network for gene and condition lookups
	 */
	public AnnotationBlock(String blockName, DataType type){
		this.blockName = blockName;
		this.type=type;
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
		out+="Block: "+this.blockName + System.getProperty("line.separator");
		out+=this.type + System.getProperty("line.separator");
		for (Annotation<?> an : annotations.values()){
			out+=an.toString() + System.getProperty("line.separator");
		}
		
		return out;
	}
	
}
