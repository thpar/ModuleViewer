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
		this.logger = model.getLogger();
		
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
