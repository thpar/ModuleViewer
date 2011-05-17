package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class GeneNode implements ITreeNode<Gene>{
	/**
	 * Left child
	 */
	private GeneNode left;
	/**
	 * Right child
	 */
	private GeneNode right;
	
	/**
	 * True is this node doesn't have any children.
	 */
	private boolean isLeaf = false;
	
	/**
	 * Parent node, for navigating the tree upwards.
	 */
	private GeneNode parent;
	
	
	/**
	 * The genes grouped into this part of the tree.
	 * This attribute is not null only for leave nodes. Internal nodes just
	 * take the concatenation of the conditions of their children.
	 */
	List<Gene> genes = new ArrayList<Gene>();
	
	
	
	
	
	public GeneNode() {
	}

	public GeneNode(GeneNode parent) {
		this.parent = parent;
	}

	public GeneNode getParent() {
		return parent;
	}

	public void setLeft(GeneNode left) {
		this.left = left;
	}

	public void setRight(GeneNode right) {
		this.right = right;
	}
	
	/**
	 * Same as {@link getColumns}
	 * @return
	 */
	public List<Gene> getConditions() {
		return getColumns();
	}

	public void setConditions(List<Gene> genes) {
		this.genes = genes;
	}

	@Override
	public ITreeNode<Gene> left() {
		return left;
	}

	@Override
	public ITreeNode<Gene> right() {
		return right;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public int getWidth() {
		if (isLeaf) return genes.size();
		else return left.getWidth() + right.getWidth();
	}

	@Override
	public List<Gene> getColumns() {
		if (isLeaf) return genes;
		else {
			List<Gene> cols = new ArrayList<Gene>();
			cols.addAll(left.getColumns());
			cols.addAll(right.getColumns());
			return cols;
		}
	}
	
	/**
	 * Get the condition in the given columns, counted over all nodes.
	 * This method does NOT relate to the number assigned to the conditions. 
	 * 
	 * @param geneNumber
	 * @return
	 */
	public Gene getCondition(int geneNumber){
		return getColumns().get(geneNumber);
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public List<ITreeNode<Gene>> getLeaves() {
		List<ITreeNode<Gene>> list = new ArrayList<ITreeNode<Gene>>();
		getLeaves(list);
		return list;
	}
	
	private void getLeaves(List<ITreeNode<Gene>> list){
		if (isLeaf()) {
			list.add(this);
			return;
		} else {
			left.getLeaves(list);
			right.getLeaves(list);
			return;
		}		
	}

	public void addCondition(Gene gene) {
		this.genes.add(gene);
	}
	
}
