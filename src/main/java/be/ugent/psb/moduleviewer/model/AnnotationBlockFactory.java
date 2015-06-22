package be.ugent.psb.moduleviewer.model;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.Color;

import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.ValueType;

/**
 * Takes default values for creating new annotation blocks
 * 
 * @author Thomas Van Parys
 *
 */
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
