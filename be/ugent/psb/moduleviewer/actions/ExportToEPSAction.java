package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import be.ugent.psb.modulegraphics.display.CanvasFigure;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.moduleviewer.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;



public class ExportToEPSAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GUIModel guiModel;

	private Model model;
	
	public ExportToEPSAction(ImageIcon icon, Model model, GUIModel guiModel){
		super(new String(), icon);
		this.guiModel = guiModel;
		this.model = model;
	}
	
	public ExportToEPSAction(Model model, GUIModel guiModel){
		super("Export to eps");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int modId = guiModel.getDisplayedModule();
		ModuleNetwork modnet = model.getModnet();
		
		Module mod = modnet.getModules().get(modId);
		
		File outputDir = guiModel.getEpsOutputDir();
		if (outputDir==null){
			JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setDialogTitle("Select EPS output directory");
			int answer = fc.showOpenDialog(guiModel.getTopContainer());
			if (answer == JFileChooser.APPROVE_OPTION){
				guiModel.setEpsOutputDir(fc.getSelectedFile());
			} else {
				guiModel.setEpsOutputDir(guiModel.getCurrentDir());
			}
			
		}
		
		String fileName = guiModel.getEpsOutputDir() + System.getProperty("file.separator") + 
								guiModel.getFileNameTemplate(modId)+".eps";
		guiModel.setStateString("figure saved to: "+fileName);
		Canvas canvas = new DefaultCanvas(mod, guiModel, guiModel.getFileNameTemplate(modId));
		CanvasFigure figCanvas = new CanvasFigure(canvas, fileName);
		figCanvas.writeToEPS();
		
	}

}
