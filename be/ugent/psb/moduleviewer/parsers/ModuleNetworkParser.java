package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import be.ugent.psb.moduleviewer.actions.LoadModulesAction.ProgressListener;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;

/**
 * This parser will load the Module information and data matrix into a given {@link ModuleNetwork} object.
 * 
 * @author thpar
 *
 */
public class ModuleNetworkParser extends Parser{

	static public void loadWithSAXParser(ModuleNetwork modnet, File xmlFile) {

		try {
			
			GZIPInputStream inputStream = new GZIPInputStream(new FileInputStream(xmlFile));
			
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser =  factory.newSAXParser();
			
			Date then = new Date();
			saxParser.parse(inputStream, new XMLModuleHandler(modnet, progressListener));
			Date now = new Date();
			System.out.println(timerCalc(then, now));
			
//			modnet.calculateModuleMeans();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static String timerCalc(Date before, Date after){
		long diff = (after.getTime() - before.getTime())/1000;

		int hours = (int)(diff / 60 / 60);
		diff-= hours*60*60;
		int mins  = (int)diff/60;
		diff-= mins*60;
		int secs   = (int)diff;
		return new String(hours +"h "+mins+"' "+secs+"''");
	}


	static private ProgressListener progressListener;

	public void setProgressListener(ProgressListener progListener) {
		progressListener = progListener;		
	}

}
