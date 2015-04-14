package be.ugent.psb.moduleviewer.parsers;

import java.io.IOException;
import java.io.InputStream;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public class RegulatorTreeParser extends TreeParser{

	public RegulatorTreeParser() {
		super();
	}

	public RegulatorTreeParser(ProgressListener progListener) {
		super(progListener);
	}

	@Override
	public void parse(Model model, InputStream input) throws IOException, ParseException {
		parse(input, new GeneXMLHandler(model.getModnet(), progressListener, GeneXMLHandler.GeneType.REGULATORS, model.getLogger()));
	}
	
	
}
