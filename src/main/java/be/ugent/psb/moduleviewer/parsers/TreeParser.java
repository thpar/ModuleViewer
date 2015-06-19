package be.ugent.psb.moduleviewer.parsers;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
