package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

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
		int modId = guiModel.getDisplayedModule();
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
				guiModel.setOutputDir(guiModel.getCurrentDir());
			}
			
		}
		
		String fileName = guiModel.getOutputDir() + System.getProperty("file.separator") + 
								guiModel.getFileNameTemplate(modId)+"."+guiModel.getOutputFormat();
		guiModel.setStateString("figure saved to: "+fileName);
		Canvas canvas = new DefaultCanvas(mod, model, guiModel, guiModel.getFileNameTemplate(modId));
		CanvasFigure figCanvas = new CanvasFigure(canvas, fileName);
		figCanvas.writeToFigure(guiModel.getOutputFormat());
		
	}

}
