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
import javax.swing.JFileChooser;

import be.ugent.psb.moduleviewer.model.GUIModel;

/**
 * Dialog window to set output directory
 * 
 * @author Thomas Van Parys
 *
 */
public class ChangeOutputDirAction extends AbstractAction {


	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	public ChangeOutputDirAction(GUIModel guiModel){
		super("Set output directory...");
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc;
		if (guiModel.getOutputDir()==null){
			fc = new JFileChooser(guiModel.getCurrentDir());
		} else {
			fc = new JFileChooser(guiModel.getOutputDir());
		}
		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDialogTitle("Select output directory");
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			guiModel.setOutputDir(fc.getSelectedFile());
		} 
		
	}

	

}
