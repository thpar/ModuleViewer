package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.MeanScopeModNet;

/**
 * 
 * @author thpar
 *
 */
public class SetMeanScopeModNetAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private MeanScopeModNet scope;

	
	public SetMeanScopeModNetAction(GUIModel guiModel, MeanScopeModNet scope){
		super(scope.toString());
		this.scope = scope;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setMeanScopeModNet(scope);
	}
	
	
	
}
