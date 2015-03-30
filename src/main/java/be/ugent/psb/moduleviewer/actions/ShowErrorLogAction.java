package be.ugent.psb.moduleviewer.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import be.ugent.psb.moduleviewer.dialogs.LogDialog;
import be.ugent.psb.moduleviewer.model.Model;

public class ShowErrorLogAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private Model model;


	public ShowErrorLogAction(Model model) {
		super("Error log...");
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LogDialog dialog = new LogDialog(model.getLogger());
		dialog.setVisible(true);
	}



}
