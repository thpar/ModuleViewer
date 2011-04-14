package be.ugent.psb.graphicalmodule.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.ugent.psb.ModuleNetwork.Gene;
import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Colorizer;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;

/**
 * The tickbox matrix simply checks if a genes is present in a list and colors
 * it if it does.
 * 
 * @author thpar
 *
 */
public class TickBoxColumn extends Element implements Colorizer<Boolean>{


	private Color color;
	private Matrix<Boolean> col;

	
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
		
		this.col = new Matrix<Boolean>(data, this); 
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
