package be.ugent.psb.moduleviewer.model;

import java.awt.Color;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;

public class AnnotationBlockFactory {

	
	private Color color;
	private DataType type;
	private String blockName;
	private ModuleNetwork modnet;
	
	
	public AnnotationBlockFactory(String blockName, DataType type, ModuleNetwork modnet){
		this.blockName = blockName;
		this.type = type;
		this.modnet = modnet;
	}

	
	public AnnotationBlock createNewAnnotationBlock(){
		AnnotationBlock ab = new AnnotationBlock(blockName, modnet, type);
		ab.setColor(color);
		return ab;
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

	
}
