package be.ugent.psb.moduleviewer.actions;

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

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.ParseException;
import be.ugent.psb.moduleviewer.parsers.RegulatorTreeParser;

/**
 * 
 * Safes the 'session' to a file.
 * A session should consist of all loaded files and the GUIModel settings.
 * 
 * @author thpar
 *
 */
public class LoadSessionAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected GUIModel guiModel;
	protected Model model;

	
	public LoadSessionAction(Model model, GUIModel guiModel){
		this("Load session...", model, guiModel);
	}
	public LoadSessionAction(String label, Model model, GUIModel guiModel){
		super(label);
		this.guiModel = guiModel;
		this.model = model;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load session...");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();	
			try {
				loadFromFile(file);
				guiModel.addRecentSession(file);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
						"Could not load session file: "+file,
						"Read error",
						JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
						"Could not load session file: "+file,
						"Parse error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

	protected void loadFromFile(File file) throws IOException, ParseException{
		Properties props = new Properties();
		
		FileReader fr = new FileReader(file);
		props.load(fr);
		
		DataMatrixParser dmp = new DataMatrixParser();
		File dmpFile = new File(props.getProperty("data"));
		dmp.parse(model, dmpFile);
		model.setDataFile(dmpFile.getAbsolutePath());
		
		GeneTreeParser gtp   = new GeneTreeParser();
		File gtpFile = new File(props.getProperty("genes"));
		gtp.parse(model, gtpFile);
		model.setGeneFile(gtpFile.getAbsolutePath());
		
		ConditionTreeParser ctp = new ConditionTreeParser();
		File ctpFile = new File(props.getProperty("conditions"));
		ctp.parse(model, ctpFile);
		model.setConditionFile(ctpFile.getAbsolutePath());
		
		String regTreeFile = props.getProperty("regulators");
		if (regTreeFile != null){
			RegulatorTreeParser rtp = new RegulatorTreeParser();
			rtp.parse(model, new File(regTreeFile));
			model.setRegulatorFile(regTreeFile);
		}
		
		String mvfFileString = props.getProperty("annotations");
		if (mvfFileString != null && !mvfFileString.isEmpty()){
			String[] mvfFiles = mvfFileString.split(";");
			for (String mvf : mvfFiles){
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, new File(mvf));
				model.addAnnotationFile(mvf);
			}
		}
		
		guiModel.refresh();
		
		
	}
	
	
	

}
