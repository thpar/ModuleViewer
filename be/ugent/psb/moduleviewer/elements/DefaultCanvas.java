package be.ugent.psb.moduleviewer.elements;

import java.awt.Dimension;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.modulegraphics.elements.ElementStack;
import be.ugent.psb.modulegraphics.elements.Label;
import be.ugent.psb.modulegraphics.elements.RelativeSpacer;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.modulegraphics.elements.TreeStructure;
import be.ugent.psb.modulegraphics.elements.UnitSpacer;
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
 * @author thpar
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
		 * coreCanvas groups regulator matrix, expression matrix and gene names
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
		regGeneArrowCanvas.add(regGeneArrowPlaceHolder);
		arrowStack.add(regGeneArrowCanvas);
		
		Canvas arrowCanvas = new Canvas();
		arrowCanvas.setVerticalSpacing(5);
		arrowStack.add(arrowCanvas);
		if (drawnTree){
			arrowCanvas.add(new RelativeSpacer(null, tree));
			arrowCanvas.newRow();
		}
		ElementStack regArrowPlaceHolder = new ElementStack();
		if (mod.getRegulatorTrees().size() > 0){
			regArrowPlaceHolder.add(new UnitSpacer(0, mod.getRegulatorTrees().get(0).getGenes().size()));			
		}
		arrowCanvas.add(regArrowPlaceHolder);
		arrowCanvas.newRow();
		arrowCanvas.add(new Spacer(new Dimension(0,10)));
		arrowCanvas.newRow();
		ElementStack geneArrowPlaceHolder = new ElementStack();
		arrowCanvas.add(geneArrowPlaceHolder);
				
		
		//regulator genes
		GeneNames regNames = null;
		double regSigma = 0;
		double regMean = 0;
		if (model.getRegulatorFile()!=null && mod.getRegulatorTrees().size()>0){
			for (GeneNode regTree : mod.getRegulatorTrees()){
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
		
		Canvas coreAndArrowCanvas = new Canvas();
		coreAndArrowCanvas.setHorizontalSpacing(5);
		coreAndArrowCanvas.setVerticalSpacing(5);
		coreAndArrowCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		coreAndArrowCanvas.add(arrowStack);
		coreAndArrowCanvas.add(coreCanvas);
		
		//horizontal canvas contains two canvas: arrow/core and annotations
		Canvas horizontalCanvas = new Canvas();
		horizontalCanvas.setHorizontalSpacing(25);
		horizontalCanvas.setVerticalSpacing(5);
		horizontalCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		
		
		horizontalCanvas.add(coreAndArrowCanvas);
		
		
		
		Canvas annotationCanvas = new Canvas();
		annotationCanvas.setHorizontalSpacing(5);
		annotationCanvas.setVerticalSpacing(5);
		annotationCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		
		
		//TODO: where to put the regulator annotations?
		
		//only use for exactly one regulator tree
		int regregBowCount = 2;
		int reggeneBowCount = 2;
		int genegeneBowCount = 2;
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
					regArrowPlaceHolder.add(regArrows);
					break;
				case regulatorgeneinteraction:				
					GeneCrossLinks regGeneArrows = new GeneCrossLinks();
					regGeneArrows.setGenes(mod.getGeneTree());
					regGeneArrows.setRegGenes(mod.getRegulatorTrees().get(0));
					regGeneArrows.setBowWidth(reggeneBowCount+=2);
					regGeneArrows.setAnnotationBlock(rab);
					regGeneArrowPlaceHolder.add(regGeneArrows);
					break;
				case unknown:
				default:
					//add default annotation blocks next to the regulators
					System.out.println("Default regulator annotation");
					GeneAnnotationMatrix ganMatrix = new GeneAnnotationMatrix(mod.getRegulatorTrees().get(0), rab);
					annotationCanvas.add(ganMatrix);
					annotationCanvas.getLastAddedElement().setAlignment(Alignment.BOTTOM_CENTER);
				}
			}
		}
		
		
		
		//extra data (bingo, ...) from MVF files
		//this is the only place where the type of the block is considered.
		//this information can be used to decide to display a block different from 
		//a standard annotation block.
		List<AnnotationBlock<Gene>> gabList = mod.getAnnotationBlocks(DataType.GENES);
		List<AnnotationBlock<Gene>> globalGabList = modnet.getGlobalAnnotationBlocks(DataType.GENES);
		gabList.addAll(globalGabList);
		
		//annotation block **LABELS**
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
				annotationCanvas.add(new Label(gab.getBlockName()));
				annotationCanvas.getLastAddedElement().setAlignment(Alignment.BOTTOM_CENTER);
			}
		}
		annotationCanvas.newRow();
		
		//annotation **BLOCKS**
		regregBowCount=2;
		for (AnnotationBlock<Gene> gab : gabList){
			BlockType blockType = gab.getBlockType();
			System.out.println(blockType);
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
				geneArrowPlaceHolder.add(geneArrows);
				break;
			case regulatorgeneinteraction:				
				//same processing as in REGULATOR blocks... depends on how the user defined this block
				GeneCrossLinks regGeneArrows = new GeneCrossLinks();
				regGeneArrows.setGenes(mod.getGeneTree());
				regGeneArrows.setRegGenes(mod.getRegulatorTrees().get(0));
				regGeneArrows.setBowWidth(reggeneBowCount+=2);
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
				annotationCanvas.add(ganMatrix);
				annotationCanvas.getLastAddedElement().setAlignment(Alignment.BOTTOM_CENTER);				
				
			}
			
		}
		horizontalCanvas.add(annotationCanvas);
		
		this.add(horizontalCanvas);
		
		this.newRow();
		
		Canvas condAnnotationCanvas = new Canvas();
		condAnnotationCanvas.setHorizontalSpacing(5);
		condAnnotationCanvas.setVerticalSpacing(5);
		condAnnotationCanvas.setAlignment(Alignment.BOTTOM_LEFT);
		
		//condition annotations (with labels next to it)
		List<AnnotationBlock<Condition>> cabList = mod.getAnnotationBlocks(DataType.CONDITIONS);
		List<AnnotationBlock<Condition>> globalCabList = modnet.getGlobalAnnotationBlocks(DataType.CONDITIONS);
		cabList.addAll(globalCabList);
		
		for (AnnotationBlock<Condition> cab : cabList){
			condAnnotationCanvas.add(new RelativeSpacer(arrowStack, null));
			ConditionAnnotationMatrix canMatrix = 
				new ConditionAnnotationMatrix(mod.getConditionTree(), mod.getNonTreeConditions(), cab);
			condAnnotationCanvas.add(canMatrix);
			condAnnotationCanvas.add(new Spacer(new Dimension(20,0)));
			Label condAnnotLabel = new Label(cab.getBlockName());
			condAnnotLabel.setAlignment(Alignment.CENTER_LEFT);
			condAnnotLabel.setAngle(-Math.PI/2);
			condAnnotationCanvas.add(condAnnotLabel);
			condAnnotationCanvas.newRow();
		}
		
		this.newRow();
		//condition labels
		condAnnotationCanvas.add(new RelativeSpacer(arrowStack, null));
		if (guiModel.isDrawConditionLabels()){			
			ConditionLabels condLabels = new ConditionLabels(mod.getConditionTree(), mod.getNonTreeConditions(),true);
			condAnnotationCanvas.add(condLabels);
		}
		
		this.add(condAnnotationCanvas);
		this.newRow();
		
		Canvas legendCanvas = new Canvas();
		for (Integer blockId : modnet.getLegendBlockIds()){
			Legend legend = new Legend(modnet, blockId);
			legendCanvas.add(legend);
		}
		this.add(legendCanvas);
	}

	
}
