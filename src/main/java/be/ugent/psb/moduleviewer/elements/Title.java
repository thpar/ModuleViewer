package be.ugent.psb.moduleviewer.elements;

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
import java.awt.Font;
import java.awt.Graphics2D;

import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Label;

/**
 * Title to display above the figure
 * 
 * @author Thomas Van Parys
 *
 */
public class Title extends Element {

	private Label label;
	
	public enum Angle{
		HORIZONTAL, VERTICAL;
	}

	public Title(String title){
		this.label = new Label(title);
		this.addChildElement(label);
		this.label.setFont(new Font("SansSerif", Font.BOLD, 14));
	}
	
	
	
	public String getTitle() {
		return label.getLabelString();
	}


	public void setTitle(String title) {
		label.setLabelString(title);
	}

	public Angle getAngle() {
		return (Math.abs(label.getAngle())>1)? Angle.VERTICAL:Angle.HORIZONTAL;
	}
	
	
	
	public void setAngle(Angle angle) {
		switch(angle){
		case HORIZONTAL:
			this.label.setAngle(0);
			break;
		case VERTICAL:
			this.label.setAngle(Math.PI/2);
			break;
		}
	}


	public Font getFont() {
		return label.getFont();
	}



	public void setFont(Font font) {
		label.setFont(font);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return label.getDimension(g);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return label.paint(g, xOffset, yOffset);
	}

}
