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

import be.ugent.psb.modulegraphics.display.CanvasFigure;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;



public class ExportToFigureAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GUIModel guiModel;

	private Model model;
	
	
	public ExportToFigureAction(Model model, GUIModel guiModel){
		super("Export to figure");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	public ExportToFigureAction(Model model, GUIModel guiModel, boolean showLabel){
		super(new String());
		this.guiModel = guiModel;
		this.model = model;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		String modId = guiModel.getDisplayedModule();
		ModuleNetwork modnet = model.getModnet();
		
		Module mod = null;
		try {
			mod = modnet.getModule(modId);
		} catch (UnknownItemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		String title = "Module "+mod.getId();
		String fileId = String.valueOf(mod.getId());
		if (mod.getName()!=null && !mod.getName().isEmpty()){
			title+=" - "+mod.getName();
			fileId+="_"+mod.getName();
		}
		
		String fileName = new String();
		try {
			fileName = guiModel.getOutputDir()
					+ System.getProperty("file.separator")
					+ guiModel.getFileNameTemplate(fileId) + "."
					+ guiModel.getOutputFormat();
			guiModel.setStateString("figure saved to: " + fileName);
			model.getLogger().addEntry("figure saved to: " + fileName);
			Canvas canvas = new DefaultCanvas(mod, model, guiModel, title);
			CanvasFigure figCanvas = new CanvasFigure(canvas, fileName);
			figCanvas.writeToFigure(guiModel.getOutputFormat());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
					"Could not export figure: "+fileName,
					"Export error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
