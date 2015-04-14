package be.ugent.psb.moduleviewer.testing;

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
