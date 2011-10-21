package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.MeanScopeGeneReg;

/**
 * 
 * @author thpar
 *
 */
public class SetMeanScopeGeneRegAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private MeanScopeGeneReg scope;

	
	public SetMeanScopeGeneRegAction(GUIModel guiModel, MeanScopeGeneReg scope){
		super(scope.toString());
		this.scope = scope;
		this.guiModel = guiModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		guiModel.setMeanScopeGeneReg(scope);
	}
	
	
	
}
