package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ToggleDebugModeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	public ToggleDebugModeAction(GUIModel guiModel){
		super("Debug mode");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setDebugMode(!guiModel.isDebugMode());
	}

}
