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

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;

/**
 * Gene names to display next to the main expression matrix 
 * 
 * @author Thomas Van Parys
 *
 */
public class GeneNames extends Canvas {
	
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
		
		LabelList labelList = new LabelList(geneNames);
		
		labelList.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelList.setDirection(Direction.TOP_TO_BOTTOM);
//		addMouseListener(new ElementEventChildForwarder(this));
		add(labelList);
		this.newRow();
		
	}

	public void colorBackgrounds(Set<Gene> coloredGenes, Color backgroundColor) {
		List<String> coloredGeneNames = new ArrayList<String>();
		for (Gene g: coloredGenes){
			coloredGeneNames.add(g.getAliasOrName());
		}
		for (Element el: this){
			LabelList labelList = (LabelList)el;
			labelList.colorBackgrounds(coloredGeneNames, backgroundColor);
		}
	}


	
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
