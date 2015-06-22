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
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ParseException;

/**
 * Action to load a recently used session file
 * 
 * @author Thomas Van Parys
 *
 */
public class LoadRecentSessionAction extends LoadSessionAction {

	private static final long serialVersionUID = 1L;
	
	private File session;
	
	
	public LoadRecentSessionAction(Model model, GUIModel guiModel, File session) {
		super(session.toString(), model, guiModel);
		this.session = session;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.loadFromFile(this.session);
			guiModel.addRecentSession(session);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1){
			JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
					"Could not load session file: "+session,
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
