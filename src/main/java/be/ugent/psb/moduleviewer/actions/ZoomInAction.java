package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class ZoomInAction extends AbstractAction {


	private static final long serialVersionUID = 1L;

	private GUIModel guiModel;
	
	
	
	public ZoomInAction(GUIModel guiModel) {
		this.guiModel = guiModel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.incZoomLevel(0.05);

	}

}
