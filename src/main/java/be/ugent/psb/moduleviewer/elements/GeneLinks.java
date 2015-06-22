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
import be.ugent.psb.moduleviewer.model.UnknownItemException;



/**
 * This element takes a list of {@link Gene}s, grouped into a {@link GeneNode}
 * and a {@link ColoredAnnotationBlock} that contains the from/to information and edge colors.
 * 
 * 
 * @author Thomas Van Parys
 *
 */
public class GeneLinks extends ConnectArrows{

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
	
	public GeneLinks(GeneNode geneRoot) {
		this.setGenes(geneRoot);
	}
	
	public void setGenes(GeneNode geneRoot) {
		this.geneRoot = geneRoot;
		this.setFixedNumber(geneRoot.getWidth());
	}
	
	
	public void setAnnotationBlock(AnnotationBlock<Gene> gab) {
		this.reset();
		
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
