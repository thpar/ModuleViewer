package be.ugent.psb.moduleviewer.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ITreeNode;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;
import be.ugent.psb.moduleviewer.model.Gene;

/**
 * Iterates {@link ConditionNode}s and constructs the Expression Matrix on a {@link Canvas}
 * 
 * @author thpar
 *
 */
public class ExpressionMatrix extends Canvas {
	
	/**
	 * a list of leaves, in order to be able to change their settings after creation
	 */
	private List<ExpressionLeaf> leaves = new ArrayList<ExpressionLeaf>();
	private ConditionNode rootNode;
	private double mean;
	private double sigma;
	private List<Gene> genes;
	private boolean recursive;

	/**
	 * 
	 * @param genes
	 * @param rootNode
	 * @param mean
	 * @param sigma
	 * @param recursive traverse the children of the node recursively
	 */
	public ExpressionMatrix(List<Gene> genes, ConditionNode rootNode, double mean, double sigma, boolean recursive){
		this.genes = genes;
		this.rootNode = rootNode;
		this.mean = mean;
		this.sigma = sigma;
		this.recursive = recursive;
		compose();
	}
	


	/**
	 * Construct all sub Elements and put them on this Canvas.
	 */
	private void compose(){
		if (recursive){ 
			addLeaves(rootNode);
		} else{ 
			ExpressionLeaf leaf = new ExpressionLeaf(genes,
					rootNode.getColumns(),
					this.mean, this.sigma);
			this.add(leaf);
			leaves.add(leaf);
		}
		
	}

	public void setMeanSigma(double mean, double sigma){
		this.mean = mean;
		this.sigma = sigma;
		
		for (ExpressionLeaf l : leaves){
			l.setMean(mean);
			l.setSigma(sigma);
		}
		
	}
	


	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		//paint the canvas as constructed in compose()
		Dimension drawnDim = super.paintElement(g, xOffset, yOffset);
		
		//draw lines between leaves
		int x = 0;
		int leafCount = 0;
		g.setColor(Color.MAGENTA);
		g.setStroke(new BasicStroke(1.5F));
		for (ExpressionLeaf leaf : leaves){
			if (leafCount<leaves.size()-1){
				x+=leaf.getDimension(g).width;
				g.drawLine(x + xOffset, yOffset, 
						x + xOffset, yOffset+drawnDim.height);
				leafCount++;
			}
		}
		
		return drawnDim;
	}

	

	/**
	 * 
	 * @param node
	 * @param x
	 * @return width of the drawn leave
	 */
	private void addLeaves(ITreeNode<Condition> node) {
		if (!node.isLeaf()){
			addLeaves(node.left());
			addLeaves(node.right());
		} else {
			ExpressionLeaf leaf = new ExpressionLeaf(genes,
					node.getColumns(),
					this.mean, this.sigma );
			this.add(leaf);
			this.leaves.add(leaf);
		}
	}
	
	
	public double getHitData(int x, int y){
		double data=-1;
		Element childElement = getHitChild(x, y);
		if (childElement instanceof ExpressionLeaf){
			ExpressionLeaf hitLeaf = (ExpressionLeaf)childElement;
			data = hitLeaf.getHitData(x, y);
		}
		return data;
	}
	
	public Point getHitCoordinates(int x, int y){
		Element childElement = getHitChild(x, y);
		if (childElement instanceof ExpressionLeaf){
			ExpressionLeaf hitLeaf = (ExpressionLeaf)childElement;
			return hitLeaf.getHitCoordinates(x, y);
		}
		return new Point(-1,-1);
	}

}
