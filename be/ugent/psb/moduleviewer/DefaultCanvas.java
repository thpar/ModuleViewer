package be.ugent.psb.moduleviewer;

import java.awt.Dimension;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Spacer;
import be.ugent.psb.moduleviewer.elements.GeneAnnotationMatrix;
import be.ugent.psb.moduleviewer.elements.ConditionLabels;
import be.ugent.psb.moduleviewer.elements.EnigmaColorizer;
import be.ugent.psb.moduleviewer.elements.ExpressionMatrix;
import be.ugent.psb.moduleviewer.elements.GeneNames;
import be.ugent.psb.moduleviewer.elements.Title;
import be.ugent.psb.moduleviewer.model.AnnotationBlock;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * A Default layout for a Canvas that looks up which elements to 
 * draw in the graphical model of the module.
 * 
 * The Module itself is drawn anyway. If topregulators are given, they are drawn as well, together
 * with the tree structure.
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
		
		//regulator genes
		if (model.getRegulatorFile()!=null && mod.getRegulatorTree()!=null){
			ExpressionMatrix regulatorMatrix = new ExpressionMatrix(mod.getRegulatorTree(),
					mod.getConditionTree(),
					mod.getNonTreeConditions(),
					new EnigmaColorizer(modnet.getSigma(),modnet.getMean()),
					true, false);
			this.add(regulatorMatrix);

			GeneNames regNames = new GeneNames(mod.getRegulatorTree());
			this.add(regNames);
			
			this.newRow();
			this.add(new Spacer(new Dimension(0,10)));
			this.newRow();
		}
		
		//expression matrix
		ExpressionMatrix expressionMatrix = new ExpressionMatrix(mod.getGeneTree(),
				mod.getConditionTree(),
				mod.getNonTreeConditions(),
				new EnigmaColorizer(modnet.getSigma(),modnet.getMean()),
				true, true);
		this.add(expressionMatrix);
		
		GeneNames geneNames = new GeneNames(mod.getGeneTree());
		this.add(geneNames);
		
		//extra data (bingo, ...)
		for (AnnotationBlock ab : mod.getAnnotationBlocks()){
			if (ab.getType()==AnnotationBlock.DataType.GENES){
				GeneAnnotationMatrix anMatrix = new GeneAnnotationMatrix(mod.getGeneTree(), ab);
				this.addExplode(anMatrix);
			}
		}
		this.newRow();
		
		//condition annotations (with labels next to it)
		
		
		
		//condition labels 
		ConditionLabels condLabels = new ConditionLabels(mod.getConditionTree(), mod.getNonTreeConditions(),true);
		this.add(condLabels);
		
		
		
		
	}

	
}
