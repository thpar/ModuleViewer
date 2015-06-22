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

import java.awt.Dimension;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ElementStack;
import be.ugent.psb.modulegraphics.elements.Label;
import be.ugent.psb.modulegraphics.elements.LegendGradient;
import be.ugent.psb.modulegraphics.elements.RelativeSpacer;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.modulegraphics.elements.TreeStructure;
import be.ugent.psb.modulegraphics.elements.UnitSpacer;
import be.ugent.psb.moduleviewer.elements.GeneAnnotationMatrix.LabelPosition;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.GeneNode;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * A Default layout for a Canvas that looks up which elements to 
 * draw in the graphical model of the module. It will try to put an expression matrix centrally
 * in the figure, with gene labels right of it, and condition names under it. 
 * When available, regulators will be drawn above the matrix. In the order supplied by 
 * the loaded MVF files, extra annotations will be drawn to the right, and possible 
 * links between genes as connecting arrows at the left.
 * 
 * When constructing drastically different figures, an other class should be used, extending Canvas. 
 * 
 * @author Thomas Van Parys
 *
 */
public class DefaultCanvas extends Canvas {

	private ModuleNetwork modnet;
	private Module mod;
	private GUIModel guiModel;
	private String title;
	private Model model;

	public DefaultCanvas(Module mod, Model model, GUIModel guiModel, String title){
		this.modnet = mod.getModuleNetwork();
		this.mod = mod;
		this.model = model;
		this.guiModel = guiModel;
		this.title = title;
		
		this.composeCanvas();

	}

