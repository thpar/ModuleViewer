package be.ugent.psb.moduleviewer.model;

import java.io.File;
import java.util.Observable;

public class Model extends Observable{

	
	ModuleNetwork modnet = new ModuleNetwork();

	
	private File dataFile;
	private File geneFile;
	private File conditionFile;
	private File regulatorFile;
	
	
	
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

	
	

}
