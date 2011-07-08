package be.ugent.psb.moduleviewer.elements;

import java.awt.Dimension;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.model.Annotation;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.BlockType;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
import be.ugent.psb.moduleviewer.model.Condition;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Gene;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * A Default layout for a Canvas that looks up which elements to 
 * draw in the graphical model of the module.
 * 
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

	private void composeCanvas() {
		//general settings
		this.setHorizontalSpacing(5);
		this.setVerticalSpacing(5);
		this.setMargin(20);
		
		
		//title
		if (guiModel.isDrawFileName()){
			this.add(new Title(title));
			this.getLastAddedElement().setBottomMargin(10);
			this.newRow();
		}
		
		/**
		 * coreCanvas groups regulator matrix, expression matrix and gene names
		 */
		Canvas coreCanvas = new Canvas();
		coreCanvas.setHorizontalSpacing(5);
		coreCanvas.setVerticalSpacing(5);
		coreCanvas.setAlignment(Alignment.BOTTOM_CENTER);
		
		//regulator genes
		GeneNames regNames = null;
		if (model.getRegulatorFile()!=null && mod.getRegulatorTree()!=null){
			ExpressionMatrix regulatorMatrix = new ExpressionMatrix(mod.getRegulatorTree(),
					mod.getConditionTree(),
					mod.getNonTreeConditions(),
					new EnigmaColorizer(modnet.getSigma(),modnet.getMean()),
					true, false);
			coreCanvas.add(regulatorMatrix);

			regNames = new GeneNames(mod.getRegulatorTree());
			coreCanvas.add(regNames);
			
			coreCanvas.newRow();
			coreCanvas.add(new Spacer(new Dimension(0,10)));
			coreCanvas.newRow();
		}
		
		//expression matrix
		ExpressionMatrix expressionMatrix = new ExpressionMatrix(mod.getGeneTree(),
				mod.getConditionTree(),
				mod.getNonTreeConditions(),
				new EnigmaColorizer(modnet.getSigma(),modnet.getMean()),
				true, true);
		coreCanvas.add(expressionMatrix);
		
		GeneNames geneNames = new GeneNames(mod.getGeneTree());
		coreCanvas.add(geneNames);
		
		
		Canvas horizontalCanvas = new Canvas();
		horizontalCanvas.setHorizontalSpacing(5);
		horizontalCanvas.setVerticalSpacing(5);
		horizontalCanvas.setAlignment(Alignment.BOTTOM_CENTER);
		
		horizontalCanvas.add(coreCanvas);
		
		
		//extra data (bingo, ...)
		List<AnnotationBlock<Gene>> gabList = mod.getAnnotationBlocks(DataType.GENES);
		for (AnnotationBlock<Gene> gab : gabList){
			BlockType blockType = gab.getBlockType();
			switch(blockType){
			case genecolorbox:
				Annotation<Gene> ansGene = gab.getAnnotation();
				geneNames.colorBackgrounds(ansGene.getItems(), gab.getColor());
				break;
			case regulatorcolorbox:
				Annotation<Gene> ansReg = gab.getAnnotation();
				regNames.colorBackgrounds(ansReg.getItems(), gab.getColor());
				break;
			case genegeneinteraction:
			case regulatorgeneinteraction:
			case regulatorregulatorinteraction:
				//TODO take care of interactions
				break;
			case bingo:
			case core:
			case modulelinks:
			case tfenrichment:
			case unknown:
			default:
				GeneAnnotationMatrix ganMatrix = new GeneAnnotationMatrix(mod.getGeneTree(), gab);
				horizontalCanvas.add(ganMatrix);
				horizontalCanvas.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);				
				
			}
			
		}
		
		this.add(horizontalCanvas);
		
		this.newRow();
		
//		//condition annotations (with labels next to it)
		List<AnnotationBlock<Condition>> cabList = mod.getAnnotationBlocks(DataType.CONDITIONS);
		for (AnnotationBlock<Condition> cab : cabList){
			ConditionAnnotationMatrix canMatrix = 
				new ConditionAnnotationMatrix(mod.getConditionTree(), mod.getNonTreeConditions(), cab);
			this.add(canMatrix);
		}
		
		this.newRow();
		//condition labels 
		ConditionLabels condLabels = new ConditionLabels(mod.getConditionTree(), mod.getNonTreeConditions(),true);
		this.add(condLabels);
		
		
	}

	
}
