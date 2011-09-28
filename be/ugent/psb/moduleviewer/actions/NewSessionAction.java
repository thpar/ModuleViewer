package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

public class NewSessionAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private Model model;
	private GUIModel guiModel;

	
	public NewSessionAction(Model model, GUIModel guiModel){
		super("New Session...", UIManager.getIcon("FileView.fileIcon"));
		this.model = model;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int answer = JOptionPane.showConfirmDialog(guiModel.getTopContainer(), 
				"This will unload all data. Are you sure?", "New Session", JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION){
			model.resetData();
		}

	}

	

}
