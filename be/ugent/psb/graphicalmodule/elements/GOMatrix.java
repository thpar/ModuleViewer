package be.ugent.psb.graphicalmodule.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.LinkedHashSet;
import java.util.List;

import be.ugent.psb.ModuleNetwork.Gene;
import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Colorizer;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;
import cytoscape.data.annotation.OntologyTerm;

public class GOMatrix extends Element implements Colorizer<Boolean>{

	private Matrix<Boolean> matrix;

	public GOMatrix(List<Gene> genes, LinkedHashSet<OntologyTerm> goTerms) {
		Boolean[][] data = new Boolean[genes.size()][goTerms.size()];
		int i = 0; int j = 0;
		for (Gene gene : genes){
			j = 0;
			for (OntologyTerm term : goTerms){
					data[i][j++] = gene.GOcat.contains(term);
			}
			i++;
		}
		this.matrix = new Matrix<Boolean>(data, this); 
		addChildElement(matrix);
		this.addMouseListener(new ElementEventChildForwarder(this));
	}
	
	
	
	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return matrix.getDimension(g);
	}

	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return matrix.paint(g, xOffset, yOffset);
	}

	@Override
	public Color getColor(Boolean element) {
		return element? Color.ORANGE:Color.WHITE;
	}

	

}
