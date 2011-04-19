package be.ugent.psb.moduleviewer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author thpar
 *
 * @param <T> the class of annotation (most often {@link Gene} or {@link Condition})
 * @param <U> the class of continuous values linked to the items
 */
public class AnnotationBlock<T,U> {
	
	private Map<String, Annotation<T, U>> annotations = new HashMap<String, Annotation<T, U>>();
	private String blockName;
	private ModuleNetwork modnet;
	
	public AnnotationBlock(String blockName, ModuleNetwork modnet){
		this.blockName = blockName;
		this.modnet = modnet;
	}

	
	public void addAnnotation(Annotation<T, U> annot){
		this.annotations.put(annot.getName(), annot);
	}
	
	
	
	public String getBlockName() {
		return blockName;
	}
	
	public boolean hasAnnotation(String name){
		return annotations.containsKey(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return the annotation list or null if none is found
	 */
	public Annotation<T, U> getAnnotation(String name){
		return annotations.get(name);
	}
	
	
}
