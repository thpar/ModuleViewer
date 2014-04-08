package be.ugent.psb.moduleviewer.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		GENES, CONDITIONS, REGULATORS;
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
	/**
	 * Kind of values linked to the genes
	 * @author thpar
	 *
	 */
	public enum ValueType{
		NUMBER, COLOR, NONE;
		
		public static ValueType getValueOf(String name){
			return valueOf(name.toUpperCase());
		}
	}
	
	private Map<String, Annotation<T>> annotations = new HashMap<String, Annotation<T>>();
	private List<String> labelOrder = new ArrayList<String>();
	
	private int blockID;
	private String blockTitle;
	private BlockType blockType;
	private String unknownBlockType;
	private DataType dataType;
	private ModuleNetwork modnet;
	
	/**
	 * Use one single color for all points of the annotation
	 */
	private Color color;


//	private boolean itemSpecificColored = false;


	private ValueType valueType;

	
	
	/**
	 * 
	 * @param blockName name of this block
	 * @param modnet Module Network for gene and condition lookups
	 */
	public AnnotationBlock(int blockID, ModuleNetwork modnet, DataType type){
		this.blockID = blockID;
		this.dataType=type;
		this.modnet = modnet;
		
	}
	
	public AnnotationBlock(int blockID, ModuleNetwork modnet, DataType type, ValueType valueType){
		this(blockID, modnet, type);
		this.valueType = valueType;
	}

	/**
	 * Add an annotation to this block 
	 * @param annot
	 */
	public void addAnnotation(Annotation<T> annot){
		this.annotations.put(annot.getName(), annot);
		this.labelOrder.add(annot.getName());
	}
	
	/**
	 * 
	 * @return name of this block
	 */
	public String getBlockName() {
		return blockTitle;
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
		out+=this.dataType+" Block: "+this.blockTitle + System.getProperty("line.separator");
		for (Annotation<?> an : annotations.values()){
			out+=an.toString() + System.getProperty("line.separator");
		}
		
		return out;
	}
	
	public Annotation<T> addNewAnnotation(String label){
		Annotation<T> annot = null;
		switch(valueType){
		case COLOR:
			annot = new ColoredAnnotation<T>(label, modnet);
			break;
		case NONE:			
			annot = new Annotation<T>(label, modnet);
			break;
		case NUMBER:
			annot = new NumberedAnnotation<T>(label, modnet);
			break;
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

	
	public List<Annotation<T>> getAnnotations(){
		List<Annotation<T>> list = new ArrayList<Annotation<T>>();
		for (String label : labelOrder){
			list.add(annotations.get(label));
		}
		return list;
	}
	
	public int size(){
		return this.annotations.size();
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

	
	public ValueType getValueType() {
		return valueType;
	}

	/**
	 * Get the first annotation (probably the only one).
	 * @return
	 */
	public Annotation<T> getAnnotation(){
		return this.annotations.get(this.labelOrder.get(0));
	}

	public String getBlockTitle() {
		return blockTitle;
	}

	public void setBlockTitle(String blockTitle) {
		this.blockTitle = blockTitle;
	}

	public int getBlockID() {
		return blockID;
	}
	
	
}
