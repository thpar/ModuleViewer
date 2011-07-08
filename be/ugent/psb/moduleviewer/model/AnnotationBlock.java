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
	public enum BlockType{
		bingo,
		conditionbingo,
		modulelinks,
		core,
		genecolorbox,
		regulatorcolorbox,
		tfenrichment,
		regulatorregulatorinteraction,
		regulatorgeneinteraction,
		genegeneinteraction,
		unknown;
		
		
		public static BlockType getValueOf(String name){
			String shortName = name.replaceAll("[ -]", "");
			try {
				BlockType bt = BlockType.valueOf(shortName);
				return bt;
			} catch (Exception e) {
				return BlockType.unknown;
			}
		}
	}
	private Map<String, Annotation<T>> annotations = new HashMap<String, Annotation<T>>();
	
	
	private String blockName;
	private BlockType blockType;
	private String unknownBlockType;
	private DataType dataType;
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
		this.dataType=type;
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

	/**
	 * The data type of an annotation block will be GENE or CONDITION
	 * @return
	 */
	public DataType getDataType() {
		return dataType;
	}
	
	
	
	@Override
	public String toString(){
		String out = new String();
		out+=this.dataType+" Block: "+this.blockName + System.getProperty("line.separator");
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

	/**
	 * The block type is data dependent. A few types have to be processed in their own way.
	 * @return
	 */
	public BlockType getBlockType() {
		return blockType;
	}

	public void setBlockType(BlockType blockType) {
		this.blockType = blockType;
	}
	
	
	public String getUnknownBlockType() {
		return unknownBlockType;
	}

	public void setUnknownBlockType(String unknownBlockType) {
		this.unknownBlockType = unknownBlockType;
	}

	/**
	 * Get the first annotation (probably the only one).
	 * @return
	 */
	public Annotation<T> getAnnotation(){
		return this.annotations.values().iterator().next();
	}
}
