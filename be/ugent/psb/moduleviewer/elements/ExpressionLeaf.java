package be.ugent.psb.moduleviewer.elements;

import java.awt.Point;
import java.util.List;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Matrix;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;

public class ExpressionLeaf extends Canvas{
	
	

	private List<Condition> condSet;
	private ExpressionColorizer c;

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
		Matrix<Double> matrix = new Matrix<Double>(data, c);
		this.add(matrix);
		this.newRow();
	}

	
	
	/**
	 * Returns the location in the data matrix that has been hit.
	 * 
	 * @param x
	 * @param y
	 * @return a {@link Point} with {@link Point}.x the hit column and {@link Point}.y the hit row.  
	 */
	public Point getHitCoordinates(int x, int y){
		Matrix<Double> hitMatrix = (Matrix<Double>)this.getHitChild(x, y);
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
		Matrix<Double> hitMatrix = (Matrix<Double>)this.getHitChild(x, y);
		return hitMatrix.getHitData(x, y);
	}


	
	
}
