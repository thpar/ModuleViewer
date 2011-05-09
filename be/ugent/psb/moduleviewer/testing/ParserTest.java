package be.ugent.psb.moduleviewer.testing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.Parser;

public class ParserTest {
	
	public static void main(String args[]){
		Model model = new Model();
		
		File dataInput = new File(args[0]);
		File conditionXML = new File(args[1]);
		File mvfInput = new File(args[2]);
		
		ModuleNetwork modnet = model.getModnet();
		
		for (int i = 0; i<300; i++){
			modnet.addModule(new Module(modnet, i));
		}
		for (int i=0; i<280; i++){
			modnet.addCondition(String.valueOf(i));
		}
		
		try {
			DataMatrixParser dmParser = new DataMatrixParser();
			dmParser.parse(model, dataInput);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			ConditionTreeParser condParser = new ConditionTreeParser();
			condParser.parse(model, conditionXML);
			
			
			//test conditions
			try {
				Module mod0 = modnet.getModule(96);
				System.out.println("Testing module 0: "+mod0.getName());
				
				List<Condition> conds = mod0.getRootNode().getConditions();
				for (Condition cond : conds){
					System.out.print(cond);
					System.out.print(", ");
				}
				
				System.out.println();
				System.out.println();
				
				for (ITreeNode<Condition> l : mod0.getRootNode().getLeaves()){
					for (Condition col : l.getColumns()){
						System.out.print(col);
						System.out.print(", ");
					}
					System.out.println();
				}
				
				System.out.println();
				System.out.println();
			} catch (UnknownItemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Parser p = new MVFParser();
		try {
			p.parse(model, mvfInput);
			
			
			//test parsing result
			for (Module mod : modnet.getModules()){
				if (mod.getAnnotationBlocks().size() >0){
					System.out.println("Module "+mod.getId());
					for (AnnotationBlock ab : mod.getAnnotationBlocks()){
						System.out.println(ab);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
