package be.ugent.psb.graphicalmodule.elements;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.ugent.psb.ModuleNetwork.Experiment;
import be.ugent.psb.ModuleNetwork.TreeNode;
import be.ugent.psb.modulegraphics.clickable.ElementEventChildForwarder;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.LabelList.Direction;

/**
 * Draw the names of Conditions (experiments) under the expression matrix.
 * 
 * @author thpar
 *
 */
public class ConditionLabels extends Canvas {

	/**
	 * Conditions to look up condition name by condition number
	 */
	private List<Experiment> conditions;


	/**
	 * 
	 * @param rootNode
	 * @param conditions
	 * @param recursive traverse the children of the node recursively
	 */
	public ConditionLabels(TreeNode rootNode, List<Experiment> conditions, boolean recursive){
		this.conditions = conditions;
		this.addMouseListener(new ElementEventChildForwarder(this));
		
		if (recursive){
			addLeaves(rootNode);
		} else {
			this.add(createLabelList(rootNode));
		}
		
	}
	
	
	
	private void addLeaves(TreeNode node) {
		if (node.nodeStatus.equals("internal")){
			addLeaves(node.leftChild);
			addLeaves(node.rightChild);
		} else {
			this.add(createLabelList(node));
		}
	}
	
	private LabelList createLabelList(TreeNode node){
		Collections.sort(node.leafDistribution.condSet);
		List<String> labelStrings = new ArrayList<String>();
		for (int condNumber : node.leafDistribution.condSet){
			labelStrings.add(conditions.get(condNumber).name);
		}
		
		LabelList labels = new LabelList(labelStrings);
		
		labels.setFont(new Font("SansSerif", Font.PLAIN, 10));
		labels.setDirection(Direction.LEFT_TO_RIGHT);
		return labels;
	}
	
	public Experiment getHitExperiment(int x, int y){
		Element el = getHitChild(x, y);
		assert(el instanceof LabelList);
		LabelList ll = (LabelList)el;
		int row = ll.getHitLabelRow(x, y);
		return conditions.get(row);
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
