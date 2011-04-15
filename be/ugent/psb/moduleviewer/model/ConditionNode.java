package be.ugent.psb.moduleviewer.model;

import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.ITreeNode;

public class ConditionNode implements ITreeNode<Condition>{
	/**
	 * Left child
	 */
	private ConditionNode left;
	/**
	 * Right child
	 */
	private ConditionNode right;
	
	/**
	 * True is this node doesn't have any children.
	 */
	private boolean isLeaf = false;
	
	/**
	 * Parent node, for navigating the tree upwards.
	 */
	private ConditionNode parent;
	
	//TODO split?
	
	/**
	 * The conditions grouped into this part of the tree.
	 * This attribute is not null only for leave nodes. Internal nodes just
	 * take the concatenation of the conditions of their children.
	 */
	List<Condition> conditions;
	
	
	
	
	
	public ConditionNode() {
	}

	public ConditionNode(ConditionNode parent) {
		this.parent = parent;
	}

	public ConditionNode getParent() {
		return parent;
	}

	public void setLeft(ConditionNode left) {
		this.left = left;
	}

	public void setRight(ConditionNode right) {
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

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
}
