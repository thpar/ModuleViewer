package be.ugent.psb.moduleviewer.testing;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.Parser;

public class ParserTest {
	
	public static void main(String args[]){
		Model model = new Model();
		
		File input = new File(args[0]);
		
		Parser p = new MVFParser();
		
		try {
			p.parse(model, input);
			
			for (Module mod : model.getModnet().getModules()){
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
