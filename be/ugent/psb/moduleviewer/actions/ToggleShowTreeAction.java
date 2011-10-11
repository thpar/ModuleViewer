package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ToggleShowTreeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	public ToggleShowTreeAction(GUIModel guiModel){
		super("Tree structure");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setDrawTreeStructure(!guiModel.isDrawTreeStructure());
	}

}
