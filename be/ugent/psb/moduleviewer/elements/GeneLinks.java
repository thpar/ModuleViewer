package be.ugent.psb.moduleviewer.elements;

import java.awt.Color;
import java.util.Set;

import be.ugent.psb.modulegraphics.elements.ConnectArrows;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.GeneNotFoundException;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.UnknownItemException;



/**
 * This element takes a list of {@link Gene}s, grouped into a {@link GeneNode}
 * and a {@link ColoredAnnotationBlock} that contains the from/to information and edge colors.
 * 
 * 
 * @author thpar
 *
 */
public class GeneLinks extends ConnectArrows{

	private AnnotationBlock<Gene> gab;
	private GeneNode geneRoot;
	
	/**
	 * if true, all edges can have their own color
	 */
	private boolean rainbow = false;
	
	
	/**
	 * Create place holder {@link Element}
	 */
	public GeneLinks(){
	}
	
	public GeneLinks(GeneNode geneRoot, AnnotationBlock<Gene> ab){
		this.geneRoot = geneRoot;
		this.setAnnotationBlock(ab);
	}
	
	public void setGenes(GeneNode regTree) {
		this.geneRoot = regTree;
	}
	
	
	public void setAnnotationBlock(AnnotationBlock<Gene> gab) {
		this.reset();
		
		this.gab = gab;
		//each annotation in the block contains a 'from' and a set of 'to' genes
		switch(gab.getValueType()){
		case COLOR:
			this.rainbow = true;
			break;
		default:
		case NUMBER:
		case NONE:
			this.rainbow = false;
			this.setColor(this.DEFAULT_SET, gab.getColor());
			break;
		}
		for (Annotation<Gene> an : gab.getAnnotations()){
			try {
				//in this case, the name of the block is the from gene
				Gene fromGene = an.getNameAsGene();
				if (rainbow){
					ColoredAnnotation<Gene> can = (ColoredAnnotation<Gene>)an;
					Set<Gene> toSet = can.getItems();
					for (Gene toGene : toSet){
						Color color = can.getColor(toGene);
						this.addEdge(geneRoot.getGeneLocation(fromGene), 
								geneRoot.getGeneLocation(toGene), color);					
					}
				} else {
					Set<Gene> toSet = an.getItems();
					for (Gene toGene : toSet){
						this.addEdge(geneRoot.getGeneLocation(fromGene), 
								geneRoot.getGeneLocation(toGene));					
					}
				}
			} catch (UnknownItemException e) {
				System.err.println("Unknown gene referenced in interaction list."
						+ gab.getBlockName()+" - "+an.getName());
			} catch (GeneNotFoundException e){
				System.err.println(e.getMessage());
			}
		}
	}


}
