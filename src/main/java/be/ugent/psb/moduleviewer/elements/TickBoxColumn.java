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
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Colorizer;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ColorMatrix;
import be.ugent.psb.moduleviewer.model.Gene;

/**
 * The tickbox matrix simply checks if a genes is present in a list and colors
 * it if it does.
 * 
 * @author Thomas Van Parys
 *
 */
public class TickBoxColumn extends Element implements Colorizer<Boolean>{


	private Color color;
	private ColorMatrix<Boolean> col;

	
	public TickBoxColumn(List<Gene> allModuleGenes, List<Gene> checkedGenes, Color color) {
		Set<Gene> set = new HashSet<Gene>();
		for (Gene gene : checkedGenes){
			set.add(gene);
		}
		init(allModuleGenes, set, color);
	}
	
	public TickBoxColumn(List<Gene> allModuleGenes, Set<Gene> checkedGenes, Color red) {
		init(allModuleGenes, checkedGenes, color);
	}
	
	private void init(List<Gene> allModuleGenes, Set<Gene> checkedGenes, Color color){
		this.color = color;
		
		Boolean[][] data = new Boolean[allModuleGenes.size()][1];
		
		for (int i = 0; i<data.length; i++){
			data[i][0] = checkedGenes.contains(allModuleGenes.get(i));
		}
		
		this.col = new ColorMatrix<Boolean>(data, this); 
		addChildElement(col);
		this.addMouseListener(new ElementEventChildForwarder(this));		
	}




	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return col.getDimension(g);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return col.paint(g, xOffset, yOffset);
	}

	@Override
	public Color getColor(Boolean element) {
		return element? this.color:Color.WHITE;
	}

	

}
