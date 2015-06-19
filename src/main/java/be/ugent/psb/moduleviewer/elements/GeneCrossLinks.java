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
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.ColoredAnnotation;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.GeneNotFoundException;
import be.ugent.psb.moduleviewer.model.UnknownItemException;

/**
 * Edges between regulators and genes.
 * 
 * @author Thomas Van Parys
 *
 */
public class GeneCrossLinks extends ConnectArrows {

	private GeneNode regTree;
	private GeneNode geneTree;
	private boolean rainbow;

	public GeneCrossLinks(){	
	}
	
	/**
	 * 
	 * @param geneTree list of genes. (regulators should be set at later stage)
	 * @param gap The gap (in px) between regulators and genes
	 */
	public GeneCrossLinks(GeneNode geneTree, int gap){
		this.geneTree = geneTree;
		this.setGap(gap);
	}
	public GeneCrossLinks(GeneNode regulatorTree, GeneNode geneTree, int gap) {
		this.regTree = regulatorTree;
		this.geneTree = geneTree;
		this.setGap(gap);
	}
	
	public void setRegGenes(GeneNode regTree) {
		this.regTree = regTree;
	}
	public void setGenes(GeneNode geneTree){
		this.geneTree = geneTree;
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
				//in this case, the name of the block is the from gene (regulator)
				Gene reg = an.getNameAsGene();
				int regCount = regTree.getWidth();
				if (rainbow){
					ColoredAnnotation<Gene> can = (ColoredAnnotation<Gene>)an;
					Set<Gene> toSet = can.getItems();
					for (Gene toGene : toSet){
						Color color = can.getColor(toGene);
						this.addEdge(regTree.getGeneLocation(reg), 
								geneTree.getGeneLocation(toGene)+regCount, color);					
					}
				} else {
					Set<Gene> toSet = an.getItems();
					for (Gene toGene : toSet){
						this.addEdge(regTree.getGeneLocation(reg), 
								geneTree.getGeneLocation(toGene));					
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
