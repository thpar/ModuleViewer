package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;

/**
 * Loads an MVF file. These files represent several kinds of annotations for 
 * genes and conditions.
 * 
 * @author thpar
 *
 */
public class SetPointModeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private PointMode pointMode;

	
	public SetPointModeAction(GUIModel guiModel, PointMode pm){
		super(pm.toString());
		this.pointMode = pm;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setPointMode(this.pointMode);
	}
	
	
	
}
