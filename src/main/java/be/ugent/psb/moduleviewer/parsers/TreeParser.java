package be.ugent.psb.moduleviewer.parsers;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import be.ugent.psb.moduleviewer.actions.ProgressListener;


/**
 * Parses a tree. A node either has two children, or it's a leave. 
 * Leaves contain a subset of the conditions in a specific order.
 * The {@link SaxParser} uses either the {@link ConditionXMLHandler} or the {@link GeneXMLHandler} 
 * depending on the implementation of TreeParser
 * 
 * @author thpar
 *
 */
abstract class TreeParser extends Parser{

	
	public TreeParser() {
		super();
	}

	public TreeParser(ProgressListener progListener) {
		super(progListener);
	}

	
	protected void parse(InputStream inputStream, DefaultHandler handler) throws IOException, ParseException{
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser =  factory.newSAXParser();
			
			saxParser.parse(inputStream, handler);
		} catch (ParserConfigurationException e) {
			throw new ParseException(e);
		} catch (SAXException e) {
			throw new ParseException(e);
		}
	}

}
