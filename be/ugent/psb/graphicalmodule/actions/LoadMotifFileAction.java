package be.ugent.psb.graphicalmodule.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.ModuleNetwork.parsers.MotifFileParser;
import be.ugent.psb.graphicalmodule.GraphicalModuleModel;
import be.ugent.psb.graphicalmodule.dialogs.LoadMotifFileDialog;

/**
 * 
 * @author thpar
 *
 */
public class LoadMotifFileAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;
	private Frame owner;

	
	public LoadMotifFileAction(Frame mainWindow, ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("Load motif checklist...");
		this.owner = mainWindow;
		this.modnet = modnet;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle("Load motif checklist");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			
			//show option dialog
			LoadMotifFileDialog dialog = new LoadMotifFileDialog(owner, file.getName());
			dialog.setVisible(true);
			
//			int perc = dialog.getPercentCutoff();
			String name = dialog.getListName();
			
			MotifFileParser.load(modnet, file, name, guiModel, true);
			
			guiModel.refresh();
			
		}
		guiModel.setCurrentDir(fc.getCurrentDirectory());
	}

}
