package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;

public class RegulatorListParser extends Parser{
	
	/**
	 * Separate genes from each other in a list
	 */
	private final String GENE_DELIMITER = "\\|";
	
	/**
	 * Indicate a comment line
	 */
	private final String COMMENT_SIGN = "#";
	
	
	public RegulatorListParser(ProgressListener progListener) {
		super(progListener);
	}

	@Override
	public void parse(Model model, InputStream stream) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		this.logger = model.getLogger();
		
		String line = in.readLine();
		while (line!=null){
			if (line.startsWith(COMMENT_SIGN) || line.isEmpty()){
				//skip line
			} else {
				String[] cols = line.split("\t");
				if (cols.length==2 && !cols[1].isEmpty()){
					parseLine(cols, model);					
				}
			}
			line = in.readLine();
		}
		in.close();
	}
	
	private void parseLine(String[] cols, Model model) {
		String modId = cols[0];
		String[] regulators = cols[1].split(GENE_DELIMITER);
		
		ModuleNetwork modnet = model.getModnet();
		
		try {
			Module module = modnet.getModule(modId);
			
			GeneNode regList = new GeneNode();
			regList.setLeaf(true);
			for (String regName : regulators){
				try {
					Gene gene = modnet.getGene(regName);
					regList.addGene(gene);
				} catch (UnknownItemException e) {
					System.err.println(e);
				}
			}
			
			module.addRegulatorTree(regList);
		} catch (UnknownItemException e) {
			e.printStackTrace();
		}
		
	}

}
