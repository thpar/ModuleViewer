package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ToggleShowConditionLabelsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	public ToggleShowConditionLabelsAction(GUIModel guiModel){
		super("Condition labels");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setDrawConditionsLabels(!guiModel.isDrawConditionLabels());
	}

}
