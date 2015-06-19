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

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;

/**
 * 
 * @author thpar
 *
 */
public class LoadDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public LoadDataAction(Model model, GUIModel guiModel){
		super("Load Data matrix...");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load data matrix");
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
			guiModel.showProgressBar(true);
			setProgress(0);
			
			ProgressListener progListener = new ProgressListener(){
				@Override
				public void setMyProgress(int percent) {
					setProgress(percent);
				}
			};
			
			DataMatrixParser parser = new DataMatrixParser(progListener);
			
			guiModel.setStateString("Loading expression data from: "+file);
			
			
			try {
				parser.parse(model, file);
				model.setDataFile(file.getAbsolutePath());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
						"Could not parse file: "+file,
						"Parsing error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			model.getLogger().addEntry("Expression matrix loaded: "+file);
			setProgress(100);
			
			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			guiModel.clearStateString();
			guiModel.refresh();
		}
	}

}
