package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import be.ugent.psb.moduleviewer.model.Model;


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

	@Override
	public void parse(Model model, File inputFile) throws IOException {
		this.inputFile = inputFile;
		
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith("#")) parseComment(line);
			else if (line.startsWith("::")) parseKeyValue(line);
			else parseEntry(line);
			in.readLine();
		}
		
	}

	private void parseKeyValue(String line) {
		String[] keyValue = line.substring(2).split(keyValueDelimiter);
		String key = keyValue[0];
		String value = keyValue[1];
		
		//TODO process key value pair
	}

	private void parseEntry(String line) {
		String[] columns = line.split("\t");
		
		int moduleId = Integer.parseInt(columns[0]);
		String[] genes = columns[1].split(geneDelimiter);
		//TODO process genes/values
		
		String label   = columns[2];
		
		// TODO process entry
	}

	private void parseComment(String line) {
		System.out.println(inputFile+" : "+line);
	}

}
