package be.ugent.psb.moduleviewer.testing;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.Parser;

public class ParserTest {
	
	public static void main(String args[]){
		Model model = new Model();
		
		File dataInput = new File(args[0]);
		File mvfInput = new File(args[1]);
		
		ModuleNetwork modnet = model.getModnet();
		modnet.addModule(new Module(modnet, 0));
		modnet.addModule(new Module(modnet, 1));
		modnet.addModule(new Module(modnet, 2));
		modnet.addModule(new Module(modnet, 3));
		modnet.addModule(new Module(modnet, 4));
		
		try {
			DataMatrixParser dmParser = new DataMatrixParser();
			dmParser.parse(model, dataInput);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			modnet.getModule(0).addGene("ATH1");
			modnet.getModule(0).addGene("ATH2");
			modnet.getModule(0).addGene("ATH3");
			modnet.getModule(0).addGene("ATH4");
			modnet.getModule(0).addGene("ATH6");
		} catch (UnknownItemException e1) {
			e1.printStackTrace();
		}
		
		
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
