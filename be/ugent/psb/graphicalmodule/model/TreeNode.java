package be.ugent.psb.graphicalmodule.model;

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
		return experiments.size();
	}

	@Override
	public List<Experiment> getColumns() {
		return experiments;
	}

	
}
