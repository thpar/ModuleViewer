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
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

/**
 * Unload all data and start a new session
 * 
 * @author Thomas Van Parys
 *
 */
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
