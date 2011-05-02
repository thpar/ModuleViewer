package be.ugent.psb.moduleviewer.parsers;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.AnnotationBlockFactory;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
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

	
	/**
	 * Separated genes from eachother in a list
	 */
	private final String geneDelimiter = "\\|";
	
	/**
	 * If a gene is linked to a value, this character separated geneId and value
	 */
	private final String geneValueDelimiter = ":";
	
	
	private final String keyValueDelimiter = "=";

	private ModuleNetwork modnet;

	private Map<String, String> unknownParameters = new HashMap<String, String>();

	private AnnotationBlockFactory abf;

	private File inputFile;

	
	/**
	 * Keys that can be used in the MVF format.
	 * @author thpar
	 *
	 */
	enum ParamKey{
		TYPE, COLOR, SEP, LABELCOLOR;
	}
	
	
	
	@Override
	public void parse(Model model, File inputFile) throws IOException {
		System.out.println("Parsing "+inputFile);
		this.modnet = model.getModnet();
		this.inputFile = inputFile;
		
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith("#")) parseComment(line);
			else if (line.isEmpty());
			else if (line.startsWith("::")) parseKeyValue(line);
			else parseEntry(line);
			
			line = in.readLine();
		}
		
	}

	private void parseKeyValue(String line) {
		String[] keyValue = line.substring(2).split(keyValueDelimiter);
		String keyString = keyValue[0];
		String value = keyValue[1];
		
		try {
			ParamKey key = ParamKey.valueOf(keyString);
			switch(key){
			case TYPE:
				this.abf = new AnnotationBlockFactory(inputFile.getAbsolutePath(), DataType.valueOf(value), modnet); 
				break;
			case COLOR:
//				this.currentColor = Color.PINK;
				break;
			case SEP:
				break;
				
			}
		} catch (IllegalArgumentException e) {
			this.unknownParameters .put(keyString, value);
		}
	}

	private void parseEntry(String line){
		String[] columns = line.split("\t");
		
		int modId = Integer.parseInt(columns[0]);
		
		String[] items = columns[1].split(geneDelimiter);
		String label   = columns[2];

		
		try {
			Module mod = modnet.getModule(modId);
			AnnotationBlock ab = mod.getAnnotationBlock(abf.getBlockName());
			if (ab==null){
				ab = abf.createNewAnnotationBlock();
				mod.addAnnotationBlock(ab);
			}
			

			Annotation<?> annot = ab.getAnnotation(label);
			if (annot==null){
				annot = ab.addNewAnnotation(label);
				ab.addAnnotation(annot);
			}
			
			try {
				for (String it : items){
					String[] itemKeyValue = it.split(this.geneValueDelimiter);
					String itemId = itemKeyValue[0];
					if (itemKeyValue.length >=2){
						Double itemValue = Double.valueOf(itemKeyValue[1]);				
						annot.addItem(itemId, itemValue);
					} else {
						annot.addItem(itemId);
					}
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void parseComment(String line) {
		System.out.println(line);
	}


}
