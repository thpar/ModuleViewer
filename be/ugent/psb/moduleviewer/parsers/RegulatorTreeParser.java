package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.IOException;

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
	public void parse(Model model, File inputFile) throws IOException {
		parse(inputFile, new GeneXMLHandler(model.getModnet(), progressListener, GeneXMLHandler.GeneType.REGULATORS));
	}
	
	
}