	/**
	 * Check in the guiModel which components should be drawn, create them, feed them the 
	 * data from the model, and add them in the right order to this Canvas.
	 */
	private void composeCanvas() {
		//general settings
		this.setHorizontalSpacing(5);
		this.setVerticalSpacing(5);
		this.setMargin(20);
		
		
		//title
		if (guiModel.isDrawFileName()){
			Title titleElement = new Title(title);
			titleElement.setBottomMargin(10);
			titleElement.setAlignment(Alignment.TOP_CENTER);
			this.add(titleElement);
			this.newRow();
		}
		
		/**
		 * groups regulator matrix, expression matrix and gene names
		 */
		Canvas coreCanvas = new Canvas();
		coreCanvas.setHorizontalSpacing(5);
		coreCanvas.setVerticalSpacing(5);
		coreCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		
		boolean drawnTree = false;
		TreeStructure tree = null;
		if (guiModel.isDrawTreeStructure() && mod.getConditionTree().getLeaves().size()>1){
			tree = new TreeStructure(mod.getConditionTree());
			coreCanvas.add(tree);
			coreCanvas.newRow();
			drawnTree = true;
		}
		
		//arrow scaffolds
		ElementStack arrowStack = new ElementStack();
		
		Canvas regGeneArrowCanvas = new Canvas();
		regGeneArrowCanvas.setVerticalSpacing(5);
		if (drawnTree){
			regGeneArrowCanvas.add(new RelativeSpacer(null, tree));
			regGeneArrowCanvas.newRow();
		}
		ElementStack regGeneArrowPlaceHolder = new ElementStack();
		regGeneArrowPlaceHolder.setAlignment(Alignment.TOP_RIGHT);
		regGeneArrowCanvas.add(regGeneArrowPlaceHolder);
		regGeneArrowCanvas.setAlignment(Alignment.TOP_RIGHT);
		arrowStack.add(regGeneArrowCanvas);
		
		Canvas arrowCanvas = new Canvas();
		arrowCanvas.setVerticalSpacing(5);
		arrowCanvas.setAlignment(Alignment.TOP_RIGHT);
		arrowStack.add(arrowCanvas);
		if (drawnTree){
			arrowCanvas.add(new RelativeSpacer(null, tree));
			arrowCanvas.newRow();
		}
		ElementStack regArrowPlaceHolder = new ElementStack();
		regArrowPlaceHolder.setAlignment(Alignment.TOP_RIGHT);
		if (mod.getRegulatorTrees().size() > 0){
			regArrowPlaceHolder.add(new UnitSpacer(0, mod.getRegulatorTrees().get(0).getGenes().size()));			
			arrowCanvas.add(regArrowPlaceHolder);
			arrowCanvas.newRow();
			arrowCanvas.add(new Spacer(new Dimension(0,10)));			
			arrowCanvas.newRow();
		}
		ElementStack geneArrowPlaceHolder = new ElementStack();
		geneArrowPlaceHolder.setAlignment(Alignment.TOP_RIGHT);
		arrowCanvas.add(geneArrowPlaceHolder);
				
		
		//regulator genes
		GeneNames regNames = null;
		double regSigma = 0;
		double regMean = 0;
		if (model.getRegulatorFile()!=null && mod.getRegulatorTrees().size()>0){
			for (GeneNode regTree : mod.getRegulatorTrees()){
				if (regTree.getGenes().size()>0){
					switch(guiModel.getMeanScopeModNet()){
					case MODULE_WIDE:
						switch(guiModel.getMeanScopeGeneReg()){
						case REGS_GENES_JOINED:	
							regMean = mod.getMeanAll();
							regSigma = mod.getSigmaAll();
							break;
						case REGS_GENES_SEPARATE:
							regMean = regTree.getMean();
							regSigma = regTree.getSigma();
							break;
						}
						break;
					case NETWORK_WIDE:
						regSigma = modnet.getSigma();
						regMean = modnet.getMean();
						break;
					}
					ExpressionMatrix regulatorMatrix = new ExpressionMatrix(regTree,
							mod.getConditionTree(),
							mod.getNonTreeConditions(),
							new EnigmaColorizer(regSigma,regMean),
							true, false);
					coreCanvas.add(regulatorMatrix);

					regNames = new GeneNames(regTree);
					coreCanvas.add(regNames);

					coreCanvas.newRow();
					coreCanvas.add(new Spacer(new Dimension(0,10)));
					coreCanvas.newRow();
				}
			}
		}
		
		//expression matrix
		double geneSigma = 0;
		double geneMean = 0;
		switch(guiModel.getMeanScopeModNet()){
		case MODULE_WIDE:
			switch(guiModel.getMeanScopeGeneReg()){
			case REGS_GENES_JOINED:	
				geneMean = mod.getMeanAll();
				geneSigma = mod.getSigmaAll();
				break;
			case REGS_GENES_SEPARATE:
				geneMean = mod.getGeneTree().getMean();
				geneSigma = mod.getGeneTree().getSigma();
				break;
			}
			break;
		case NETWORK_WIDE:
			geneSigma = modnet.getSigma();
			geneMean = modnet.getMean();
			break;
		}
		
		
		ExpressionMatrix expressionMatrix = new ExpressionMatrix(mod.getGeneTree(),
				mod.getConditionTree(),
				mod.getNonTreeConditions(),
				new EnigmaColorizer(geneSigma,geneMean),
				true, true);
		coreCanvas.add(expressionMatrix);
		
		GeneNames geneNames = new GeneNames(mod.getGeneTree());
		coreCanvas.add(geneNames);
		
		Canvas leftCanvas = new Canvas();
		leftCanvas.setHorizontalSpacing(5);
		leftCanvas.setVerticalSpacing(5);
		leftCanvas.setAlignment(Alignment.TOP_LEFT);
		leftCanvas.add(arrowStack);
		leftCanvas.add(coreCanvas);
		
		//horizontal canvas contains two canvas: arrow/core and annotations (left/right)
		Canvas horizontalCanvas = new Canvas();
		horizontalCanvas.setHorizontalSpacing(25);
		horizontalCanvas.setVerticalSpacing(5);
		horizontalCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		
		
		horizontalCanvas.add(leftCanvas);
		
		
		Canvas rightCanvas = new Canvas();
		rightCanvas.setHorizontalSpacing(5);
		rightCanvas.setVerticalSpacing(5);
		rightCanvas.setAlignment(Alignment.TOP_LEFT);
		
		if (drawnTree){
			rightCanvas.add(new RelativeSpacer(null, tree));
			rightCanvas.newRow();
		}
		
		//only use for exactly one regulator tree
		//arrow blocks are not capable of handling more
		int regregBowCount = 2;
		int reggeneBowCount = 2;
		int genegeneBowCount = 2;
		int regulatorAnnotationBlocks = 0;
		if (mod.getRegulatorTrees().size()==1){
			List<AnnotationBlock<Gene>> rabList = mod.getAnnotationBlocks(DataType.REGULATORS);
			for (AnnotationBlock<Gene> rab : rabList){
				BlockType blockType = rab.getBlockType();
				switch(blockType){
				case regulatorcolorbox:
					Annotation<Gene> ansReg = rab.getAnnotation();
					regNames.colorBackgrounds(ansReg.getItems(), rab.getColor());
					break;
				case regulatorregulatorinteraction:
					GeneLinks regArrows = new GeneLinks();
					regArrows.setGenes(mod.getRegulatorTrees().get(0));
					regArrows.setAnnotationBlock(rab);
					regArrows.setBowWidth(regregBowCount+=2);
					regArrows.setAlignment(Alignment.TOP_RIGHT);
					regArrowPlaceHolder.add(regArrows);
					break;
				case regulatorgeneinteraction:				
					GeneCrossLinks regGeneArrows = new GeneCrossLinks();
					regGeneArrows.setGenes(mod.getGeneTree());
					regGeneArrows.setRegGenes(mod.getRegulatorTrees().get(0));
					regGeneArrows.setBowWidth(reggeneBowCount+=2);
					regGeneArrows.setAlignment(Alignment.TOP_RIGHT);
					regGeneArrows.setAnnotationBlock(rab);
					regGeneArrowPlaceHolder.add(regGeneArrows);
					break;
				case unknown:
				default:
					//add default annotation blocks next to the regulators
					//TODO for now, don't show labels for the regulator annotation matrices
					GeneAnnotationMatrix ganMatrix = new GeneAnnotationMatrix(mod.getRegulatorTrees().get(0), rab, LabelPosition.NONE);
					rightCanvas.add(ganMatrix);
					rightCanvas.getLastAddedElement().setAlignment(Alignment.TOP_CENTER);
					regulatorAnnotationBlocks++;
				}
			}
			//if we haven't added any actual regulator annotation blocks: add a spacer
			if (regulatorAnnotationBlocks==0){
				rightCanvas.add(new UnitSpacer(0, mod.getRegulatorTrees().get(0).getGenes().size()));
			}
			rightCanvas.newRow();
			rightCanvas.add(new Spacer(new Dimension(0,10)));
			rightCanvas.newRow();
		}
		
		
		
		//extra data (bingo, ...) from MVF files
		//this is the only place where the type of the block is considered.
		//this information can be used to decide to display a block different from 
		//a standard annotation block.
		List<AnnotationBlock<Gene>> gabList = mod.getAnnotationBlocks(DataType.GENES);
		List<AnnotationBlock<Gene>> globalGabList = modnet.getGlobalAnnotationBlocks(DataType.GENES);
		gabList.addAll(globalGabList);
		
		
		//annotation **BLOCKS**
		regregBowCount=2;
		for (AnnotationBlock<Gene> gab : gabList){
			BlockType blockType = gab.getBlockType();
			switch(blockType){
			case genecolorbox:
				Annotation<Gene> ansGene = gab.getAnnotation();
				geneNames.colorBackgrounds(ansGene.getItems(), gab.getColor());
				break;
			case genegeneinteraction:
				GeneLinks geneArrows = new GeneLinks();
				geneArrows.setGenes(mod.getGeneTree());
				geneArrows.setAnnotationBlock(gab);
				geneArrows.setBowWidth(genegeneBowCount+=2);
				geneArrows.setAlignment(Alignment.TOP_RIGHT);
				geneArrowPlaceHolder.add(geneArrows);
				break;
			case regulatorgeneinteraction:				
				//same processing as in REGULATOR blocks... depends on how the user defined this block
				GeneCrossLinks regGeneArrows = new GeneCrossLinks();
				regGeneArrows.setGenes(mod.getGeneTree());
				regGeneArrows.setRegGenes(mod.getRegulatorTrees().get(0));
				regGeneArrows.setBowWidth(reggeneBowCount+=2);
				regGeneArrows.setAlignment(Alignment.TOP_RIGHT);
				regGeneArrows.setAnnotationBlock(gab);
				regGeneArrowPlaceHolder.add(regGeneArrows);
				break;
			case bingo:
			case core:
			case modulelinks:
			case tfenrichment:
			case unknown:
			default:
				GeneAnnotationMatrix ganMatrix = new GeneAnnotationMatrix(mod.getGeneTree(), gab);
				rightCanvas.add(ganMatrix);
				rightCanvas.getLastAddedElement().setAlignment(Alignment.TOP_CENTER);				
				
			}
			
		}
		
		rightCanvas.newRow();
		
		//annotation **BLOCK LABELS**
		for(AnnotationBlock<Gene> gab : gabList){
			BlockType blockType = gab.getBlockType();
			switch(blockType){
			//special cases, process later
			case genecolorbox:
			case genegeneinteraction:
			case regulatorgeneinteraction:
				break;
				//annotation blocks
			case bingo:
			case core:
			case modulelinks:
			case tfenrichment:
			case unknown:
			default: 
				rightCanvas.add(new Label(gab.getBlockName()));
				Element lastLabel = rightCanvas.getLastAddedElement();
				lastLabel.setAlignment(Alignment.BOTTOM_CENTER);
				lastLabel.setMargin(10, 0, 0, 0);
			}
		}
		
		
		horizontalCanvas.add(rightCanvas);
		
		this.add(horizontalCanvas);
		
		
		//condition annotations (with labels next to it)
		leftCanvas.newRow();
		leftCanvas.newRow();
		List<AnnotationBlock<Condition>> cabList = mod.getAnnotationBlocks(DataType.CONDITIONS);
		List<AnnotationBlock<Condition>> globalCabList = modnet.getGlobalAnnotationBlocks(DataType.CONDITIONS);
		cabList.addAll(globalCabList);
		
		for (AnnotationBlock<Condition> cab : cabList){
			leftCanvas.add(new RelativeSpacer(arrowStack, null));
			ConditionAnnotationMatrix canMatrix = 
				new ConditionAnnotationMatrix(mod.getConditionTree(), mod.getNonTreeConditions(), cab);
			leftCanvas.add(canMatrix);
			leftCanvas.add(new Spacer(new Dimension(20,0)));
			Label condAnnotLabel = new Label(cab.getBlockName());
			condAnnotLabel.setAlignment(Alignment.CENTER_LEFT);
			condAnnotLabel.setAngle(-Math.PI/2);
			leftCanvas.add(condAnnotLabel);
			leftCanvas.newRow();
		}
		
		//condition labels
		leftCanvas.add(new RelativeSpacer(arrowStack, null));
		if (guiModel.isDrawConditionLabels()){			
			ConditionLabels condLabels = new ConditionLabels(mod.getConditionTree(), mod.getNonTreeConditions(),true);
			leftCanvas.add(condLabels);
		}
				
		leftCanvas.newRow();
		Canvas legendCanvas = new Canvas();
		legendCanvas.setMargin(20, 0);
		legendCanvas.setHorizontalSpacing(30);
		for (Integer blockId : modnet.getLegendBlockIds()){
			Legend legend = new Legend(modnet, blockId);
			legendCanvas.add(legend);
		}
		
		double legendMean = 0;
		double legendSigma = 0;
		
		switch(guiModel.getMeanScopeModNet()){
		case MODULE_WIDE:
			switch(guiModel.getMeanScopeGeneReg()){
			case REGS_GENES_JOINED:	
				legendMean = mod.getMeanAll();
				legendSigma = mod.getSigmaAll();
				break;
			case REGS_GENES_SEPARATE:
				legendMean = mod.getGeneTree().getMean();
				legendSigma = mod.getGeneTree().getSigma();
				break;
			}
			break;
		case NETWORK_WIDE:
			legendMean = modnet.getMean();
			legendSigma = modnet.getSigma();
			break;
		}
		
		double minGradValue = legendMean-legendSigma*2;
		double maxGradValue = legendMean+legendSigma*2;
		LegendGradient gradient = new LegendGradient(
				minGradValue, 
				maxGradValue, 
				new EnigmaColorizer(modnet.getSigma(), modnet.getMean()));
		gradient.setMinLabel("<= "+Math.round(minGradValue*10)/10d);
		gradient.setMaxLabel(">= "+Math.round(maxGradValue*10)/10d);
		gradient.setWidth(20);
		gradient.setTitle("Expression ratios");
		gradient.addLabel(modnet.getMean());
		gradient.setAlignment(Alignment.BOTTOM_LEFT);
		legendCanvas.add(gradient);
		
		leftCanvas.add(new RelativeSpacer(arrowStack, null));
		leftCanvas.add(legendCanvas);
	}

	
}
