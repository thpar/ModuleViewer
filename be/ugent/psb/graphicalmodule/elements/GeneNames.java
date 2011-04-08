package be.ugent.psb.graphicalmodule.elements;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.graphicalmodule.model.Gene;
import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;

public class GeneNames extends Element {

	private LabelList labelList;
	private List<Gene> genes;
	
	public GeneNames(List<Gene> genes){
		this.genes = genes;
		List<String> geneNames = new ArrayList<String>();
		for (Gene gene : genes){
			geneNames.add(gene.getName());
		}
		labelList = new LabelList(geneNames);
		
		addChildElement(labelList);
		addMouseListener(new ElementEventChildForwarder(this));
		
		labelList.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelList.setDirection(Direction.TOP_TO_BOTTOM);
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
	
	public Gene getHitGene(int x, int y){
		int hitGene;
		if ((hitGene = labelList.getHitLabelRow(x, y)) >= 0){
			return genes.get(hitGene);
		} else {
			return null;
		}
	}
	
	public String getHitGeneName(int x, int y){
		return labelList.getHitLabelString(x, y);
	}
	
	
	public void highlightGene(Gene highlight, boolean hl){
		labelList.setLabelHighlight(highlight.getName(), hl);		
	}
	public void toggleHighlightGene(Gene highlight){
		labelList.toggleLabelHighlight(highlight.getName());
	}
	public void removeHighlights(){
		labelList.removeHighlights();
	}
}
