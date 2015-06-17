package be.ugent.psb.moduleviewer.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * Parses the initial expression matrix.
 * 
 * Format:
 * 
 * line 1 : tab delimited list of conditions (ids) col 0 and 1 are headers and can be skipped.
 * other lines: tab delimited list: 
 * column 0: gene id
 * column 1: optional description
 * column 2 and further: values in same order as conditions
 * 
 * @author thpar
 *
 */
public class DataMatrixParser extends Parser {

	
	
	public DataMatrixParser() {
		super();
	}

	public DataMatrixParser(ProgressListener progListener) {
		super(progListener);
	}

	@Override
	public void parse(Model model, InputStream input) throws IOException {
		ModuleNetwork modnet = model.getModnet();
		this.logger = model.getLogger();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(input));

		String conditionLine = in.readLine();
		String[] conditionIds = conditionLine.split("\\t");
		
		int numCond = conditionIds.length - 2;
		for (int i=2; i<conditionIds.length; i++){
			modnet.addCondition(conditionIds[i]);
		}
		//make a single leaf node of the condition list
		List<Condition> condList = modnet.getConditionList();
		ConditionNode condNode = new ConditionNode();
		condNode.setLeaf(true);
		condNode.setConditions(condList);
		modnet.setConditionTree(condNode);
				
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
				} catch (NumberFormatException e) {
					geneData[j] = Double.NaN;
				}
			}
			newGene.setData(geneData);
			
			line = in.readLine();
		}
		in.close();
	}


}
