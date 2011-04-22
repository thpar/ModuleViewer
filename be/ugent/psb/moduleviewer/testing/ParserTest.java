package be.ugent.psb.moduleviewer.testing;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.Parser;

public class ParserTest {
	
	public static void main(String args[]){
		Model model = new Model();
		
		File input = new File(args[0]);
		
		ModuleNetwork modnet = model.getModnet();
		modnet.addModule(new Module(modnet, 0));
		modnet.addModule(new Module(modnet, 1));
		modnet.addModule(new Module(modnet, 2));
		modnet.addModule(new Module(modnet, 3));
		modnet.addModule(new Module(modnet, 4));
		
		
		
		Parser p = new MVFParser();
		try {
			p.parse(model, input);
			
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
