package be.ugent.psb.moduleviewer.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.CheckListParser;
import be.ugent.psb.graphicalmodule.dialogs.LoadCheckListDialog;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

/**
 * 
 * @author thpar
 *
 */
public class LoadGeneCheckListAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;

	
	public LoadGeneCheckListAction(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load gene checklist...");
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load gene checklist");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			
			//show option dialog
			LoadCheckListDialog dialog = new LoadCheckListDialog(guiModel.getTopContainer(), file.getName());
			dialog.setVisible(true);
			
			int perc = dialog.getPercentCutoff();
			String name = dialog.getListName();
			
			CheckListParser.load(modnet, file, name, perc, guiModel, true);
			
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
