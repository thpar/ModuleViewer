package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.awt.Dimension;

import be.ugent.psb.modulegraphics.elements.ColorMatrix;
import be.ugent.psb.modulegraphics.elements.LabelList;
import be.ugent.psb.modulegraphics.elements.NumberMatrix;
import be.ugent.psb.modulegraphics.elements.PassThroughColorizer;
import be.ugent.psb.modulegraphics.elements.SimpleColorizer;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.NumberedAnnotation;



public class GeneAnnotationMatrix extends AnnotationMatrix<Gene> {

	private AnnotationBlock<Gene> ab;
	private GeneNode geneRoot;
	
	enum LabelPosition{
		TOP, BOTTOM, NONE;
	}
	
	public GeneAnnotationMatrix(GeneNode geneRoot, AnnotationBlock<Gene> ab){
		this(geneRoot, ab, LabelPosition.BOTTOM);
	}
	
	public GeneAnnotationMatrix(GeneNode geneRoot, AnnotationBlock<Gene> ab, LabelPosition labelPosition){
		this.ab = ab;
		this.geneRoot = geneRoot;
		
		//create matrix
		switch(ab.getValueType()){
		case COLOR:
			createColoredMatrix();
			break;
		case NONE:
			createBooleanMatrix();			
			break;
		case NUMBER:
			createNumberedMatrix();			
			break;
		}
		
		LabelList labelList = new LabelList(labels);
		
		switch(labelPosition){
		default:
		case BOTTOM:
			labelList.setrAngle(LabelList.ReadingAngle.LEFT);
			labelList.setDir(LabelList.Direction.LEFT_TO_RIGHT);
			labelList.setLabelAlignment(LabelList.Alignment.TOP);
			labelList.setAlignment(Alignment.TOP_CENTER);
			break;
		case TOP:
			labelList.setrAngle(LabelList.ReadingAngle.LEFT);
			labelList.setDir(LabelList.Direction.LEFT_TO_RIGHT);
			labelList.setLabelAlignment(LabelList.Alignment.BOTTOM);
			labelList.setAlignment(Alignment.BOTTOM_CENTER);
			break;
		case NONE:
			break;
		}
		
		if (labelPosition==LabelPosition.TOP){
			this.add(labelList);			
			this.newRow();
			this.add(new Spacer(new Dimension(0,10)));
			this.newRow();
		}
		this.add(matrix);
		if (labelPosition==LabelPosition.BOTTOM){
			this.newRow();
			this.add(new Spacer(new Dimension(0,10)));
			this.newRow();
			this.add(labelList);			
		}
	}
	
	
	
	
	private void createNumberedMatrix() {
		int numberOfGenes = geneRoot.getWidth();
		Integer data[][] = new Integer[numberOfGenes][ab.size()];

		int anCount = 0;
		for (Annotation<Gene> an : ab.getAnnotations()){
			NumberedAnnotation<Gene> numberAn = (NumberedAnnotation<Gene>)an; 
			for (int i=0; i<numberOfGenes; i++){
				Gene gene = geneRoot.getGene(i);
				if (an.hasItem(gene)){
					data[i][anCount] = numberAn.getNumber(gene);
				} else {
					data[i][anCount] = null;
				}
			}
			labels.add(an.getName());
			anCount++;
		}

		matrix = new NumberMatrix(data);
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
		matrix = new ColorMatrix<Color>(data, c);
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
		matrix = new ColorMatrix<Boolean>(data, c);
	}


	
}
