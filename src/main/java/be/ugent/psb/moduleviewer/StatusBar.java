package be.ugent.psb.moduleviewer;

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

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import be.ugent.psb.moduleviewer.model.GUIModel;

/**
 * Status bar at the bottom of the screen. Listens to the model, which can set status messages to display.
 * Also the progress bar while loading files will be shown here.
 * 
 * @author Thomas Van Parys
 *
 */
public class StatusBar extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	
	/**
	 * Status message
	 */
	private JLabel state;
	
	/**
	 * Progress bar
	 */
	private JProgressBar progressBar = new JProgressBar();
	
	
	public StatusBar(GUIModel guiModel){
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.guiModel = guiModel;
		guiModel.addObserver(this);
				
		state = new JLabel();
		
		add(state);
		add(Box.createHorizontalGlue());
		
		add(progressBar);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setMaximumSize(new Dimension(40, 100));
		
		update(null, null);
	}


	@Override
	public void update(Observable o, Object arg) {
		state.setText(guiModel.getStateString());
		if (guiModel.isShowProgressBar()){
			progressBar.setVisible(true);
			progressBar.setValue(guiModel.getProgressBarProgress());
		} else {
			progressBar.setVisible(false);
		}
		
	}

	
}
