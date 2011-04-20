package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneAnnotation;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


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
 * Known key examples:
 * ::TYPE=type
 * ::COLOR=red
 * ::SEP=:
 * ::LABELCOLOR=
 * 
 * TYPE defines the type of data we're reading. Can be used as a label for the 
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

	private File inputFile;
	
	/**
	 * Separated genes from eachother in a list
	 */
	private final String geneDelimiter = "|";
	
	/**
	 * If a gene is linked to a value, this character separated geneId and value
	 */
	private final String geneValueDelimiter = ":";
	
	
	private final String keyValueDelimiter = "=";

	private ModuleNetwork modnet;

	private Map<String, String> unknownParameters = new HashMap<String, String>();

	private AnnotationBlock<?, ?> currentBlock;
	
	private Class<?> currentItemClass;
	private Class<?> currentValueClass;
	

	
	enum ParamKey{
		TYPE, COLOR, SEP, LABELCOLOR;
	}
	
	
	@Override
	public void parse(Model model, File inputFile) throws IOException {
		this.modnet = model.getModnet();
		
		this.inputFile = inputFile;
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		
//		newBlock();
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith("#")) parseComment(line);
//			else if (line.isEmpty()) newBlock();
			else if (line.startsWith("::")) parseKeyValue(line);
			else parseEntry(line);
			in.readLine();
		}
		
	}

//	private void newBlock() {
//		
//		
//	}

	private void parseKeyValue(String line) {
		String[] keyValue = line.substring(2).split(keyValueDelimiter);
		String keyString = keyValue[0];
		String value = keyValue[1];
		
		try {
			ParamKey key = ParamKey.valueOf(keyString);
			switch(key){
			case TYPE:
				//TODO meh... switch on Gene/Condition here
				currentBlock = new AnnotationBlock(value, modnet);
				break;
			case COLOR:
				break;
			case SEP:
				break;
				
			}
		} catch (IllegalArgumentException e) {
			this.unknownParameters .put(keyString, value);
		}
	}

	private void parseEntry(String line) {
		String[] columns = line.split("\t");
		
		int moduleId = Integer.parseInt(columns[0]);
		String[] items = columns[1].split(geneDelimiter);
		String label   = columns[2];

		currentBlock.
//		Annotation<?,Double> annot = currentBlock.getAnnotation(label);
		if (annot == null){
			annot = new GeneAnnotation<Double>(label, modnet);
		}
		
		for (String it : items){
			String[] itemKeyValue = it.split(this.geneValueDelimiter);
			String itemId = itemKeyValue[0];
			Double itemValue = Double.valueOf(itemKeyValue[1]);
			
			annot.addItem(itemId, itemValue);
		}
		
		
		
	}

	private void parseComment(String line) {
		System.out.println(inputFile+" : "+line);
	}

}
