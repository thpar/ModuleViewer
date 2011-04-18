package be.ugent.psb.moduleviewer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ModuleViewer {

	private static final String SLASH = System.getProperty("file.separator");

	
	public static void main(String[] args) {
		//we get a properties file as input
		if (args.length>0){
			Properties props = new Properties();
			try {
				props.load(new FileReader(args[0]));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ViewerGUI figGen = new ViewerGUI();

			figGen.startGUI();

		
	}

}
