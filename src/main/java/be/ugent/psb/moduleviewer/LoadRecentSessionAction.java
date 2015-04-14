package be.ugent.psb.moduleviewer;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import be.ugent.psb.moduleviewer.actions.LoadSessionAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ParseException;

public class LoadRecentSessionAction extends LoadSessionAction {

	private static final long serialVersionUID = 1L;
	
	private File session;
	
	
	public LoadRecentSessionAction(Model model, GUIModel guiModel, File session) {
		super(session.toString(), model, guiModel);
		this.session = session;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.loadFromFile(this.session);
			guiModel.addRecentSession(session);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1){
			JOptionPane.showMessageDialog(guiModel.getTopContainer(), 
					"Could not load session file: "+session,
					"Parse error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
