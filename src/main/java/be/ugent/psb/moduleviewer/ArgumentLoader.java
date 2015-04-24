package be.ugent.psb.moduleviewer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneListParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.ParseException;
import be.ugent.psb.moduleviewer.parsers.RegulatorListParser;
import be.ugent.psb.moduleviewer.parsers.RegulatorTreeParser;
import be.ugent.psb.moduleviewer.parsers.SymbolicNameParser;

/**
 * Processes commandline and URL arguments
 * 
 * @author thpar
 *
 */
public class ArgumentLoader {
	
	private Model model;
	private Options options;
	private GUIModel guiModel;

	
	
	public ArgumentLoader(Model model, GUIModel guiModel) {
		this.model = model;
		this.guiModel = guiModel;
		
		this.options = new Options();
		
		Option expMatrix = OptionBuilder.withArgName("matrix file")
					.hasArg()
					.withDescription("The expression matrix")
					.isRequired()
					.withLongOpt("expmatrix")
					.create('e');
		
		Option modules = OptionBuilder.withArgName("module file")
				.hasArg()
				.withDescription("Module file (XML or flat list)")
				.isRequired()
				.withLongOpt("modules")
				.create('m');
		
		Option regs = OptionBuilder.withArgName("regulator file")
				.hasArg()
				.withDescription("Regulator file (XML or flat list)")
				.withLongOpt("regulators")
				.create('r');

		Option conds = OptionBuilder.withArgName("condition file")
				.hasArg()
				.withDescription("Condition tree file")
				.withLongOpt("conditions")
				.create('c');
		
		Option synonyms = OptionBuilder.withArgName("synonym file")
				.hasArg()
				.withDescription("Gene ID - synonym mapping")
				.withLongOpt("synonyms")
				.create('s');
		
		Option annotations = OptionBuilder.withArgName("annotation file")
				.hasArgs()
				.withDescription("One or more MVF annotation files")
				.withLongOpt("annotations")
				.create('a');

		Option help = OptionBuilder
				.withDescription("Show this help")
				.withLongOpt("help")
				.create('h');
		
		
		options.addOption(help);
		options.addOption(expMatrix);
		options.addOption(modules);
		options.addOption(regs);
		options.addOption(conds);
		options.addOption(synonyms);
		options.addOption(annotations);
				
	}

	public void processArguments(String[] args) throws IOException, ParseException{
		CommandLineParser parser = new GnuParser();
		
		try {
			CommandLine line = parser.parse(this.options, args);
			
			loadData(line);
			
		} catch (org.apache.commons.cli.ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "ModuleViewer", options );
			System.exit(0);
		} 				
		
	}

	public void loadData(CommandLine line) throws IOException, ParseException{
		
		//expression matrix
		String expMatrix = line.getOptionValue("expmatrix");
		DataMatrixParser dmp = new DataMatrixParser();
		dmp.parse(model, createStream(expMatrix));
		model.setDataFile(expMatrix);			
		
		//modules and gene list
		String modules = line.getOptionValue("modules");
		InputStream moduleStream = createStream(modules);
		if (isXML(modules)){
			GeneTreeParser gtp = new GeneTreeParser();
			gtp.parse(model, moduleStream);
		} else {
			GeneListParser glp = new GeneListParser();
			glp.parse(model, moduleStream);
		}
		model.setGeneFile(modules);		

		
		//regulator files
		if (line.hasOption("regulators")){
			String regs = line.getOptionValue("regulators");
			InputStream regStream = createStream(regs);
			if (isXML(regs)){
				RegulatorTreeParser rtp = new RegulatorTreeParser();
				rtp.parse(model, regStream);
			} else {
				RegulatorListParser rlp = new RegulatorListParser();
				rlp.parse(model, regStream);
			}
			model.setRegulatorFile(regs);				
		}
		
		//condition files
		if (line.hasOption("conditions")){
			String conditions = line.getOptionValue("conditions");
			ConditionTreeParser ctp = new ConditionTreeParser();
			ctp.parse(model, createStream(conditions));
			model.setConditionFile(conditions);			
		}
		
		
		if (line.hasOption("annotations")){
			String[] annots = line.getOptionValues("annotations");
			for (String annot : annots){
				InputStream annotStream = createStream(annot);
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, annotStream);
				model.addAnnotationFile(annot);
			}
		}
		
		//synonym file
		if (line.hasOption("synonyms")){
			String syns = line.getOptionValue("synonyms");
			SymbolicNameParser snp = new SymbolicNameParser();
			snp.parse(model, createStream(syns));
			model.setSymbolMappingFile(syns);
		}
		
	}
	
	public InputStream createStream(String input) throws IOException{
		InputStream inputStream;
		if (isURL(input)){
			inputStream = new URL(input).openStream();
		} else {
			inputStream = new FileInputStream(input);
		}
		return inputStream;
	}
	
	public boolean isURL(String URI){
		return URI.startsWith("http://") || URI.startsWith("ftp://");
	}
	
	public boolean isXML(String URI){
		return URI.endsWith(".xml") || URI.endsWith(".XML");
	}
	
}
