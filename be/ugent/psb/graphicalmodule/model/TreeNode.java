package be.ugent.psb.graphicalmodule.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class TreeNode implements ITreeNode<Experiment>{
	/**
	 * Left child
	 */
	private TreeNode left;
	/**
	 * Right child
	 */
	private TreeNode right;
	
	/**
	 * True is this node doesn't have any children.
	 */
	private boolean isLeaf;
	
	/**
	 * Parent node, for navigating the tree upwards.
	 */
	private TreeNode parent;
	
	//TODO split?
	
	/**
	 * The experiments grouped into this part of the tree
	 */
	List<Experiment> experiments;
	
	
	
	
	
	public TreeNode() {
	}

	public TreeNode(TreeNode parent) {
		this.parent = parent;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public List<Experiment> getExperiments() {
		return experiments;
	}

	public void setExperiments(List<Experiment> experiments) {
		this.experiments = experiments;
	}

	@Override
	public ITreeNode<Experiment> left() {
		return left;
	}

	@Override
	public ITreeNode<Experiment> right() {
		return right;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public int getWidth() {
		if (isLeaf) return experiments.size();
		else return left.getWidth() + right.getWidth();
	}

	@Override
	public List<Experiment> getColumns() {
		if (isLeaf) return experiments;
		else {
			List<Experiment> cols = new ArrayList<Experiment>();
			cols.addAll(left.getColumns());
			cols.addAll(right.getColumns());
			return cols;
		}
	}
	
	public Experiment getExperiment(int expNumber){
		return getColumns().get(expNumber);
	}
	
}
