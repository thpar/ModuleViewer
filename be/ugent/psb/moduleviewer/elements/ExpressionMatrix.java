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
import be.ugent.psb.moduleviewer.model.GeneNode;

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
	private ConditionNode conditionRoot;
	private GeneNode geneRoot;
	
	private boolean recursive;
	private ExpressionColorizer colorizer;

	private boolean drawGeneSeparators = true;
	
	
	/**
	 * 
	 * @param genes
	 * @param conditionRoot
	 * @param mean
	 * @param sigma
	 * @param recursive traverse the children of the node recursively
	 */
	public ExpressionMatrix(GeneNode geneRoot, ConditionNode conditionRoot, ExpressionColorizer colorizer, 
			boolean recursive, boolean drawGeneSeparators){
		this.conditionRoot = conditionRoot;
		this.geneRoot = geneRoot;
		this.colorizer = colorizer;
		this.recursive = recursive;
		this.drawGeneSeparators = drawGeneSeparators;
		compose();
	}
	


	/**
	 * Construct all sub Elements and put them on this Canvas.
	 */
	private void compose(){
		if (recursive){ 
			addLeaves(conditionRoot);
		} else{ 
			ExpressionLeaf leaf = new ExpressionLeaf(geneRoot,
					conditionRoot.getColumns(),
					this.colorizer);
			leaf.setDrawGeneSeparators(drawGeneSeparators);
			this.add(leaf);
			leaves.add(leaf);
		}
		
	}



	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		//paint the canvas as constructed in compose()
		Dimension drawnDim = super.paintElement(g, xOffset, yOffset);
		
		//draw lines between condition leaves
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
			ExpressionLeaf leaf = new ExpressionLeaf(geneRoot,
					node.getColumns(),
					this.colorizer);
			this.add(leaf);
			leaf.setDrawGeneSeparators(drawGeneSeparators);
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
