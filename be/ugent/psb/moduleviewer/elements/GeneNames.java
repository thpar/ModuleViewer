package be.ugent.psb.moduleviewer.elements;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;

public class GeneNames extends Canvas {

	private LabelList labelList;

	
	public GeneNames(GeneNode geneRoot){
		addLeaves(geneRoot);
	}
	
	private void addLeaves(GeneNode node){
		if (node.isLeaf()){
			addGeneNames(node.getColumns());
		} else {
			addLeaves((GeneNode)node.left());
			addLeaves((GeneNode)node.right());
		}
	}
	

	
	private void addGeneNames(List<Gene> genes) {
		List<String> geneNames = new ArrayList<String>();
		for (Gene gene : genes){
			geneNames.add(gene.getAliasOrName());
		}
		
		labelList = new LabelList(geneNames);
		
		add(labelList);
		this.newRow();
//		addMouseListener(new ElementEventChildForwarder(this));
		
		labelList.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelList.setDirection(Direction.TOP_TO_BOTTOM);
		
	}

//	@Override
//	protected Dimension getRawDimension(Graphics2D g) {
//		return labelList.getDimension(g);
//	}
//
//	@Override
//	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
//		return labelList.paint(g, xOffset, yOffset);
//	}
	
	
	/*
	 * Hits
	 */
	
//	public Gene getHitGene(int x, int y){
//		int hitGene;
//		if ((hitGene = labelList.getHitLabelRow(x, y)) >= 0){
//			return genes.get(hitGene);
//		} else {
//			return null;
//		}
//	}
//	
//	public String getHitGeneName(int x, int y){
//		return labelList.getHitLabelString(x, y);
//	}
//	
//	
//	public void highlightGene(Gene highlight, boolean hl){
//		labelList.setLabelHighlight(highlight.getAliasOrName(), hl);		
//	}
//	public void toggleHighlightGene(Gene highlight){
//		labelList.toggleLabelHighlight(highlight.getAliasOrName());
//	}
//	public void removeHighlights(){
//		labelList.removeHighlights();
//	}
}
