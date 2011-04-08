package be.ugent.psb.graphicalmodule.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Label;


public class Title extends Element {

	private Label label;
	
	public enum Angle{
		HORIZONTAL, VERTICAL;
	}

	public Title(String title){
		this.label = new Label(title);
		this.addChildElement(label);
		this.label.setFont(new Font("SansSerif", Font.BOLD, 10));
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
