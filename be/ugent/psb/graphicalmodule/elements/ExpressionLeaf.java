package be.ugent.psb.graphicalmodule.elements;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Collections;
import java.util.List;

import be.ugent.psb.graphicalmodule.model.Experiment;
import be.ugent.psb.graphicalmodule.model.Gene;
import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;

public class ExpressionLeaf extends Element{

	private Matrix<Double> matrix;
	
	private ExpressionColorizer c;

	/**
	 * 
	 * @param genes list of genes in this module
	 * @param condSet the conditions represented in this leaf
	 * @param mean
	 * @param sigma
	 */
	public ExpressionLeaf(List<Gene> genes, List<Experiment> condSet, double mean, double sigma){
		c = new ExpressionColorizer(mean, sigma);
//		Collections.sort(condSet);

		this.addMouseListener(new ElementEventChildForwarder(this));
		
		Double[][] data = new Double[genes.size()][condSet.size()];
		int i=0;
		for (Gene gene : genes){
			int ii=0;
			for (Experiment exp : condSet){
				data[i][ii++] = gene.getValue(exp);
			}
			i++;
		}
		this.matrix = new Matrix<Double>(data, c);
		addChildElement(matrix);
	}
	

	public void setMean(double mean){
		c.setMean(mean);
	}
	public void setSigma(double sigma){
		c.setSigma(sigma);
	}


	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return matrix.getDimension(g);
	}


	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return matrix.paint(g, xOffset, yOffset);
	}
	
	
	/**
	 * Returns the location in the data matrix that has been hit.
	 * 
	 * @param x
	 * @param y
	 * @return a {@link Point} with {@link Point}.x the hit column and {@link Point}.y the hit row.  
	 */
	public Point getHitCoordinates(int x, int y){
		return matrix.getHitSquare(x, y);
	}
	
	/**
	 * Returns 
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Double getHitData(int x, int y){
		return matrix.getHitData(x, y);
	}


	
	
}
