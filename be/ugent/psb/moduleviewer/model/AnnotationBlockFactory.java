package be.ugent.psb.moduleviewer.model;

import java.awt.Color;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;

public class AnnotationBlockFactory {

	
	private Color color;
	private DataType type;
	private String blockName;
	private ModuleNetwork modnet;
	
	private boolean geneSpecificColored = false;
	
	
	public AnnotationBlockFactory(String blockName, DataType type, ModuleNetwork modnet){
		this.blockName = blockName;
		this.type = type;
		this.modnet = modnet;
	}

	
	public AnnotationBlock<?> createNewAnnotationBlock(){
		switch(type){
		case GENES:
			AnnotationBlock<Gene> abg = new AnnotationBlock<Gene>(blockName, modnet, type, geneSpecificColored);			
			abg.setColor(color);
			return abg;
		case CONDITIONS:
			AnnotationBlock<Condition> abc = new AnnotationBlock<Condition>(blockName, modnet, type, geneSpecificColored);
			abc.setColor(color);
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


	public boolean isGeneSpecificColored() {
		return geneSpecificColored;
	}


	public void setGeneSpecificColored(boolean geneSpecificColored) {
		this.geneSpecificColored = geneSpecificColored;
	}

	
	
}
