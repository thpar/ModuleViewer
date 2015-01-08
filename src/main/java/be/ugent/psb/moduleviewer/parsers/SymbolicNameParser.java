package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;

/**
 * A simple ID to Symbolic Name parser.
 * Retrieves genes by ID and sets their alias.
 * 
 * @author Thomas Van Parys
 *
 */
public class SymbolicNameParser extends Parser {

	private ModuleNetwork modnet;

	@Override
	public void parse(Model model, InputStream inputFile) throws IOException {
		this.modnet = model.getModnet();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputFile));
		
		String line = in.readLine();
		while (line!=null){
			try{
				parseLine(line);
			} catch(UnknownItemException e){
				//don't output unknown genes here
				//there will usualy be mappings for more genes than only the loaded ones
			}
			line = in.readLine();
		}
	}

	private void parseLine(String line) throws UnknownItemException{
		String[] cols = line.split("\\t");
		String geneId = cols[0];
		String symbolicName = cols[1];
		
		Gene gene = modnet.getGene(geneId);
		gene.setAlias(symbolicName);
	}

}
