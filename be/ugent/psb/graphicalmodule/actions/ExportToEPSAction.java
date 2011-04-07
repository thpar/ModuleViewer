package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;
import be.ugent.psb.graphicalmodule.model.Module;
import be.ugent.psb.graphicalmodule.model.ModuleNetwork;

public class ExportToEPSAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	GraphicalModuleModel guiModel;

	private ModuleNetwork modnet;
	
	public ExportToEPSAction(ImageIcon icon, ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super(new String(), icon);
		this.guiModel = guiModel;
		this.modnet = modnet;
	}
	
	public ExportToEPSAction( ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Export to eps");
		this.guiModel = guiModel;
		this.modnet = modnet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int modId = guiModel.getDisplayedModule();
		
		Module mod = modnet.moduleSet.get(modId);
		
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
		
		mod.drawMyWay(guiModel.getEpsOutputDir()+System.getProperty("file.separator")+
				guiModel.getFileNameTemplate(modId), "Module "+modId);
		
	}

}
