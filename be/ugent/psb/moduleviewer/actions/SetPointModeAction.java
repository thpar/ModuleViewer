package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

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
		this.putValue(LARGE_ICON_KEY, getIcon(pm));
		this.pointMode = pm;
		this.guiModel = guiModel;
	}
	
	private Icon getIcon(PointMode pm) {
		Icon icon = null;
		switch(pm){
		case PAN:
			icon = new ImageIcon(getClass().getResource("/icons/move.png"));
			break;
		case POINT:
			icon = new ImageIcon(getClass().getResource("/icons/pointer.png"));
			break;
		}
		return icon;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setPointMode(this.pointMode);
	}
	
	
	
}
