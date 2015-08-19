package be.ugent.psb.moduleviewer.model;

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

import java.awt.Color;
import java.awt.Frame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import be.ugent.psb.modulegraphics.display.CanvasFigure.OutputFormat;
import be.ugent.psb.modulegraphics.elements.Element;


/**
 * Settings of which data to include in the figure to be drawn.
 * This model can be observed by a Canvas or JPanel to react to 
 * changes (toggling of certain components).
 * 
 * @author Thomas Van Parys
 *
 */
public class GUIModel extends Observable implements PropertyChangeListener{
	
	private boolean drawModule = true;
	
	private boolean drawGOForTopRegulators = true;
	private boolean drawGOForGenes = true;
	private boolean drawAracyc = false;
	private boolean drawConditionAnnotations = false;
	private boolean drawConditionAnnotationLegend = false;
	
	private boolean drawConditionLabels = false;
	
	private boolean drawFileName = true;
	
	
	private Color[] defaultGeneLinkColors = {Color.GRAY, Color.ORANGE, Color.BLACK, Color.RED};
	private Color[] defaultCheckListColors = {Color.GREEN, Color.BLUE, Color.ORANGE, Color.CYAN, Color.MAGENTA};
	private Map<String, Color> geneLinkColorMap = new HashMap<String, Color>();
	private Map<String, Color> geneCheckListColorMap = new HashMap<String, Color>();
	private boolean drawGeneLinks = true;
	private boolean drawGeneCheckLists = true;
	
	private boolean drawTreeStructure = true;
	
	private double zoomLevel = 1;
	
	private boolean debugMode = false; 
	
	private boolean useGlobalMeans = false;
	
	private Model model;
	
	private String stateString;

	private String displayedModule = "";

	private Frame window;

	private File currentDir;
	
	private File outputDir;
	
	private List<File> recentSessions = new ArrayList<File>();

	private int progressBarProgress;

	private boolean showProgressBar = false;

	private String template = "module_#ID#";

	
	private OutputFormat outputFormat = OutputFormat.PDF;
	
	private static String preferenceFileString = ".moduleviewer"; 
	private File prefFile;
	
	/**
	 * With wideMode true, the cells are 16px wide, instead of the default 10px.
	 */
	private boolean wideMode = false;
	
	/**
	 * Scope how to calculate data statistics (mean and sigma)
	 * @author Thomas Van Parys
	 *
	 */
	public enum MeanScopeModNet{
		MODULE_WIDE, 
		NETWORK_WIDE;

		@Override
		public String toString() {
			switch(this){
			case MODULE_WIDE: return "By Module";
			case NETWORK_WIDE:return "Network wide";
			default: return new String();
			}
		}
		
		
	}

	public enum MeanScopeGeneReg{
		REGS_GENES_JOINED,
		REGS_GENES_SEPARATE;
		
		@Override
		public String toString() {
			switch(this){
			case REGS_GENES_JOINED: return "Genes and regulators together";
			case REGS_GENES_SEPARATE:return "Genes and regulators separate";
			default: return new String();
			}
		}
	}

	
	private MeanScopeModNet meanScopeModNet = MeanScopeModNet.NETWORK_WIDE;
	private MeanScopeGeneReg meanScopeGeneReg = MeanScopeGeneReg.REGS_GENES_JOINED;
	
	public enum PointMode{
		POINT, PAN;
	}
	
	private PointMode pointMode = PointMode.PAN;


	
	
