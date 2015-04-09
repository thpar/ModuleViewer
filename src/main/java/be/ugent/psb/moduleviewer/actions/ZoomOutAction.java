package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ZoomOutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	
	
	
	public ZoomOutAction(GUIModel guiModel) {
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.decZoomLevel(0.05);
	}

}
