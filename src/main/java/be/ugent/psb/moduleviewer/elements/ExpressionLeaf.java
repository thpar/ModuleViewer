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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ColorMatrix;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;

public class ExpressionLeaf extends Canvas{
	

	private List<Condition> condSet;
	private ExpressionColorizer c;
	

	private boolean drawGeneSeparators = true;
	
	/**
	 * 
	 * @param genes list of genes in this module
	 * @param condSet the conditions represented in this leaf
	 * @param mean
	 * @param sigma
	 */
	public ExpressionLeaf(GeneNode geneRoot, List<Condition> condSet, ExpressionColorizer c){

		this.addMouseListener(new ElementEventChildForwarder(this));
		
		this.condSet = condSet;
		this.c = c;
		
		addLeaves(geneRoot);
		
	}


	private void addLeaves(GeneNode node){
		if (node.isLeaf()){
			this.addMatrix(node.getColumns());
		} else {
			addLeaves((GeneNode)node.left());
			addLeaves((GeneNode)node.right());
		}
	}
	
	private void addMatrix(List<Gene> genes){
		Double[][] data = new Double[genes.size()][condSet.size()];
		int i=0;
		for (Gene gene : genes){
			int ii=0;
			for (Condition cond : condSet){
				data[i][ii++] = gene.getValue(cond);
			}
			i++;
		}
		ColorMatrix<Double> matrix = new ColorMatrix<Double>(data, c);
		this.add(matrix);
		this.newRow();
	}

	
	
	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		Dimension drawnDim = super.paintElement(g, xOffset, yOffset);
		
		
		//white lines in between gene groups
		if (drawGeneSeparators){
			int y = 0;
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(1.5F));

			for (Iterator<Element> it = this.iterator(); it.hasNext(); ){
				Element el = it.next();
				if (it.hasNext()){
					y+=el.getDimension(g).height;
					g.drawLine(xOffset, yOffset + y, xOffset+drawnDim.width, yOffset+y);
				}
			}
		}
		
		
		return drawnDim;
	}


	/**
	 * Returns the location in the data matrix that has been hit.
	 * 
	 * @param x
	 * @param y
	 * @return a {@link Point} with {@link Point}.x the hit column and {@link Point}.y the hit row.  
	 */
	public Point getHitCoordinates(int x, int y){
		ColorMatrix<Double> hitMatrix = (ColorMatrix<Double>)this.getHitChild(x, y);
		return hitMatrix.getHitSquare(x, y);
	}
	
	/**
	 * Returns 
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Double getHitData(int x, int y){
		ColorMatrix<Double> hitMatrix = (ColorMatrix<Double>)this.getHitChild(x, y);
		return hitMatrix.getHitData(x, y);
	}


	public boolean isDrawGeneSeparators() {
		return drawGeneSeparators;
	}


	public void setDrawGeneSeparators(boolean drawGeneSeparators) {
		this.drawGeneSeparators = drawGeneSeparators;
	}


	
	
}
