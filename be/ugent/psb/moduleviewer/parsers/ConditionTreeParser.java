package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * Parses the module tree. A node either has two children, or it's a leave. 
 * Leaves contain a subset of the conditions in a specific order.
 * The {@link SexParser} uses the {@link TreeXMLHandler}
 * 
 * @author thpar
 *
 */
public class ConditionTreeParser extends Parser{

	@Override
	public void parse(Model model, File inputFile) throws IOException {
		ModuleNetwork modnet = model.getModnet();
		try {
			
			InputStream inputStream = new FileInputStream(inputFile);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser =  factory.newSAXParser();
			
			saxParser.parse(inputStream, new TreeXMLHandler(modnet, progressListener));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
