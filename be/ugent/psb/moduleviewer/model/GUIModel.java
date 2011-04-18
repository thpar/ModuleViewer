package be.ugent.psb.moduleviewer.model;

import java.awt.Color;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Settings of which data to include in the figure to be drawn.
 * This model can be observed by a Canvas or JPanel to react to 
 * changes (toggling of certain components).
 * 
 * @author thpar
 *
 */
public class GUIModel extends Observable implements PropertyChangeListener{
	private boolean drawModule = true;
	
	private boolean drawGOForTopRegulators = true;
	private boolean drawGOForGenes = true;
	private boolean drawAracyc = false;
	private boolean drawConditionAnnotations = false;
	private boolean drawConditionAnnotationLegend = false;
	private boolean drawFileName = true;
	
	
	private Color[] defaultGeneLinkColors = {Color.GRAY, Color.ORANGE, Color.BLACK, Color.RED};
	private Color[] defaultCheckListColors = {Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.MAGENTA};
	private Map<String, Color> geneLinkColorMap = new HashMap<String, Color>();
	private Map<String, Color> geneCheckListColorMap = new HashMap<String, Color>();
	private boolean drawGeneLinks = true;
	private boolean drawGeneCheckLists = true;
	
	
	private boolean useGlobalMeans = false;
	
	
	private String stateString = "--";

	private int displayedModule = 0;
	

	private Frame window;

	private File currentDir;
	
	private File epsOutputDir;

	private int progressBarProgress;

	private boolean showProgressBar = false;

	private String template = "module_#ID#";


	
	public boolean isDrawModule() {
		return drawModule;
	}
	public void setDrawModule(boolean drawModule) {
		this.drawModule = drawModule;
	}
	public boolean isDrawGOForTopRegulators() {
		return drawGOForTopRegulators;
	}
	public void setDrawGOForTopRegulators(boolean drawGOForTopRegulators) {
		this.drawGOForTopRegulators = drawGOForTopRegulators;
	}
	public boolean isDrawGOForGenes() {
		return drawGOForGenes;
	}
	public void setDrawGOForGenes(boolean drawGOForGenes) {
		this.drawGOForGenes = drawGOForGenes;
	}
	public boolean isDrawConditionAnnotations() {
		return drawConditionAnnotations;
	}
	public void setDrawConditionAnnotations(boolean drawConditionAnnotations) {
		this.drawConditionAnnotations = drawConditionAnnotations;
	}
	public boolean isDrawConditionAnnotationLegend() {
		return drawConditionAnnotationLegend;
	}
	public void setDrawConditionAnnotationLegend(
			boolean drawConditionAnnotationLegend) {
		this.drawConditionAnnotationLegend = drawConditionAnnotationLegend;
	}
	public void setDrawFileName(boolean drawFileName) {
		this.drawFileName = drawFileName;
	}
	public boolean isDrawFileName() {
		return drawFileName;
	}
	public boolean isDrawGeneLinks() {
		return drawGeneLinks;
	}
	public void setDrawGeneLinks(boolean drawGeneLinks) {
		this.drawGeneLinks = drawGeneLinks;
	}
	
	
	public String getStateString() {
		return this.stateString;
	}
	public void setStateString(String stateString) {
		this.stateString = stateString;
		setChanged();
		notifyObservers();
	}
	/**
	 * Adds the value of step to the currently displayed module number.
	 * @param step
	 */
	public void navDisplayedModule(int step) {
		this.setDisplayedModule(this.getDisplayedModule()+step);
	}
	public int getDisplayedModule() {
		return displayedModule;
	}
	public void setDisplayedModule(int displayedModule) {
		this.displayedModule = displayedModule;
		setChanged();
		notifyObservers();
	}
	public void setTopContainer(Frame window) {
		this.window = window;
	}
	public Frame getTopContainer() {
		return this.window;
	}
	public void refresh() {
		setChanged();
		notifyObservers();		
	}
	public File getCurrentDir() {
		return currentDir;
	}
	public void setCurrentDir(File currentDir) {
		this.currentDir = currentDir;
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            setProgressBarProgress(progress);
//            taskOutput.append(String.format(
//                    "Completed %d%% of task.\n", task.getProgress()));
        } 

		
	}
	public void setProgressBarProgress(int progress) {
		this.progressBarProgress = progress;
		setChanged();
		notifyObservers();
	}
	public int getProgressBarProgress() {
		return this.progressBarProgress;
	}
	public void showProgressBar(boolean b) {
		showProgressBar = b;
		setChanged();
		notifyObservers();
	}
	public boolean isShowProgressBar(){
		return showProgressBar;
	}
	
	public void addGeneLinkSet(String id){
		this.geneLinkColorMap.put(id, defaultGeneLinkColors[this.geneLinkColorMap.size()%defaultGeneLinkColors.length]);
	}
	public void addGeneCheckList(String id){
		this.geneCheckListColorMap.put(id, defaultCheckListColors[this.geneCheckListColorMap.size()%defaultCheckListColors.length]);
	}
	
	
	public Map<String, Color> getGeneLinkColorMap(){
		return geneLinkColorMap;
	}
	public void setDrawGeneCheckLists(boolean drawGeneCheckLists) {
		this.drawGeneCheckLists = drawGeneCheckLists;
	}
	public boolean isDrawGeneCheckLists() {
		return drawGeneCheckLists;
	}
	public Map<String, Color> getGeneCheckListColorMap() {
		return geneCheckListColorMap;
	}
	
	
	/**
	 * 
	 * @param modId Module ID
	 * @return a filename without extension, based on a template.
	 */
	public String getFileNameTemplate(int modId) {
		String subst = template.replaceFirst("#ID#", String.valueOf(modId));
		return subst;
	}
	public void setEpsOutputDir(File epsOutputDir) {
		this.epsOutputDir = epsOutputDir;
	}
	public File getEpsOutputDir() {
		return epsOutputDir;
	}
	public void setDrawAracyc(boolean drawAracyc) {
		this.drawAracyc = drawAracyc;
	}
	public boolean isDrawAracyc() {
		return drawAracyc;
	}
	public void setUseGlobalMeans(boolean useGlobalMeans) {
		this.useGlobalMeans = useGlobalMeans;
	}
	public boolean isUseGlobalMeans() {
		return useGlobalMeans;
	}
}
