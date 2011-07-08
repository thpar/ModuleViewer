package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.Matrix;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.SimpleColorizer;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;

public class ConditionAnnotationMatrix extends AnnotationMatrix<Condition> {

	private AnnotationBlock<Condition> ab;
	private ConditionNode condRoot;
	private List<Condition> nonTreeConditions;
		
	public ConditionAnnotationMatrix(ConditionNode condRoot, List<Condition> nonTreeConditions, AnnotationBlock<Condition> ab){
		this.ab = ab;
		this.condRoot = condRoot;
		this.nonTreeConditions = nonTreeConditions;
		this.setHorizontalSpacing(5);
		this.setVerticalSpacing(5);
		
		if (ab.isItemSpecificColored()){
			constructColoredMatrix();
		} else {
			constructBooleanMatrix();			
		}
		
		LabelList labelList = new LabelList(labels);
		labelList.setDir(LabelList.Direction.TOP_TO_BOTTOM);
		this.add(labelList);
	}
	
	
	private void constructColoredMatrix() {
		int numberOfConditions = condRoot.getWidth();
		int numberOfNonTreeConditions = nonTreeConditions.size();
		Color data[][] = new Color[ab.size()][numberOfConditions + numberOfNonTreeConditions];

		int anCount = 0;
		for (Annotation<Condition> an : ab.getAnnotations()){
			ColoredAnnotation<Condition> colorAn = (ColoredAnnotation<Condition>)an; 
			for (int i=0; i<numberOfConditions; i++){
				Condition cond = condRoot.getCondition(i);
				if (an.hasItem(cond)){
					data[anCount][i] = colorAn.getColor(cond);
				} else {
					data[anCount][i] = null;
				}
			}
			for (int i=0; i<numberOfNonTreeConditions; i++){
				Condition cond = nonTreeConditions.get(i);
				if (an.hasItem(cond)){
					data[anCount][i+numberOfConditions] = colorAn.getColor(cond);
				} else {
					data[anCount][i+numberOfConditions] = null;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		PassThroughColorizer c = new PassThroughColorizer();
		matrix = new Matrix<Color>(data, c);
		this.add(matrix);
	}




	private void constructBooleanMatrix() {

		int numberOfConditions = condRoot.getWidth();
		int numberOfNonTreeConditions = nonTreeConditions.size();

		Boolean data[][] = new Boolean[ab.size()][numberOfConditions + numberOfNonTreeConditions];

		int anCount = 0;
		for (Annotation<Condition> an : ab.getAnnotations()){
			for (int i=0; i<numberOfConditions; i++){
				Condition cond = condRoot.getCondition(i);
				if (an.hasItem(cond)){
					data[anCount][i] = true;
				} else {
					data[anCount][i] = false;
				}
			}
			for (int i=0; i<numberOfNonTreeConditions; i++){
				Condition cond = nonTreeConditions.get(i);
				if (an.hasItem(cond)){
					data[anCount][i+numberOfConditions] = true;
				} else {
					data[anCount][i+numberOfConditions] = false;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		Color color = ab.getColor();
		SimpleColorizer c = new SimpleColorizer(color);
		matrix = new Matrix<Boolean>(data, c);
		this.add(matrix);
	}
	
	
}
