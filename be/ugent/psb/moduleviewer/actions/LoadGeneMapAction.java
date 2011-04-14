package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.GeneNameMappingParser;
import be.ugent.psb.ModuleNetwork.parsers.ModuleNetworkParser;
import be.ugent.psb.ModuleNetwork.parsers.TopRegulatorsParser;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadGeneMapAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadGeneMapAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load gene map...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load gene mapping");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			//TODO wrap in a Task
			GeneNameMappingParser.load(modnet, file);
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
