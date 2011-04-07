package be.ugent.psb.graphicalmodule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {

	private static final String SLASH = System.getProperty("file.separator");

	
	public static void main(String[] args) {
		//we get a properties file as input
		if (args.length>0){
			Properties props = new Properties();
			try {
				props.load(new FileReader(args[0]));
				
				dataDir = props.getProperty("dataDir");
				outputDir = props.getProperty("outputDir");
				xmlInput = dataDir+SLASH+props.getProperty("xmlInput");
				nameMap = dataDir+SLASH+props.getProperty("nameMap");
				topRegs = dataDir+SLASH+props.getProperty("topRegs");
				condMap = dataDir+SLASH+props.getProperty("condMap");
				geneAssoc  = dataDir+SLASH+props.getProperty("geneAssoc");
				geneOnto = dataDir+SLASH+props.getProperty("geneOnto");
				bingoOut = outputDir+SLASH+props.getProperty("bingoOut");
				topCondOut = outputDir+SLASH+props.getProperty("topCondOut");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		VameiFigGen figGen = new VameiFigGen(xmlInput, 
					outputDir, 
					nameMap,
					topRegs, 
					condMap,
					geneAssoc,
					geneOnto,
					bingoOut,
					topCondOut);

			figGen.startGUI();

		
	}

}

