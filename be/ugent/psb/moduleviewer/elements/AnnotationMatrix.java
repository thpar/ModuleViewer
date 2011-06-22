package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.Matrix;
import be.ugent.psb.modulegraphics.elements.SimpleColorizer;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;


public class AnnotationMatrix extends Element {

	private AnnotationBlock ab;
	private Matrix<Boolean> matrix;
	private GeneNode geneRoot;
	
	
	public AnnotationMatrix(GeneNode geneRoot, AnnotationBlock ab){
		this.ab = ab;
		this.geneRoot = geneRoot;
		constructMatrix();
	}
	
	
	
	
	private void constructMatrix() {
		//important to know if this is a horizontal or vertical matrix...
		DataType annotType = ab.getType();
		
		int numberOfGenes = geneRoot.getWidth();
		Boolean data[][] = new Boolean[numberOfGenes][ab.size()];
		List<String> labels = new ArrayList<String>();
		
		switch(annotType){
		case GENES:
			int anCount = 0;
			for (Annotation<?> an : ab.getAnnotations()){
				Annotation<Gene> geneAnnot = (Annotation<Gene>)an;
				labels.add(an.getName());
				for (int i=0; i<numberOfGenes; i++){
					Gene gene = geneRoot.getGene(i);
					if (geneAnnot.hasItem(gene)){
						data[i][anCount] = true;
					} else {
						data[i][anCount] = false;
					}
				}
				anCount++;
			}
			Color color = ab.getColor();
			SimpleColorizer c = new SimpleColorizer(color);
			matrix = new Matrix<Boolean>(data, c);
			matrix.setParentElement(this);
			break;
		case CONDITIONS:
			break;
		default: break;

		}

	}




	@Override
	protected Dimension paintElement(Graphics2D g, int xOffset, int yOffset) {
		return matrix.paint(g, xOffset, yOffset);
	}

	@Override
	protected Dimension getRawDimension(Graphics2D g) {
		return matrix.getDimension(g);
	}

	
	
}
