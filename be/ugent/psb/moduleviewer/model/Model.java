package be.ugent.psb.moduleviewer.model;

public class Model {

	
	ModuleNetwork modnet = new ModuleNetwork();

	private boolean dataMatrixLoaded = false;
	private boolean geneTreeLoaded = false;
	private boolean conditionTreeLoaded = false;
	
	private boolean regulatorTreeLoaded = false;
	
	
	public ModuleNetwork getModnet() {
		return modnet;
	}

	public void setModnet(ModuleNetwork modnet) {
		this.modnet = modnet;
	}
	
	
	public boolean isEssentialsLoaded(){
		return dataMatrixLoaded && geneTreeLoaded && conditionTreeLoaded;
	}

	public boolean isDataMatrixLoaded() {
		return dataMatrixLoaded;
	}

	public void setDataMatrixLoaded(boolean dataMatrixLoaded) {
		this.dataMatrixLoaded = dataMatrixLoaded;
	}

	public boolean isGeneTreeLoaded() {
		return geneTreeLoaded;
	}

	public void setGeneTreeLoaded(boolean geneTreeLoaded) {
		this.geneTreeLoaded = geneTreeLoaded;
	}

	public boolean isConditionTreeLoaded() {
		return conditionTreeLoaded;
	}

	public void setConditionTreeLoaded(boolean conditionTreeLoaded) {
		this.conditionTreeLoaded = conditionTreeLoaded;
	}

	public boolean isRegulatorTreeLoaded() {
		return regulatorTreeLoaded;
	}

	public void setRegulatorTreeLoaded(boolean regulatorTreeLoaded) {
		this.regulatorTreeLoaded = regulatorTreeLoaded;
	}
	
	

}
