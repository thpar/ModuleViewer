package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.AracycParser;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadAracycAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadAracycAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load aracyc data for genes...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load aracyc data for genes");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			
			AracycParser.load(modnet, file, guiModel, true);
			guiModel.setDrawAracyc(true);
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
