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

import java.awt.Cursor;
import java.awt.Frame;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import be.ugent.psb.modulegraphics.display.CanvasLabel;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.elements.DefaultCanvas;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;


/**
 * The body of the GUI, the label that draws the Canvas with the module figures.
 * 
 * @author thpar
 *
 */
public class ModuleLabel extends CanvasLabel implements Observer{

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;

	private ModuleNetwork modnet;
	
//	private Dimension currentCanvasSize = new Dimension();

	private Model model;
	private boolean firstload = true;
	private Cursor cursor; 
		
		
	public ModuleLabel(Model model, GUIModel guiModel){
		this.model = model;
		this.model.addObserver(this);
		this.modnet = model.getModnet();
		this.guiModel = guiModel;
		this.guiModel.addObserver(this);
		//set splash here if needed
	}
	
	/**
	 * Create the Canvas and add it to this label.
	 * @return
	 */
	public Element initCanvas() {
		//don't even start if we don't have the essential data loaded
		if (!model.isEssentialsLoaded()){
			setCanvas(null);
			firstload = true;
			return null;
		} else {
			if (firstload ){
				Frame window = guiModel.getTopContainer();
				window.setExtendedState(window.getExtendedState()|JFrame.MAXIMIZED_BOTH);
				firstload = false;
			}
		}
	
		PointMode pm = guiModel.getPointMode();
		this.setPointMode(pm);
		
		String displayedModule = guiModel.getDisplayedModule();
		Module mod = null;
		try {
			mod = modnet.getModule(displayedModule);
		} catch (UnknownItemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String title = "Module "+displayedModule;
		if (mod.getName()!=null && !mod.getName().isEmpty()){
			title+=" - "+mod.getName();
		}
		
		DefaultCanvas canvas = new DefaultCanvas(mod, model, guiModel, title);
		setCanvas(canvas);
		
		
		this.setZoomLevel(guiModel.getZoomLevel());
		
		
		return canvas;
	}

	private void setPointMode(PointMode pm){
		switch(pm){
		case POINT:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			break;
		case PAN:
			this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			break;
		}
	}



	@Override
	public void update(Observable o, Object arg) {
		if (arg!=null && arg.toString().equals("nonredraw")){
			this.setPointMode(guiModel.getPointMode());
			return;
		}
		initCanvas();
		repaint();
	}


	
	
	
}
