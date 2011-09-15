package be.ugent.psb.moduleviewer.model;

import java.awt.Color;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.ValueType;

public class AnnotationBlockFactory {

	
	private Color color;
	private DataType type;
	private String blockName;
	private BlockType blockType;
	private String unknownBlockType;
	private ModuleNetwork modnet;
	
	private ValueType valueType;
	
	
	public AnnotationBlockFactory(String blockName, DataType type, ModuleNetwork modnet){
		this.blockName = blockName;
		this.type = type;
		this.modnet = modnet;
	}

	
	public AnnotationBlock<?> createNewAnnotationBlock(){
		switch(type){
		case GENES:
			AnnotationBlock<Gene> abg = new AnnotationBlock<Gene>(blockName, modnet, type, valueType);			
			abg.setColor(color);
			abg.setBlockType(blockType);
			abg.setUnknownBlockType(unknownBlockType);
			return abg;
		case CONDITIONS:
			AnnotationBlock<Condition> abc = new AnnotationBlock<Condition>(blockName, modnet, type, valueType);
			abc.setColor(color);
			abc.setBlockType(blockType);
			abc.setUnknownBlockType(unknownBlockType);
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


	public String getBlockName() {
		return blockName;
	}


	public void setBlockName(String blockName) {
		this.blockName = blockName;
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
	
	

	
	
}