	public GUIModel(Model model){
		this.model = model;
		try {
			loadPreferences();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void loadPreferences() throws IOException{
		String homeDir = System.getProperty("user.home");
		this.prefFile = new File(homeDir+System.getProperty("file.separator")+preferenceFileString);
		if (prefFile.exists()){
			Properties props = new Properties();
			props.load(new BufferedReader(new FileReader(prefFile)));
			
			String recentString = props.getProperty("recent", "");
			String[] recents = recentString.split(":");
			for (String rec : recents){
				File recFile = new File(rec);
				if (recFile.exists()){
					this.addRecentSession(recFile);					
				}
			}
			
			String currentDir = props.getProperty("currentDir",homeDir);
			this.setCurrentDir(new File(currentDir));
			
		} else {
			this.setCurrentDir(new File(homeDir));
		}
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
		if (this.stateString !=null){
			return this.stateString;			
		} else {
			return "--";
		}
	}
	
	/**
	 * Set message in status bar
	 * 
	 * @param stateString message to appear in the status bar
	 */
	public void setStateString(String stateString) {
		this.stateString = stateString;
		setChanged();
		notifyObservers();
	}
	
	public void clearStateString(){
		this.stateString = null;
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
	
	/**
	 * 
	 * @return the currently displayed module id. The first one if no display module was set yet. Null if no modules are loaded yet.
	 */
	public String getDisplayedModule() {
		if (!this.displayedModule.isEmpty()){
			return displayedModule;			
		} else if (!model.getModnet().getModules().isEmpty()){
			this.displayedModule = model.modnet.getFirstModuleId(); 
			return displayedModule; 
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * Set the currently displayed module id.
	 */
	public void setDisplayedModule(String displayedModule) {
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


	public MeanScopeModNet getMeanScopeModNet() {
		return meanScopeModNet;
	}


	public void setMeanScopeModNet(MeanScopeModNet meanScopeModNet) {
		if (this.meanScopeModNet != meanScopeModNet){
			this.setChanged();
		}
		this.meanScopeModNet = meanScopeModNet;
		
		//network wide, we just take all data anyway, genes and regulators together
		if (meanScopeModNet == MeanScopeModNet.NETWORK_WIDE){
			this.setMeanScopeGeneReg(MeanScopeGeneReg.REGS_GENES_JOINED);
		}
		this.notifyObservers();
	}


	public MeanScopeGeneReg getMeanScopeGeneReg() {
		return meanScopeGeneReg;
	}


	public void setMeanScopeGeneReg(MeanScopeGeneReg meanScopeGeneReg) {
		if (this.meanScopeGeneReg != meanScopeGeneReg){
			this.setChanged();
		}
		this.meanScopeGeneReg = meanScopeGeneReg;
		this.notifyObservers();
	}

	/**
	 * Keep track of five last saved or opened sessions. Move session to the top 
	 * if it already was in the list.
	 * 
	 * @param session
	 */
	public void addRecentSession(File session){
		if (recentSessions.contains(session)){
			int index = recentSessions.indexOf(session);
			recentSessions.remove(index);
		}
		
		this.recentSessions.add(0, session);
		
		if (recentSessions.size()>5){
			recentSessions.remove(5);
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<File> getRecentSessions(){
		return recentSessions;
	}


	public void saveState() throws IOException {
		Properties props = new Properties();
		
		String recentString = new String();
		for (int i=recentSessions.size()-1; i>=0; i--){
			recentString+=recentSessions.get(i);
			if (i!=0){
				recentString+=":";
			}
		}
		props.setProperty("recent", recentString);
		
		String currentDir = this.getCurrentDir().getAbsolutePath();
		props.setProperty("currentDir", currentDir);
		
		props.store(new BufferedWriter(new FileWriter(prefFile)), "ModuleViewer settings");
	}


	public boolean isDrawConditionLabels() {
		return drawConditionLabels;
	}

	/**
	 * Toggle the condition labels at the bottom of the figure.
	 * @param drawConditionLabels
	 */
	public void setDrawConditionsLabels(boolean drawConditionLabels) {
		if (drawConditionLabels != this.drawConditionLabels){
			this.drawConditionLabels = drawConditionLabels;
			this.setChanged();
			this.notifyObservers();
		}		
	}


	/**
	 * 
	 * @return the current zoom level. 1 is normal scale.
	 */
	public double getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * Set the zoom level. 
	 * 
	 * @param zoomLevel scaling to be applied. 1 is not scaled.
	 */
	public void setZoomLevel(double zoomLevel) {
		if (this.zoomLevel != zoomLevel){
			this.zoomLevel = zoomLevel;
			setChanged();
			notifyObservers();
		}
	}


	public void decZoomLevel(double d) {
		this.setZoomLevel(Math.max(zoomLevel-d, 0));
	}


	public void incZoomLevel(double d) {
		this.setZoomLevel(Math.min(zoomLevel+d, 3));
	}

	/**
	 * With wideMode true, the cells are 16px wide, instead of the default 10px.
	 */
	public boolean isWideMode() {
		return this.wideMode;
	}
	
	/**
	 * With wideMode true, the cells are 16px wide, instead of the default 10px.
	 */
	public void setWideMode(boolean wideMode) {
		if (this.wideMode != wideMode){
			this.wideMode = wideMode;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	

	
	
	
}
