package be.ugent.psb.graphicalmodule.model;

import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class TreeNode implements ITreeNode{
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
	
	
	
	@Override
	public ITreeNode left() {
		return left;
	}

	@Override
	public ITreeNode right() {
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

	
}
