package be.ugent.psb.moduleviewer.parsers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
 * 
 * OBJECT defines the type of data we're reading. Can be used as a label for the 
 * matrix that is drawn.
 * 
 * COLOR is an optional setting to tell the viewer which color to use when displaying this data.
 * 
 * SEP optional separator value. When set, each gene entry from a list will be considered to exist of GeneID<SEP>value
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
	 * Separated genes from eachother in a list
	 */
	private final String geneDelimiter = "\\|";
	
	/**
	 * If a gene is linked to a value, this character separated geneId and value
	 */
	private static final String DEFAULT_GENE_VALUE_SEPARATOR = ":";

	private static final String BLOCK = "block";
	
	
	private final String keyValueDelimiter = "=";

	private ModuleNetwork modnet;

	private Map<String, String> unknownParameters = new HashMap<String, String>();

	private Map<ParamKey, String> params;
	
	
	private AnnotationBlockFactory abf;


	private ParsingType parsingMode;

	private int blockID;

	private String parseGeneValuesSeparator = DEFAULT_GENE_VALUE_SEPARATOR; 
	
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
		OBJECT,     //GENES of CONDITIONS (defines what kind of object is being annotated
		VALUES,		//either none, color, or number: the values linked to individual genes
		VALUE_SEPARATOR //by default ":", the separator between gene and value
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
//		System.out.println("Parsing "+inputFile);
		this.modnet = model.getModnet();
//		this.inputFile = inputFile;
		blockID=0;
		
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith("#")) parseComment(line);
			else if (line.isEmpty());
			else if (line.startsWith("::")) parseKeyValue(line);
			else {
				try {
					parseEntry(line);
				} catch (LineParseException e) {
					System.err.println(e.getMessage());
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
			unknownParameters = new HashMap<String, String>();
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
			params.put(pk, value);
		} catch (IllegalArgumentException e) {
			System.err.println("Ignored unknown parameter: "+ keyString);
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
				objectType=DataType.GENES;
			}
		}
		
		
		//create factory for annotation blocks 
		abf = new AnnotationBlockFactory(blockID++, objectType, modnet);
		
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
			case VALUES:
				ValueType valueType = ValueType.getValueOf(value);
				abf.setValueType(valueType);
				break;
			case OBJECT:
			default:
				break;
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
		
		int modId = Integer.parseInt(columns[0]);
		
		String[] items = columns[1].split(geneDelimiter);
		String label;
		if (columns.length>=3){
			label = columns[2];			
		} else {
			if (abf.getBlockType()!=BlockType.unknown){
				label = abf.getBlockType().toString();
			} else {
				label = abf.getUnknownBlockType();
			}
		}

		
		try {
			Module mod = modnet.getModule(modId);
			AnnotationBlock<?> ab = mod.getAnnotationBlock(abf.getBlockID());
			if (ab==null){
				ab = abf.createNewAnnotationBlock();
				mod.addAnnotationBlock(ab);
			}
			

			
			Annotation<?> annot = ab.getAnnotation(label);
			if (annot==null){
				annot = ab.addNewAnnotation(label);
			}
			
			try {
				for (String it : items){
					switch(abf.getValueType()){
					case COLOR:
						String[] itemKeyValue = it.split(this.parseGeneValuesSeparator);
						String itemId = itemKeyValue[0];
						Color geneColor = new Color(Integer.valueOf(itemKeyValue[1]));
						switch(ab.getDataType()){
						case GENES:
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
//		System.out.println(line);
	}


}
