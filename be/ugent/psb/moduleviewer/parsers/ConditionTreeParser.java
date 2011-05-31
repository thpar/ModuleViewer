package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public class ConditionTreeParser extends TreeParser{

	public ConditionTreeParser() {
		super();
	}

	public ConditionTreeParser(ProgressListener progListener) {
		super(progListener);
	}

	@Override
	public void parse(Model model, File inputFile) throws IOException {
		parse(inputFile, new ConditionXMLHandler(model.getModnet(), progressListener));
		model.setConditionFile(inputFile);
	}
	
	
}
