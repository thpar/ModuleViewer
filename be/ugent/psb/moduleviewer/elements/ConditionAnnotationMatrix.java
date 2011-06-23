package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;

import be.ugent.psb.modulegraphics.elements.Matrix;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.SimpleColorizer;
import be.ugent.psb.moduleviewer.elements.AnnotationMatrix;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.ConditionNode;


//TODO rotate matrix for conditions!!!!

public class ConditionAnnotationMatrix extends AnnotationMatrix<Condition> {

	private AnnotationBlock<Condition> ab;
	private ConditionNode condRoot;
		
	public ConditionAnnotationMatrix(ConditionNode condRoot, AnnotationBlock<Condition> ab){
		this.ab = ab;
		this.condRoot = condRoot;
		
		if (ab.isItemSpecificColored()){
			constructColoredMatrix();
		} else {
			constructBooleanMatrix();			
		}
	}
	
	
	private void constructColoredMatrix() {
		int numberOfConditions = condRoot.getWidth();
		Color data[][] = new Color[numberOfConditions][ab.size()];

		int anCount = 0;
		for (Annotation<Condition> an : ab.getAnnotations()){
			ColoredAnnotation<Condition> colorAn = (ColoredAnnotation<Condition>)an; 
			for (int i=0; i<numberOfConditions; i++){
				Condition cond = condRoot.getCondition(i);
				if (an.hasItem(cond)){
					data[i][anCount] = colorAn.getColor(cond);
				} else {
					data[i][anCount] = null;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		PassThroughColorizer c = new PassThroughColorizer();
		matrix = new Matrix<Color>(data, c);
		matrix.setParentElement(this);
	}




	private void constructBooleanMatrix() {

		int numberOfConditions = condRoot.getWidth();
		Boolean data[][] = new Boolean[numberOfConditions][ab.size()];

		int anCount = 0;
		for (Annotation<Condition> an : ab.getAnnotations()){
			for (int i=0; i<numberOfConditions; i++){
				Condition cond = condRoot.getCondition(i);
				if (an.hasItem(cond)){
					data[i][anCount] = true;
				} else {
					data[i][anCount] = false;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		Color color = ab.getColor();
		SimpleColorizer c = new SimpleColorizer(color);
		matrix = new Matrix<Boolean>(data, c);
		matrix.setParentElement(this);
	}
	
	
}
