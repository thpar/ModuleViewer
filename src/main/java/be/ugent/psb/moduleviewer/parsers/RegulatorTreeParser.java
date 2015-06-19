package be.ugent.psb.moduleviewer.parsers;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
