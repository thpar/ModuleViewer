package be.ugent.psb.moduleviewer.model;

import java.awt.Color;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import be.ugent.psb.modulegraphics.display.CanvasFigure.OutputFormat;
import be.ugent.psb.modulegraphics.elements.Element;


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
	
	private boolean drawTreeStructure = true;
	
	private boolean debugMode = false; 
	
	private boolean useGlobalMeans = false;
	
	private Model model;
	
	private String stateString = "--";

	private int displayedModule = 0;
	

	private Frame window;

	private File currentDir;
	
	private File outputDir;

	private int progressBarProgress;

	private boolean showProgressBar = false;

	private String template = "module_#ID#";

	
	private OutputFormat outputFormat = OutputFormat.PDF;
	
	
	public enum PointMode{
		POINT, PAN;
	}
	
	private PointMode pointMode = PointMode.PAN;
	
	
	public GUIModel(Model model){
		this.model = model;
	}
	
	
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
		if (step>0){
			displayNextModule();
		} else {
			displayPrevModule();
		}
	}
	public int getDisplayedModule() {
		return displayedModule;
	}
	public void setDisplayedModule(int displayedModule) {
		this.displayedModule = displayedModule;
		setChanged();
		notifyObservers();
	}
	public void displayNextModule(){
		setDisplayedModule(model.getModnet().getNextModuleId(displayedModule));
	}
	public void displayPrevModule(){
		setDisplayedModule(model.getModnet().getPrevModuleId(displayedModule));
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
	
	
	public boolean isDrawTreeStructure() {
		return drawTreeStructure;
	}


	public void setDrawTreeStructure(boolean drawTreeStructure) {
		if (drawTreeStructure != this.drawTreeStructure){
			this.drawTreeStructure = drawTreeStructure;
			this.setChanged();
			this.notifyObservers();
		}		
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
	public String getFileNameTemplate(String modId) {
		String subst = template.replaceFirst("#ID#", modId);
		return subst;
	}
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	public File getOutputDir() {
		return outputDir;
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
	public OutputFormat getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(OutputFormat outputFormat) {
		this.outputFormat = outputFormat;
		this.setChanged();
		this.notifyObservers();
	}


	public boolean isDebugMode() {
		return debugMode;
	}


	public void setDebugMode(boolean debugMode) {
		if (debugMode != this.debugMode){
			this.debugMode = debugMode;
			Element.setDebugMode(debugMode);
			this.setChanged();
			this.notifyObservers();
		}		
	}
	
	/**
	 * 
	 * @return the use mode of the cursor (pointing, dragging, ...)
	 */
	public PointMode getPointMode(){
		return pointMode;
	}
	
	public void setPointMode(PointMode pm){
		this.pointMode = pm;
		this.setChanged();
		this.notifyObservers(new String("nonredraw"));
	}
	
}
