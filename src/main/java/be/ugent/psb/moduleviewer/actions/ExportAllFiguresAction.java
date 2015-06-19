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
import javax.swing.SwingWorker;

import be.ugent.psb.modulegraphics.display.CanvasFigure;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;



public class ExportAllFiguresAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GUIModel guiModel;

	private Model model;
	
	
	public ExportAllFiguresAction(Model model, GUIModel guiModel){
		super("Export all figures");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	public ExportAllFiguresAction(Model model, GUIModel guiModel, boolean showLabel){
		super(new String());
		this.guiModel = guiModel;
		this.model = model;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		File outputDir = guiModel.getOutputDir();
		if (outputDir==null){
			JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select output directory");
			int answer = fc.showOpenDialog(guiModel.getTopContainer());
			if (answer == JFileChooser.APPROVE_OPTION){
				guiModel.setOutputDir(fc.getSelectedFile());
			} else {
				return;
			}
			
		}
		

		SaveTask worker = new SaveTask();
		worker.addPropertyChangeListener(guiModel);
		worker.execute();
		
		
		
	}
	
	
	
	public class SaveTask extends SwingWorker<Void, Void>{

		public SaveTask(){

		}
		
		@Override
		protected Void doInBackground() throws Exception {
			guiModel.showProgressBar(true);
			setProgress(0);
			
			//trick to pass on progListener to other class. Not needed now.
//			ProgressListener progListener = new ProgressListener(){
//				@Override
//				public void setMyProgress(int percent) {
//					setProgress(percent);
//				}
//			};
			
			
			ModuleNetwork modnet = model.getModnet();
			guiModel.showProgressBar(true);
			guiModel.setStateString("Exporting images...");
			
			int modCount = 0;
			int totModCount = modnet.getModuleIds().size();
			
			for (String modId : modnet.getModuleIds()){
				Module mod = modnet.getModule(modId);
				String title = "Module "+modId;
				String file_id = modId;
				if (mod.getName()!=null && !mod.getName().isEmpty()){
					title+=" - "+mod.getName();
					file_id+="_"+mod.getName();
				}
				String fileName = guiModel.getOutputDir() + System.getProperty("file.separator") + 
				guiModel.getFileNameTemplate(file_id)+"."+guiModel.getOutputFormat();
				
				try {
					Canvas canvas = new DefaultCanvas(mod, model, guiModel, title);
					CanvasFigure figCanvas = new CanvasFigure(canvas, fileName);
					figCanvas.writeToFigure(guiModel.getOutputFormat());
				} catch (Exception e) {
					model.getLogger().addEntry(e, "Failed to export figure "+fileName);
				}
				
				modCount++;
				int prog = (int)Math.round(((double)modCount/(double)totModCount) * 100);
				setProgress(prog);
			}
			
			setProgress(100);
			guiModel.setStateString("Figures exported to "+guiModel.getOutputDir());
			return null;
		}
		
		
		
		@Override
		protected void done() {
			guiModel.showProgressBar(false);
			model.getLogger().addEntry("All figures exported to: "+guiModel.getOutputDir());
			guiModel.refresh();
		}
	}


}
