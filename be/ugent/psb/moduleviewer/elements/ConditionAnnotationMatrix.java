package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import be.ugent.psb.ModuleNetwork.ConditionClassification;
import be.ugent.psb.ModuleNetwork.Experiment;
import be.ugent.psb.ModuleNetwork.TreeNode;
import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Colorizer;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;


public class ConditionAnnotationMatrix extends Canvas implements Colorizer<String>{

	
	private ConditionClassification classification;
	private List<Experiment> conditions;
	private TreeNode rootNode;
	private boolean recursive;


	/**
	 * 
	 * @param rootNode
	 * @param classification
	 * @param conditions
	 * @param recursive traverse the children of the node recursively
	 */
	public ConditionAnnotationMatrix(TreeNode rootNode, ConditionClassification classification, 
			List<Experiment> conditions, boolean recursive){
		this.classification = classification;
		this.conditions = conditions;
		this.rootNode = rootNode;
		this.recursive = recursive;
		compose();
	}
	
	private void compose(){
		if (recursive){
			addLeaves(rootNode);
		} else {
			Matrix<String> matrix = createMatrix(rootNode);
			this.add(matrix);
		}
	}
	
	
	private void addLeaves(TreeNode node) {
		if (node.nodeStatus.equals("internal")){
			addLeaves(node.leftChild);
			addLeaves(node.rightChild);
		} else {
			Matrix<String> matrix = createMatrix(node);
			this.add(matrix);
		}
	}


	private Matrix<String> createMatrix(TreeNode node) {
		Collections.sort(node.leafDistribution.condSet);
		
		String[][] data = new String[classification.getClasses().size()][node.leafDistribution.condSet.size()];
		
		int i=0; int j=0;
		for (String clas: classification.getClasses()){
			j=0;
			for (int c : node.leafDistribution.condSet){
				String name = this.conditions.get(c).name;
				String prop = classification.getProperty(name, clas);
				data[i][j++] = prop;
			}
			i++;
		}
		return new Matrix<String>(data, this);
	}


	@Override
	public Color getColor(String element) {
		return classification.getPropertyColor(element);
	}
	
	
	public String getHitAnnotation(int x, int y){
		Element el = getHitChild(x, y);
		assert(el instanceof Matrix);
		Matrix<String> mat = (Matrix<String>)el;
		String data = mat.getHitData(x, y);
		return data;
	}
	
}
