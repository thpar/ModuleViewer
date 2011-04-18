package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.actions.LoadModulesAction.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public abstract class Parser {

	abstract public void parse(Model model, File inputFile) throws IOException;


	protected ProgressListener progressListener;
	public void setProgressListener(ProgressListener progListener) {
		progressListener = progListener;		
	}
	
}
