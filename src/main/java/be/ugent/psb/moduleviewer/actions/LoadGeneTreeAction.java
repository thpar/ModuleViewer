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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.GeneListParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;

/**
 * Loads an xml file that classifies genes into modules and organizes them 
 * into a tree structure within each module.
 * 
 * @author Thomas Van Parys
 *
 */
public class LoadGeneTreeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public LoadGeneTreeAction(Model model, GUIModel guiModel){
		super("Load Gene Modules...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load gene modules");
		fc.addChoosableFileFilter(new FileNameRegexFilter("All gene files", ".*\\.(xml|mvf)"));
		fc.addChoosableFileFilter(new FileNameRegexFilter("Gene tree files", ".*\\.xml"));
		fc.addChoosableFileFilter(new FileNameRegexFilter("Gene list files", ".*\\.mvf"));
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			final File file = fc.getSelectedFile();
			
			LoadTask worker = new LoadTask(file);
			worker.addPropertyChangeListener(guiModel);
			worker.execute();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
		
	}
	
	
	public class LoadTask extends SwingWorker<Void, Void>{

		private File file;
		public LoadTask(File file){
			this.file = file;
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			//decide on file format
			BufferedReader input = new BufferedReader(new FileReader(file));
			String firstLine = input.readLine();
			input.close();
			
			guiModel.showProgressBar(true);
			setProgress(0);
			ProgressListener progListener = new ProgressListener(){
				@Override
				public void setMyProgress(int percent) {
					setProgress(percent);
				}
			};
			
			
			try {
				if (firstLine.startsWith("<?xml")){
					//Enigma style tree structure
					guiModel.setStateString("Loading XML gene tree structure from "+file);
					GeneTreeParser parser = new GeneTreeParser(progListener);
					
					parser.parse(model, file);
					
				} else {
					//mvf style gene list
					guiModel.setStateString("Loading flat gene structure from "+file);
					GeneListParser parser = new GeneListParser(progListener);
					
					parser.parse(model, file);
					
				}
				
				//set the displayed module to the first in the map
				String firstModule = model.getModnet().getFirstModuleId();
				guiModel.setDisplayedModule(firstModule);
				model.setGeneFile(file.getAbsolutePath());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
						"Could not parse file: "+file,
						"Parsing error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			model.getLogger().addEntry("Gene and module data loaded: "+file);
			setProgress(100);
			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.clearStateString();
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}

}
