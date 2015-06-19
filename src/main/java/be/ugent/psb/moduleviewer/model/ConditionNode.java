package be.ugent.psb.moduleviewer.model;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
	
	
	/**
	 * The conditions grouped into this part of the tree.
	 * This attribute is not null only for leave nodes. Internal nodes just
	 * take the concatenation of the conditions of their children.
	 */
	private List<Condition> conditions = new ArrayList<Condition>();
	
	
	
	
	
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
	
	/**
	 * Same as {@link getColumns}
	 * @return
	 */
	public List<Condition> getConditions() {
		return getColumns();
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
	
	/**
	 * Get the condition in the given columns, counted over all nodes.
	 * This method does NOT relate to the number assigned to the conditions. 
	 * 
	 * @param condNumber
	 * @return
	 */
	public Condition getCondition(int condNumber){
		return getColumns().get(condNumber);
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Override
	public List<ITreeNode<Condition>> getLeaves() {
		List<ITreeNode<Condition>> list = new ArrayList<ITreeNode<Condition>>();
		getLeaves(list);
		return list;
	}
	
	private void getLeaves(List<ITreeNode<Condition>> list){
		if (isLeaf()) {
			list.add(this);
			return;
		} else {
			left.getLeaves(list);
			right.getLeaves(list);
			return;
		}		
	}

	public void addCondition(Condition condition) {
		this.conditions.add(condition);
	}
	
	public class ConditionComparator implements Comparator<Condition>{

		private Map<Condition, Double> condMeanMap;

		public ConditionComparator(Map<Condition, Double> condMeanMap){
			this.condMeanMap = condMeanMap;
		}
		
		@Override
		public int compare(Condition o1, Condition o2) {
			double mean1 = condMeanMap.get(o1);
			double mean2 = condMeanMap.get(o2);
			if (mean1 == mean2){
				return 0;
			} else if (mean1<mean2){
				return -1;
			} else {
				return 1;
			}
		}
		
	}
	
	/**
	 * Traverse the tree recursively and sort the conditions in the leaves from low to high 
	 * according the mean expression in the module.
	 */
	public void sortLeavesByMeanExpression(Map<Condition, Double> condMeanMap){
		ConditionComparator comparator = new ConditionComparator(condMeanMap);
		this.sortLeavesByMeanExpression(this, comparator);
	}
	private void sortLeavesByMeanExpression(ConditionNode node, ConditionComparator comparator){
		if (node.isLeaf){
			Collections.sort(node.conditions, comparator);
		} else {
			sortLeavesByMeanExpression(node.left, comparator);
			sortLeavesByMeanExpression(node.right, comparator);
		}
	}

	@Override
	public ConditionNode clone(){
		return clone(this);
	}
	private ConditionNode clone(ConditionNode oldNode){
		ConditionNode newNode = new ConditionNode();
		if (oldNode.isLeaf){
			for (Condition condition : oldNode.conditions){
				newNode.addCondition(condition);
				newNode.setLeaf(true);
			}
		} else {
			newNode.left = clone(oldNode.left);
			newNode.right = clone(oldNode.right);
		}
		return newNode;
	}
	
	
	
}
