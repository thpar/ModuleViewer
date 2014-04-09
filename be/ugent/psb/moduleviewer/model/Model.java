package be.ugent.psb.moduleviewer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable{

	
	ModuleNetwork modnet = new ModuleNetwork();

	
	private String dataFileName;
	private String geneFileName;
	private String symbolMappingFileName;
	private String conditionFileName;
	private String regulatorFileName;
	private List<String> annotationFileNames = new ArrayList<String>();
	
	private String version;


	
	
	public ModuleNetwork getModnet() {
		return modnet;
	}

	public void setModnet(ModuleNetwork modnet) {
		this.modnet = modnet;
	}
	
	
	
	/**
	 * Checks if essential data is loaded to draw a minimal figure.
	 * This includes the expression matrix and a list (or tree) of genes for each
	 * module.  
	 * 
	 * @return true if enough data is loaded to generate a figure.
	 */
	public boolean isEssentialsLoaded(){
		return dataFileName != null && geneFileName != null;
	}

	public String getDataFile() {
		return dataFileName;
	}

	public void setDataFile(String dataFile) {
		this.dataFileName = dataFile;
		setChanged();
		notifyObservers();
	}

	public String getGeneFile() {
		return geneFileName;
	}

	public void setGeneFile(String geneFile) {
		this.geneFileName = geneFile;
		setChanged();
		notifyObservers();
	}

	public String getConditionFile() {
		return conditionFileName;
	}

	public void setConditionFile(String conditionFile) {
		this.conditionFileName = conditionFile;
		setChanged();
		notifyObservers();
	}

	public String getRegulatorFile() {
		return regulatorFileName;
	}

	public void setRegulatorFile(String regulatorFile) {
		this.regulatorFileName = regulatorFile;
		setChanged();
		notifyObservers();
	}
	
	public void setSymbolMappingFile(String symbolMappingFile){
		this.symbolMappingFileName = symbolMappingFile;
		setChanged();
		notifyObservers();
	}
	
	public String getSymbolMappingFile(){
		return this.symbolMappingFileName;
	}

	public List<String> getAnnotationFiles() {
		return annotationFileNames;
	}

	public void addAnnotationFile(String annotationFile) {
		this.annotationFileNames.add(annotationFile);
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

	public void resetData() {
		this.modnet = new ModuleNetwork();
		dataFileName = null;
		geneFileName = null;
		conditionFileName = null;
		regulatorFileName = null;
		annotationFileNames = new ArrayList<String>();
		
		setChanged();
		notifyObservers();
	}

	
	
	

}
