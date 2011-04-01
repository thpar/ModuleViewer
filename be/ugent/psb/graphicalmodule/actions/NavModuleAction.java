package be.ugent.psb.graphicalmodule.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

public class NavModuleAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private int step = 1;

	private GraphicalModuleModel guiModel;
	
	
	
	public NavModuleAction(ImageIcon icon, int step, GraphicalModuleModel guiModel) {
		super(new String(), icon);
		this.step = step;
		this.guiModel = guiModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.navDisplayedModule(step);

	}

}
