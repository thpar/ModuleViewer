package be.ugent.psb.moduleviewer.parsers;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import be.ugent.psb.modulegraphics.elements.ColorFactory;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.ValueType;
import be.ugent.psb.moduleviewer.model.AnnotationBlockFactory;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.NumberedAnnotation;
import be.ugent.psb.moduleviewer.model.UnknownItemException;



/**
 * Parses files of the .mvf type, the native ModuleViewer Format.
 * 
 * Format:
 * 
 * Start block:
 * List of key value pairs. One pair per line. A key value line starts with "::". 
 * Key and value are separated by "=".
 * Keys are in capitals.
 * 
 * 
 * ======== MVF specs below are not up to date! =========
 * 
 * Known key examples:
 * ::TYPE=type
 * ::COLOR=red
 * ::LABELCOLOR=
 * ::LEGEND=
 * 
 * OBJECT defines the type of data we're reading. Can be used as a label for the 
 * matrix that is drawn.
 * 
 * COLOR is an optional setting to tell the viewer which color to use when displaying this data.
 * 
 * VALUE_SEPARATOR optional separator value. When set, each gene entry from a list will be considered to exist of GeneID<SEP>value
 * 
 * LABELCOLOR
 * 
 * The rest of the lines of a block are tab delimited:
 * Col 0 : ModuleId
 * Col 1 : Gene list, pipe delimited.
 * Col 2 : Optional. Describes which set this list of genes belongs to (a GO term, etc...) When empty, the TYPE value will be
 * used.
 * 
 * @author thpar
 *
 */
public class MVFParser extends Parser {


	/**
	 * Separate genes from each other in a list
	 */
	private final String geneDelimiter = "\\|";
	
	/**
	 * If a gene is linked to a value, this character separated geneId and value
	 */
	private static final String DEFAULT_GENE_VALUE_SEPARATOR = ":";
	
	
	private final String keyValueDelimiter = "=";

	private ModuleNetwork modnet;

	private Map<String, String> unknownParameters = new HashMap<String, String>();

	private Map<ParamKey, String> params;
	
	
	private AnnotationBlockFactory abf;


	private ParsingType parsingMode;

	private String parseGeneValuesSeparator = DEFAULT_GENE_VALUE_SEPARATOR;

	private List<String> legendLines = new ArrayList<String>();
	
	/**
	 * Keys that can be used in the MVF format.
	 * @author thpar
	 *
	 */
	enum ParamKey{
		TITLE,   //specific block name
		TYPE,       //Indicates the type of annotation. Will be used as template for name when no name is given.
		COLOR,      //suggests a color for the annotation matrix
		LABELCOLOR, //?? 
		OBJECT,     //GENES, CONDITIONS or REGULATORS (defines what kind of object is being annotated)
		VALUES,		//either none, color, or number: the values linked to individual genes
		VALUE_SEPARATOR, //by default ":", the separator between gene and value
		GLOBAL,    //indicates that this block does not have the module column.
		LABELS,    //"ON" by default. "OFF" hides the individual annotation labels for this block 
		BLOCKLABELS, //"ON" by default. "OFF" hides the block label for this block 
		LEGEND
	}
	
	/**
	 * Part of the MVF file
	 * 
	 * @author thpar
	 *
	 */
	enum ParsingType{
		KEYVALUE, ENTRY;
	}
	
	
	
	@Override
	public void parse(Model model, InputStream stream) throws IOException {
		this.modnet = model.getModnet();
		this.logger = model.getLogger();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith("#")){
				parseComment(line);
			} else if (line.isEmpty()){
				//empty line
			} else if (line.startsWith("::")) {
				parseKeyValue(line);
			} else {
				try {
					parseEntry(line);
				} catch (LineParseException e) {
					logger.addEntry(e, "Skipping line");
				}
			}
			
