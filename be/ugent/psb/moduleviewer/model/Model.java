package be.ugent.psb.moduleviewer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{

	
	ModuleNetwork modnet = new ModuleNetwork();

	
	private File dataFile;
	private File geneFile;
	private File conditionFile;
	private File regulatorFile;
	private List<File> annotationFiles = new ArrayList<File>();
	
	private String version;
	
	
	public ModuleNetwork getModnet() {
		return modnet;
	}

	public void setModnet(ModuleNetwork modnet) {
		this.modnet = modnet;
	}
	
	
	
	
	public boolean isEssentialsLoaded(){
		return dataFile != null && geneFile != null && conditionFile!= null;
	}

	public File getDataFile() {
		return dataFile;
	}

	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
		setChanged();
		notifyObservers();
	}

	public File getGeneFile() {
		return geneFile;
	}

	public void setGeneFile(File geneFile) {
		this.geneFile = geneFile;
		setChanged();
		notifyObservers();
	}

	public File getConditionFile() {
		return conditionFile;
	}

	public void setConditionFile(File conditionFile) {
		this.conditionFile = conditionFile;
		setChanged();
		notifyObservers();
	}

	public File getRegulatorFile() {
		return regulatorFile;
	}

	public void setRegulatorFile(File regulatorFile) {
		this.regulatorFile = regulatorFile;
		setChanged();
		notifyObservers();
	}

	public List<File> getAnnotationFiles() {
		return annotationFiles;
	}

	public void addAnnotationFile(File annotationFile) {
		this.annotationFiles.add(annotationFile);
		setChanged();
		notifyObservers();
	}

	public String getVersion() {
		if (version!=null) return version;
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					this.getClass().getResourceAsStream("/VERSION.TXT")));
			version = br.readLine();
			br.close();
		} catch (IOException e1) {
			System.err.println(e1.getMessage());
			version = "??";
		}
		return version;
	}

	
	
	

}
