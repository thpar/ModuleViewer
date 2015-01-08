package be.ugent.psb.moduleviewer.parsers;

import java.io.IOException;
import java.io.InputStream;

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
	public void parse(Model model, InputStream input) throws IOException {
		parse(input, new ConditionXMLHandler(model.getModnet(), progressListener));
	}
	
	
	
}
