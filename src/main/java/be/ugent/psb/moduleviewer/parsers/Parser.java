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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.Logger;
import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public abstract class Parser {

	abstract public void parse(Model model, InputStream inputFile) throws IOException, ParseException;

	public void parse(Model model, File inputFile) throws IOException, ParseException{
		InputStream stream = new FileInputStream(inputFile);
		this.parse(model, stream);
	}
	
	public Parser(){
		this.progressListener = new ProgressListener(){
			@Override
			public void setMyProgress(int percent) {
				//a progress listener that doesn't do anything
			}
		};
	}
	
	protected ProgressListener progressListener;
	protected Logger logger;
	
	/**
	 * this progress listener should be implemented inside a {@link SwingWorker} 
	 * so it can refer to its setProgress method.
	 *  
	 * @param progListener
	 */
	public Parser(ProgressListener progListener){
		this.progressListener = progListener;
	}

	
	
	
}
