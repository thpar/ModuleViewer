package be.ugent.psb.graphicalmodule.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class TreeNode implements ITreeNode<Condition>{
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
	 * The conditions grouped into this part of the tree
	 */
	List<Condition> conditions;
	
	
	
	
	
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

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public ITreeNode<Condition> left() {
		return left;
	}

	@Override
	public ITreeNode<Condition> right() {
		return right;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public int getWidth() {
		if (isLeaf) return conditions.size();
		else return left.getWidth() + right.getWidth();
	}

	@Override
	public List<Condition> getColumns() {
		if (isLeaf) return conditions;
		else {
			List<Condition> cols = new ArrayList<Condition>();
			cols.addAll(left.getColumns());
			cols.addAll(right.getColumns());
			return cols;
		}
	}
	
	public Condition getCondition(int condNumber){
		return getColumns().get(condNumber);
	}
	
}
