package be.ugent.psb.graphicalmodule.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Angle;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import cytoscape.data.annotation.OntologyTerm;


public class GOLabels extends Element {
	private LabelList labelList;
	
	public GOLabels(LinkedHashSet<OntologyTerm> conditions){
		List<String> list = new ArrayList<String>();
		for (OntologyTerm term : conditions){
			list.add(term.getName());
		}
		labelList = new LabelList(list);
		addChildElement(labelList);
		labelList.setDirection(Direction.LEFT_TO_RIGHT);
		labelList.setAngle(Angle.SKEWED);
		labelList.setFont(new Font("SansSerif", Font.PLAIN, 10));
		addMouseListener(new ElementEventChildForwarder(this));
	}
	
	public void setPushBounds(boolean pushBounds){
		this.labelList.setPushBounds(pushBounds);
	}
	
	public boolean getPushBounds(){
		return this.labelList.isPushBounds();
	}
	
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return labelList.getDimension(g);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return labelList.paint(g, xOffset, yOffset);
	}
	
	/*
	 * Hits
	 */
	
	public String getHitString(int x, int y){
		return labelList.getHitLabelString(x, y);
	}
	

}
