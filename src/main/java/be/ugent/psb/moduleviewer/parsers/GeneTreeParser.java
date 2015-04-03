package be.ugent.psb.moduleviewer.parsers;

import java.io.IOException;
import java.io.InputStream;

import be.ugent.psb.moduleviewer.Logger;
import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public class GeneTreeParser extends TreeParser{

	public GeneTreeParser() {
		super();
	}

	public GeneTreeParser(ProgressListener progListener) {
		super(progListener);
	}

	@Override
	public void parse(Model model, InputStream input) throws IOException {
		parse(input, new GeneXMLHandler(model.getModnet(), progressListener, GeneXMLHandler.GeneType.GENES, model.getLogger()));
	}
	
	
}
