package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.graphicalmodule.GraphicalModuleModel;

public class ChangeEpsOutputDirAction extends AbstractAction {


	private static final long serialVersionUID = 1L;
	private GraphicalModuleModel guiModel;

	public ChangeEpsOutputDirAction(GraphicalModuleModel guiModel){
		super("Set eps output directory...");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc;
		if (guiModel.getEpsOutputDir()==null){
			fc = new JFileChooser(guiModel.getCurrentDir());
		} else {
			fc = new JFileChooser(guiModel.getEpsOutputDir());
		}
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Select EPS output directory");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			guiModel.setEpsOutputDir(fc.getSelectedFile());
		} 
		
	}

	

}
