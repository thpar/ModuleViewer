package be.ugent.psb.moduleviewer;

import be.ugent.psb.modulegraphics.elements.Canvas;
import be.ugent.psb.modulegraphics.elements.Element;
import be.ugent.psb.moduleviewer.elements.Title;
import be.ugent.psb.moduleviewer.model.GUIModel;
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
	private GUIModel guiModel;
	private String title;

	public DefaultCanvas(Module mod, GUIModel guiModel, String title){
		this.modnet = mod.getModuleNetwork();
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
		
		
		
	}

	
}
