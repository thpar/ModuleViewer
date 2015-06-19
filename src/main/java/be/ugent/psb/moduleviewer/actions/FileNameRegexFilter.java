package be.ugent.psb.moduleviewer.actions;

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

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.filechooser.FileFilter;

public class FileNameRegexFilter extends FileFilter {

	private String descr;
	private Pattern pattern;

	public FileNameRegexFilter(String descr, String pattern){
		this.descr = descr;
		this.pattern = Pattern.compile(pattern);
	}
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		Matcher m = pattern.matcher(f.getName());
		return m.matches();
	}

	@Override
	public String getDescription() {
		return descr;
	}

}
