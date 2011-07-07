package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ChangeOutputDirAction extends AbstractAction {


	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	public ChangeOutputDirAction(GUIModel guiModel){
		super("Set output directory...");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc;
		if (guiModel.getOutputDir()==null){
			fc = new JFileChooser(guiModel.getCurrentDir());
		} else {
			fc = new JFileChooser(guiModel.getOutputDir());
		}
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Select output directory");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			guiModel.setOutputDir(fc.getSelectedFile());
		} 
		
	}

	

}
