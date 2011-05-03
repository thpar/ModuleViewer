package be.ugent.psb.moduleviewer.testing;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.model.AnnotationBlock;
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
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
//		try {
//			modnet.getModule(0).addGene("ATH1");
//			modnet.getModule(0).addGene("ATH2");
//			modnet.getModule(0).addGene("ATH3");
//			modnet.getModule(0).addGene("ATH4");
//			modnet.getModule(0).addGene("ATH6");
//		} catch (UnknownItemException e1) {
//			e1.printStackTrace();
//		}
		
		
		Parser p = new MVFParser();
		try {
			p.parse(model, mvfInput);
			
			for (Module mod : modnet.getModules()){
				System.out.println("Module "+mod.getId());
				for (AnnotationBlock ab : mod.getAnnotationBlocks()){
					System.out.println(ab);
					System.out.println("--");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
