package be.ugent.psb.moduleviewer.testing;

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

import java.io.File;

import be.ugent.psb.modulegraphics.elements.ITreeNode;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.Parser;

public class ParserTest {
	
	public static void main(String args[]){
		Model model = new Model();
		
		String dir = args[0];
		String sep = File.separator;
		
		File dataInput = new File(dir+sep+args[1]);
		File geneXML = new File(dir+sep+args[2]);
		File regulatorXML = new File(dir+sep+args[3]);
		File conditionXML = new File(dir+sep+args[4]);
		File mvfInput = new File(dir+sep+args[5]);
		
		ModuleNetwork modnet = model.getModnet();
		
		try {
			Parser dmParser = new DataMatrixParser();
			dmParser.parse(model, dataInput);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		

		try {
			Parser geneParser = new GeneTreeParser();
			geneParser.parse(model, geneXML);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Parser geneParser = new GeneTreeParser();
			geneParser.parse(model, regulatorXML);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Parser condParser = new ConditionTreeParser();
			condParser.parse(model, conditionXML);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		try {
			Parser p = new MVFParser();
			p.parse(model, mvfInput);	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		//test genes
		try {
			Module mod0 = modnet.getModule("44");
			System.out.println("Testing module 44 genes: "+mod0.getName());
			for (ITreeNode<Gene> l : mod0.getGeneTree().getLeaves()){
				for (Gene gene : l.getColumns()){
					System.out.print(gene);
					System.out.print(", ");
				}
				System.out.println();
			}

			System.out.println();
			System.out.println();
		} catch (UnknownItemException e) {
			e.printStackTrace();
		}
			
		//test conditions
		try {
			Module mod0 = modnet.getModule("18");
			System.out.println("Testing module 18 conditions: "+mod0.getName());


			for (ITreeNode<Condition> l : mod0.getConditionTree().getLeaves()){
				for (Condition col : l.getColumns()){
					System.out.print(col);
					System.out.print(", ");
				}
				System.out.println();
			}
			System.out.println("Non Tree Conditions");
			for (Condition cond : mod0.getNonTreeConditions()){
				System.out.print(cond);
				System.out.print(", ");
			}

			System.out.println();
			System.out.println();
		} catch (UnknownItemException e) {
			e.printStackTrace();
		}


		
		//test annotations
		
		try {
			Module mod0 = modnet.getModule("0");
			System.out.println("Annotation for module "+mod0.getId());
			for (AnnotationBlock<?> ab : mod0.getAnnotationBlocks()){
				System.out.println(ab);
			}
		} catch (UnknownItemException e) {
			e.printStackTrace();
		}
		
	}
}
