package be.ugent.psb.graphicalmodule.actions;

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
