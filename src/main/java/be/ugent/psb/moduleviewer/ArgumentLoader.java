package be.ugent.psb.moduleviewer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.ParseException;
import be.ugent.psb.moduleviewer.parsers.RegulatorTreeParser;

/**
 * Processes commandline and URL arguments
 * 
 * @author thpar
 *
 */
public class ArgumentLoader {
	
	private Model model;
	private Options options;

	
	
	public ArgumentLoader(Model model) {
		this.model = model;
		
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

		
		options.addOption("help", false, "print this message");
		options.addOption(expMatrix);
		options.addOption(modules);
		options.addOption(regs);
		options.addOption(synonyms);
		options.addOption(annotations);
				
	}

	public void processArguments(String[] args) throws org.apache.commons.cli.ParseException{
		CommandLineParser parser = new GnuParser();
		CommandLine line = parser.parse(this.options, args);
		
	}

	public void processURLS(List<URL> urls) throws IOException, ParseException{
		DataMatrixParser dmp = new DataMatrixParser();
		dmp.parse(model, urls.get(0).openStream());
		model.setDataFile(urls.get(0).getFile());

		GeneTreeParser gtp   = new GeneTreeParser();
		gtp.parse(model, urls.get(1).openStream());
		model.setGeneFile(urls.get(1).getFile());

		ConditionTreeParser ctp = new ConditionTreeParser();
		ctp.parse(model, urls.get(2).openStream());
		model.setConditionFile(urls.get(2).getFile());

		if (urls.size()>=4){
			int remLinks = 3;
			if (!(urls.get(remLinks).getFile().endsWith(".mvf") ||
					checkMagicWord(urls.get(remLinks).openStream(), "MVF"))){
				RegulatorTreeParser rtp = new RegulatorTreeParser();
				rtp.parse(model, urls.get(3).openStream());
				model.setRegulatorFile(urls.get(3).getFile());
				remLinks++;
			}
			while (remLinks<urls.size()){
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, urls.get(remLinks).openStream());
				model.addAnnotationFile(urls.get(remLinks).getFile());
				remLinks++;
			}
			
		}
	}
	
	
	/**
	 * Checks if the first line of the given InputStream contains the magic word, indicating 
	 * the file type without depending on a file extension.
	 * 
	 * @param stream
	 * @param magic
	 * @return
	 * @throws IOException
	 */
	private boolean checkMagicWord(InputStream stream, String magic) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line = in.readLine();
		String magicLine = line.substring(1);
		in.close();
		return magicLine.equalsIgnoreCase(magic);
	}
}
