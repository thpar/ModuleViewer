package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.ModuleNetworkParser;
import be.ugent.psb.ModuleNetwork.parsers.TopRegulatorsParser;
import be.ugent.psb.graphicalmodule.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadTopRegulatorsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadTopRegulatorsAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load topregulators...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load topregulators");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			//TODO wrap in a Task
			TopRegulatorsParser.load(modnet, file);
			guiModel.refresh();
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
