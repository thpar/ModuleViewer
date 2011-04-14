package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.GeneLinkParser;
import be.ugent.psb.ModuleNetwork.parsers.GeneNameMappingParser;
import be.ugent.psb.ModuleNetwork.parsers.ModuleNetworkParser;
import be.ugent.psb.ModuleNetwork.parsers.TopRegulatorsParser;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadGeneLinkAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadGeneLinkAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load gene links...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load gene links");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			//TODO wrap in a Task
			GeneLinkParser.load(modnet, file, guiModel, true);
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
