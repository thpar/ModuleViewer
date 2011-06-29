package be.ugent.psb.moduleviewer.elements;

import java.awt.Canvas;
import java.awt.Color;

import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.SimpleColorizer;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;



public class GeneAnnotationMatrix extends AnnotationMatrix<Gene> {

	private AnnotationBlock<Gene> ab;
	private GeneNode geneRoot;
		
	public GeneAnnotationMatrix(GeneNode geneRoot, AnnotationBlock<Gene> ab){
		this.ab = ab;
		this.geneRoot = geneRoot;
		
		//create matrix
		if (ab.isItemSpecificColored()){
			createColoredMatrix();
		} else {
			createBooleanMatrix();			
		}

		//create labels (were scanned while creating matrix)
		LabelList labelList = new LabelList(labels);
		labelList.setrAngle(LabelList.ReadingAngle.RIGHT);
		labelList.setDir(LabelList.Direction.LEFT_TO_RIGHT);
//		labelList.setAlignment(Element.Alignment.BOTTOM_CENTER);
		
		//add stuff
		this.add(matrix);
		this.newRow();
		this.add(labelList);
	}
	
	
	
	
	private void createColoredMatrix() {
		int numberOfGenes = geneRoot.getWidth();
		Color data[][] = new Color[numberOfGenes][ab.size()];

		int anCount = 0;
		for (Annotation<Gene> an : ab.getAnnotations()){
			ColoredAnnotation<Gene> colorAn = (ColoredAnnotation<Gene>)an; 
			for (int i=0; i<numberOfGenes; i++){
				Gene gene = geneRoot.getGene(i);
				if (an.hasItem(gene)){
					data[i][anCount] = colorAn.getColor(gene);
				} else {
					data[i][anCount] = null;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		PassThroughColorizer c = new PassThroughColorizer();
		matrix = new Matrix<Color>(data, c);
//		this.add(matrix);
	}




	private void createBooleanMatrix() {

		int numberOfGenes = geneRoot.getWidth();
		Boolean data[][] = new Boolean[numberOfGenes][ab.size()];

		int anCount = 0;
		for (Annotation<Gene> an : ab.getAnnotations()){
			for (int i=0; i<numberOfGenes; i++){
				Gene gene = geneRoot.getGene(i);
				if (an.hasItem(gene)){
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
//		this.add(matrix);
	}




	
	
}
