package be.ugent.psb.moduleviewer.testing;

import be.ugent.psb.moduleviewer.dialogs.LoadingWizard;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

public class WizardTest {

	public static void main(String[] args) {
		LoadingWizard wizard = new LoadingWizard(new GUIModel(new Model()));
		wizard.setVisible(true);
	}

}
