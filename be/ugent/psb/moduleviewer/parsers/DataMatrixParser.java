package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * Parses the initial expression matrix.
 * 
 * Format:
 * 
 * line 1 : tab delimited list of conditions (ids)
 * other lines: tab delimited list: 
 * column 0: gene id
 * column 1: optional description
 * column 2 and further: values in same order as conditions
 * 
 * @author thpar
 *
 */
public class DataMatrixParser extends Parser {

	@Override
	public void parse(Model model, File inputFile) throws IOException {
		ModuleNetwork modnet = model.getModnet();
		
		BufferedReader in = new BufferedReader(new FileReader(inputFile));

		String conditionLine = in.readLine();
		String[] conditionIds = conditionLine.split("\\t");
		int numCond = conditionIds.length;
		for (String condId : conditionIds){
			modnet.addCondition(condId);
		}

		String line = in.readLine();
		while (line!=null){
			String[] columns = line.split("\\t");
			double[] geneData = new double[numCond];

			String geneId = columns[0];
			String descr  = columns[1];
			
			Gene newGene;
			if (descr==null || descr=="null" || descr.isEmpty()){
				newGene = new Gene(geneId);				
			} else {
				newGene = new Gene(geneId, descr);							
			}
			modnet.addGene(newGene);

			for (int j = 0; j < numCond; j++) {
				try {
					geneData[j] = Double.parseDouble(columns[j + 2]);
				} catch (InputMismatchException e) {
					geneData[j] = Double.NaN;
				}
			}
			newGene.setData(geneData);
			
			line = in.readLine();
		}

	}


}