			line = in.readLine();
		}
		
		
	}

	/**
	 * line began with "::", indicating a parameter key value pair.
	 * 
	 * @param line
	 */
	private void parseKeyValue(String line) {
		if (parsingMode!=ParsingType.KEYVALUE){
			parsingMode = ParsingType.KEYVALUE;
			params = new HashMap<ParamKey, String>();
			legendLines = new ArrayList<String>();
			unknownParameters = new HashMap<String, String>();
			parseGeneValuesSeparator = DEFAULT_GENE_VALUE_SEPARATOR;
		}
		
		String[] keyValue = line.substring(2).split(keyValueDelimiter);
		String keyString = keyValue[0];
		String value;
		if (keyValue.length>=2){
			value = keyValue[1];			
		} else {
			value = "true";
		}
		
		try {
			ParamKey pk = ParamKey.valueOf(keyString);
			if (pk==ParamKey.LEGEND){
				legendLines.add(value);
			} else {
				params.put(pk, value);				
			}
		} catch (IllegalArgumentException e) {
			logger.addEntry(e,"Ignored unknown parameter: "+ keyString);
			unknownParameters.put(keyString, value);
		}
	}
	
	private void processParams(){
		//get object type
		String objectTypeString = params.get(ParamKey.OBJECT);
		DataType objectType; 
		if (objectTypeString==null){
			objectType=DataType.GENES;
		} else {
			try {
				objectType=DataType.valueOf(objectTypeString);
			} catch (IllegalArgumentException e) {
				//fallback to default
				objectType=DataType.GENES;
				logger.addEntry("Falling back to default type GENES. Unknown type: "+objectTypeString);
			}
		}
		
		
		//create factory for annotation blocks 
		int blockID = modnet.getNextAnnotationBlockID();
		abf = new AnnotationBlockFactory(blockID, objectType, modnet);
		modnet.incrementNextAnnotationBlockID();
		
		
		
		//add other options to the block factory
		for (Entry<ParamKey, String> pe : params.entrySet()){
			ParamKey key = pe.getKey();
			String value = pe.getValue();
			switch(key){
			case TITLE:
				abf.setTitle(value);
				break;
			case TYPE:
				BlockType bt = BlockType.getValueOf(value);
				abf.setBlockType(bt);
				if (bt == BlockType.unknown){
					abf.setUnknownBlockType(value);
				}
				break;
			case COLOR:
				Color c = ColorFactory.decodeColor(value);
				abf.setColor(c);
				break;
			case LABELCOLOR:
				break;
			case VALUE_SEPARATOR:
				this.parseGeneValuesSeparator = value;
				break;
			case VALUES:
				ValueType valueType = ValueType.getValueOf(value);
				abf.setValueType(valueType);
				break;
			case GLOBAL:
				abf.setGlobal(true);
				break;
			case LABELS:
				if (value=="OFF"){
					abf.setHideLabels(true);
				}
				break;
			case BLOCKLABELS:
				if (value=="OFF"){
					abf.setHideBlockLabels(true);
				}
				break;
			case OBJECT:
			default:
				break;
			}
		}
		
		//add legend lines to the module network
		for (String legendLine : legendLines){
			String[] legendCols = legendLine.split("\t");
			String keyValueString = legendCols[0];
			String label = legendCols[1];
			String[] keyValues = keyValueString.split(geneDelimiter);
			for (String keyValue : keyValues){
				String[] keyValueArray = keyValue.split(parseGeneValuesSeparator);
				String key = keyValueArray[0];
				Color color = ColorFactory.decodeColor(keyValueArray[1]);
				modnet.addLegend(blockID, label, key, color);
			}
		}
	}



	private void parseEntry(String line) throws LineParseException{
		//if this is the first entry after parsing key values,
		//first process the gathered key value pairs.
		if (parsingMode==ParsingType.KEYVALUE){
			processParams();
			parsingMode=ParsingType.ENTRY;
		}
		
		//now work on the entry itself
		String[] columns = line.split("\t");
		
		String modId = null;
		String[] items;
		int labelCol;
		int itemCol;
		String label;
		
		if (abf.isGlobal()){
			itemCol = 0;
			labelCol = 1;
		} else {
			modId = columns[0];
			itemCol = 1;
			labelCol = 2;
		}
		
		if (columns.length>=itemCol+1){
			items = columns[itemCol].split(geneDelimiter);			
		} else {
			items = new String[0];
		}
		
		if (columns.length>=labelCol+1){
			label = columns[labelCol];			
		} else {
			if (abf.getBlockType()!=BlockType.unknown){
				label = abf.getBlockType().toString();
			} else {
				label = abf.getUnknownBlockType();
			}
		}
		
		
		try {
			AnnotationBlock<?>  ab;
			if (abf.isGlobal()){
				ab = modnet.getGlobalAnnotationBlock(abf.getBlockID());
				if (ab==null){
					ab = abf.createNewAnnotationBlock();
					modnet.addGlobalAnnotationBlock(ab);
				}
			} else {
				Module mod = modnet.getModule(modId);				
				ab = mod.getAnnotationBlock(abf.getBlockID());
				if (ab==null){
					ab = abf.createNewAnnotationBlock();
					mod.addAnnotationBlock(ab);
				}
			}
			
			Annotation<?> annot = ab.getAnnotation(label);
			if (annot==null){
				annot = ab.addNewAnnotation(label);
			}
			
			try {
				for (String it : items){
					//don't bother with empty items
					if (it.isEmpty()){
						continue;
					}
					switch(abf.getValueType()){
					case COLOR:
						String[] itemKeyValue = it.split(this.parseGeneValuesSeparator);
						String itemId = itemKeyValue[0];
						Color geneColor = ColorFactory.decodeColor(itemKeyValue[1]);
						switch(ab.getDataType()){
						case GENES:
						case REGULATORS:
							Gene geneItem = modnet.getGene(itemId);
							((ColoredAnnotation<Gene>)annot).addItem(geneItem, geneColor);	
							break;
						case CONDITIONS:
							Condition condItem = modnet.getCondition(itemId);
							((ColoredAnnotation<Condition>)annot).addItem(condItem, geneColor);	
							break;
						}
						break;
					case NONE:
						switch(ab.getDataType()){
						case GENES:
						case REGULATORS:
							Gene geneItem = modnet.getGene(it);
							((Annotation<Gene>)annot).addItem(geneItem);	
							break;
						case CONDITIONS:
							Condition condItem = modnet.getCondition(it);
							((Annotation<Condition>)annot).addItem(condItem);	
							break;
						}
						break;
					case NUMBER:
						String[] itemKeyValueNumber = it.split(this.parseGeneValuesSeparator);
						String itemGeneId = itemKeyValueNumber[0];
						int geneNumber = Integer.valueOf(itemKeyValueNumber[1]);
						switch(ab.getDataType()){
						case GENES:
						case REGULATORS:
							Gene geneItem = modnet.getGene(itemGeneId);
							((NumberedAnnotation<Gene>)annot).addItem(geneItem, geneNumber);	
							break;
						case CONDITIONS:
							Condition condItem = modnet.getCondition(itemGeneId);
							((NumberedAnnotation<Condition>)annot).addItem(condItem, geneNumber);	
							break;
						}
						break;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e ){
				throw new LineParseException(e);
			} catch (NumberFormatException e) {
				throw new LineParseException(e);
			} catch (UnknownItemException e) {
				throw new LineParseException(e);
			}
		} catch (UnknownItemException e) {
			e.printStackTrace();
		}
		
	}


	private void parseComment(String line) {
		//nothing interesting, often column headers
	}


}
