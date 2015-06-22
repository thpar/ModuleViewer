package be.ugent.psb.moduleviewer.actions;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;

/**
 * Action to toggle point mode (between dragging and clicking)
 * 
 * Point mode button is disabled at this point.
 * 
 * @author Thomas Van Parys
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
