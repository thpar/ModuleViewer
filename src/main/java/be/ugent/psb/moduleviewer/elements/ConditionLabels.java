package be.ugent.psb.moduleviewer.elements;

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

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ITreeNode;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;
import be.ugent.psb.modulegraphics.elements.LabelList.ReadingAngle;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;

/**
 * Draw the names of Conditions under the expression matrix.
 * 
 * @author Thomas Van Parys
 *
 */
public class ConditionLabels extends Canvas {


	private ConditionNode rootNode;
	private List<Condition> nonTreeConditions;

	/**
	 * 
	 * @param rootNode
	 * @param conditions
	 * @param recursive traverse the children of the node recursively
	 */
	public ConditionLabels(ConditionNode rootNode, List<Condition> nonTreeConditions, boolean recursive){
		this.addMouseListener(new ElementEventChildForwarder(this));
		this.rootNode = rootNode;
		this.nonTreeConditions = nonTreeConditions;
		
		if (recursive){
			addLeaves(rootNode);
		} else {
			this.add(createLabelList(rootNode));
		}
		
		if (nonTreeConditions.size()>0){
			this.add(createLabelList(nonTreeConditions));
		}
		
	}
	
	
	
	private void addLeaves(ITreeNode<Condition> node) {
		if (!node.isLeaf()){
			addLeaves(node.left());
			addLeaves(node.right());
		} else {
			this.add(createLabelList(node));
		}
	}
	
	private LabelList createLabelList(ITreeNode<Condition> node){
		return createLabelList(node.getColumns());
	}
	
	private LabelList createLabelList(List<Condition> conds){
//		Collections.sort(node.getColumns());
		List<String> labelStrings = new ArrayList<String>();
		for (Condition condition : conds){
			labelStrings.add(condition.getName());
		}
		
		LabelList labels = new LabelList(labelStrings);
		
		labels.setFont(new Font("SansSerif", Font.PLAIN, 8));
		labels.setDirection(Direction.LEFT_TO_RIGHT);
		labels.setrAngle(ReadingAngle.LEFT);
		return labels;
	}
	
	public Condition getHitCondition(int x, int y){
		Element el = getHitChild(x, y);
		assert(el instanceof LabelList);
		LabelList ll = (LabelList)el;
		int row = ll.getHitLabelRow(x, y);
		return rootNode.getCondition(row);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public String getHitConditionName(int x, int y){
		Element el = getHitChild(x, y);
		if (el!=null){
			assert(el instanceof LabelList);
			LabelList ll = (LabelList)el;
			return ll.getHitLabelString(x, y);
		} else return new String();
	}
}
