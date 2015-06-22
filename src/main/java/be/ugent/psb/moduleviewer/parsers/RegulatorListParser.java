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
 * Parser for a simple list of regulators per module
 * 
 * @author Thomas Van Parys
 *
 */
public class RegulatorListParser extends Parser{
	
	/**
	 * Separate genes from each other in a list
	 */
	private final String GENE_DELIMITER = "\\|";
	
	/**
	 * Indicate a comment line
	 */
	private final String COMMENT_SIGN = "#";
	
	public RegulatorListParser(){
		
	}
	
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
					logger.addEntry(e, "Unknown regulator gene");
				}
			}
			
			module.addRegulatorTree(regList);
		} catch (UnknownItemException e) {
			logger.addEntry(e, "Unknown module ID");
		}
		
	}

}
