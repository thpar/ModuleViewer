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

/**
 * Read in a list of genes, formatted more or less in the MVF format.
 * 
 * module# \t gene_list
 * 
 * where gene_list is a pipe separated list of ATG names.
 * 
 * @author Thomas Van Parys
 *
 */
public class GeneListParser extends Parser {
	
	/**
	 * Separate genes from each other in a list
	 */
	private final String GENE_DELIMITER = "\\|";
	
	/**
	 * Indicate a comment line
	 */
	private final String COMMENT_SIGN = "#";
	
	public GeneListParser(ProgressListener progListener) {
		super(progListener);
	}
	
	@Override
	public void parse(Model model, InputStream stream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));

		String line = in.readLine();
		while (line!=null){
			if (line.startsWith(COMMENT_SIGN) || line.isEmpty()){
				//skip line
			} else {
				parseLine(line, model);
			}
			line = in.readLine();
		}
	}

	private void parseLine(String line, Model model) {
		String[] cols = line.split("\t");
		int modId = Integer.valueOf(cols[0]);
		String[] genes = cols[1].split(GENE_DELIMITER);
		
		ModuleNetwork modnet = model.getModnet();
		Module module = new Module(modnet, modId);
		
		GeneNode geneList = new GeneNode();
		geneList.setLeaf(true);
		for (String geneName : genes){
			try {
				Gene gene = modnet.getGene(geneName);
				geneList.addGene(gene);
			} catch (UnknownItemException e) {
				System.err.println(e);
			}
		}
		
		module.setGeneTree(geneList);
		modnet.addModule(module);
		
	}
	
}
