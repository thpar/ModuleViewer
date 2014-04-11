package be.ugent.psb.moduleviewer.model;

import java.awt.Color;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.ValueType;

public class AnnotationBlockFactory {
	
	private int blockID;
	
	private Color color;
	private DataType type;
	private String title;
	private BlockType blockType;
	private String unknownBlockType;
	private ModuleNetwork modnet;
	
	private ValueType valueType = ValueType.NONE;

	private boolean global;

	private boolean hideLabels = false;

	private boolean hideBlockLabels = false;
	
	
	public AnnotationBlockFactory(int blockID, DataType type, ModuleNetwork modnet){
		this.blockID = blockID;
		this.type = type;
		this.modnet = modnet;
	}

	
	public AnnotationBlock<?> createNewAnnotationBlock(){
		switch(type){
		case GENES:
		case REGULATORS:
			AnnotationBlock<Gene> abg = new AnnotationBlock<Gene>(blockID, modnet, type, valueType);			
			abg.setColor(color);
			abg.setBlockType(blockType);
			abg.setUnknownBlockType(unknownBlockType);
			abg.setBlockTitle(title);
			abg.setHideBlockLabel(this.hideBlockLabels);
			abg.setHideLabels(this.hideLabels);
			return abg;
		case CONDITIONS:
			AnnotationBlock<Condition> abc = new AnnotationBlock<Condition>(blockID, modnet, type, valueType);
			abc.setColor(color);
			abc.setBlockType(blockType);
			abc.setUnknownBlockType(unknownBlockType);
			abc.setBlockTitle(title);
			abc.setHideBlockLabel(this.hideBlockLabels);
			abc.setHideLabels(this.hideLabels);
			return abc;
		default: return null;
		}
	}
	
	

	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public DataType getType() {
		return type;
	}


	public void setType(DataType type) {
		this.type = type;
	}


	public String getBlockTitle() {
		return title;
	}


	public void setTitle(String blockTitle) {
		this.title = blockTitle;
	}


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


	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
		
	}


	public ValueType getValueType() {
		return valueType;
	}


	public int getBlockID() {
		return blockID;
	}


	public void setGlobal(boolean b) {
		this.global = b;
	}
	
	public boolean isGlobal(){
		return this.global;
	}


	public void setHideLabels(boolean b) {
		this.hideLabels = b;
	}


	public boolean isHideLabels() {
		return hideLabels;
	}


	public void setHideBlockLabels(boolean b) {
		this.hideBlockLabels = b;
	}


	public boolean isHideBlockLabels() {
		return hideBlockLabels;
	}
	
	

	
	
}
