package be.ugent.psb.moduleviewer.dialogs;

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


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import be.ugent.psb.moduleviewer.Logger;

/**
 * The log window. Display the list of log messages.
 *  
 * @author Thomas Van Parys
 *
 */
public class LogDialog extends JDialog implements Observer{


	private static final long serialVersionUID = 1L;
	private Logger logger;
	private JTextArea log;
	
	public LogDialog(Logger logger){
		this.logger = logger;
		this.logger.addObserver(this);
		
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(500,300));
		
		log = new JTextArea();
		log.setColumns(80);
		log.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(log);
		
		this.add(scrollPane, BorderLayout.CENTER);
		updateLogField();
	}
	
	private void updateLogField(){
		List<String> logEntries = this.logger.getLog();
		StringBuffer fullLog = new StringBuffer();
		for (String entry : logEntries){
			fullLog.append(entry);
		}
		log.setText(fullLog.toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		updateLogField();
	}
	
}
