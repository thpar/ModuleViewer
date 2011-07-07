package be.ugent.psb.moduleviewer.elements;

import java.awt.Dimension;
import java.util.List;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.AnnotationBlock.DataType;
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
		
		Canvas coreCanvas = new Canvas();
		coreCanvas.setHorizontalSpacing(5);
		coreCanvas.setVerticalSpacing(5);
		coreCanvas.setAlignment(Alignment.BOTTOM_CENTER);
		
		//regulator genes
		if (model.getRegulatorFile()!=null && mod.getRegulatorTree()!=null){
			ExpressionMatrix regulatorMatrix = new ExpressionMatrix(mod.getRegulatorTree(),
					mod.getConditionTree(),
					mod.getNonTreeConditions(),
					new EnigmaColorizer(modnet.getSigma(),modnet.getMean()),
					true, false);
			coreCanvas.add(regulatorMatrix);

			GeneNames regNames = new GeneNames(mod.getRegulatorTree());
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
		
		this.add(coreCanvas);
		
		//extra data (bingo, ...)
		List<AnnotationBlock<Gene>> gabList = mod.getAnnotationBlocks(DataType.GENES);
		for (AnnotationBlock<Gene> gab : gabList){
			GeneAnnotationMatrix ganMatrix = new GeneAnnotationMatrix(mod.getGeneTree(), gab);
			this.add(ganMatrix);
			this.getLastAddedElement().setAlignment(Alignment.BOTTOM_LEFT);
		}
		this.newRow();
//		this.newRow();
		
//		//condition annotations (with labels next to it)
//		List<AnnotationBlock<Condition>> cabList = mod.getAnnotationBlocks(DataType.CONDITIONS);
//		for (AnnotationBlock<Condition> cab : cabList){
//			ConditionAnnotationMatrix canMatrix = new ConditionAnnotationMatrix(mod.getConditionTree(), cab);
//			this.addExplode(canMatrix);
//		}
//		
//		this.newRow();
		//condition labels 
		ConditionLabels condLabels = new ConditionLabels(mod.getConditionTree(), mod.getNonTreeConditions(),true);
		this.add(condLabels);
		
		
	}

	
}
