package be.ugent.psb.moduleviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import be.ugent.psb.modulegraphics.display.CanvasFigure.OutputFormat;
import be.ugent.psb.moduleviewer.actions.ChangeOutputDirAction;
import be.ugent.psb.moduleviewer.actions.ExportAllFiguresAction;
import be.ugent.psb.moduleviewer.actions.ExportToFigureAction;
import be.ugent.psb.moduleviewer.actions.LoadAnnotationAction;
import be.ugent.psb.moduleviewer.actions.LoadConditionTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadDataAction;
import be.ugent.psb.moduleviewer.actions.LoadGeneTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadRegulatorTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadSessionAction;
import be.ugent.psb.moduleviewer.actions.LoadSymbolicNamesAction;
import be.ugent.psb.moduleviewer.actions.NewSessionAction;
import be.ugent.psb.moduleviewer.actions.SaveSessionAsAction;
import be.ugent.psb.moduleviewer.actions.SetMeanScopeGeneRegAction;
import be.ugent.psb.moduleviewer.actions.SetMeanScopeModNetAction;
import be.ugent.psb.moduleviewer.actions.SetOutputFormatAction;
import be.ugent.psb.moduleviewer.actions.SortConditionsAction;
import be.ugent.psb.moduleviewer.actions.SortGenesAction;
import be.ugent.psb.moduleviewer.actions.ToggleDebugModeAction;
import be.ugent.psb.moduleviewer.actions.ToggleShowConditionLabelsAction;
import be.ugent.psb.moduleviewer.actions.ToggleShowTreeAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.MeanScopeGeneReg;
import be.ugent.psb.moduleviewer.model.GUIModel.MeanScopeModNet;
import be.ugent.psb.moduleviewer.model.Model;

/**
 * The classic top screen menu. Observes the model to toggle options when needed.
 * 
 * @author thpar
 *
 */
public class MainMenu extends JMenuBar implements Observer{

	
	private static final long serialVersionUID = 1L;
	private JMenuItem saveSessionAsItem;
	private Model model;
	private GUIModel guiModel;
	private JCheckBoxMenuItem treeStructureBoxItem;
	private JCheckBoxMenuItem debugBoxItem;
	private ButtonGroup regulatorMeanGroup;
	private JRadioButtonMenuItem regSeparateItem;
	private JRadioButtonMenuItem regJoinedItem;
	private JMenu recentSessionMenu;
	private JCheckBoxMenuItem conditionLabelBoxItem;
	
	public MainMenu(Model model, final GUIModel guiModel){

		this.model = model;
		this.guiModel = guiModel;
		model.addObserver(this);
		guiModel.addObserver(this);

		
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadDataItem = new JMenuItem(new LoadDataAction(model, guiModel));
		JMenuItem loadGeneTreeItem = new JMenuItem(new LoadGeneTreeAction(model, guiModel));
		JMenuItem loadSymbolicNameItem = new JMenuItem(new LoadSymbolicNamesAction(model, guiModel));
		JMenuItem loadConditionTreeItem = new JMenuItem(new LoadConditionTreeAction(model, guiModel));
		JMenuItem loadRegTreeItem = new JMenuItem(new LoadRegulatorTreeAction(model, guiModel));
		JMenuItem loadAnnotItem = new JMenuItem(new LoadAnnotationAction(model, guiModel));
		
		saveSessionAsItem = new JMenuItem(new SaveSessionAsAction(model, guiModel));
		saveSessionAsItem.setEnabled(false);
		
		recentSessionMenu = new JMenu("Recent sessions");
		for (File recentSession : guiModel.getRecentSessions()){
			recentSessionMenu.add(new LoadRecentSessionAction(model, guiModel, recentSession));
		}
		if (guiModel.getRecentSessions().size()==0){
			recentSessionMenu.setEnabled(false);
		}
		
		JMenuItem loadSessionItem = new JMenuItem(new LoadSessionAction(model, guiModel));
		JMenuItem newSessionItem = new JMenuItem(new NewSessionAction(model, guiModel));
		


		JMenuItem exportItem = new JMenuItem(new ExportToFigureAction(model, guiModel));
		JMenuItem exportAllItem = new JMenuItem(new ExportAllFiguresAction(model, guiModel));
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(loadDataItem);
		fileMenu.add(loadGeneTreeItem);
		fileMenu.add(loadSymbolicNameItem);
		fileMenu.add(loadConditionTreeItem);
		fileMenu.add(loadRegTreeItem);
		fileMenu.add(loadAnnotItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(newSessionItem);
		fileMenu.add(recentSessionMenu);
		fileMenu.add(saveSessionAsItem);
		fileMenu.add(loadSessionItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(exportItem);
		fileMenu.add(exportAllItem);
		fileMenu.addSeparator();
		
		fileMenu.add(exitItem);
		
		
		JMenu viewMenu = new JMenu("View");
		treeStructureBoxItem = new JCheckBoxMenuItem(new ToggleShowTreeAction(guiModel));
		treeStructureBoxItem.setSelected(guiModel.isDrawTreeStructure());
		viewMenu.add(treeStructureBoxItem);
		
		conditionLabelBoxItem = new JCheckBoxMenuItem(new ToggleShowConditionLabelsAction(guiModel));
		conditionLabelBoxItem.setSelected(guiModel.isDrawConditionLabels());
		viewMenu.add(conditionLabelBoxItem);
		
		viewMenu.addSeparator();
		
		JMenuItem sortGenesItem = new JMenuItem(new SortGenesAction(model, guiModel));
		JMenuItem sortConditionsItem = new JMenuItem(new SortConditionsAction(model, guiModel));
		viewMenu.add(sortGenesItem);
		viewMenu.add(sortConditionsItem);
		
		viewMenu.addSeparator();
		
		JMenu sigmaMeanMenu = new JMenu("Sigma/Mean scope");
		viewMenu.add(sigmaMeanMenu);
		ButtonGroup moduleMeanGroup = new ButtonGroup();
		JRadioButtonMenuItem moduleWideItem = new JRadioButtonMenuItem(new SetMeanScopeModNetAction(guiModel, MeanScopeModNet.MODULE_WIDE));
		JRadioButtonMenuItem networkWideItem = new JRadioButtonMenuItem(new SetMeanScopeModNetAction(guiModel, MeanScopeModNet.NETWORK_WIDE));
		moduleMeanGroup.add(moduleWideItem);
		moduleMeanGroup.add(networkWideItem);
		
		switch(guiModel.getMeanScopeModNet()){
		case MODULE_WIDE:
			moduleMeanGroup.setSelected(moduleWideItem.getModel(), true);
			break;
		case NETWORK_WIDE:
			moduleMeanGroup.setSelected(networkWideItem.getModel(), true);
			break;
		}
		
		regulatorMeanGroup = new ButtonGroup();
		regSeparateItem = new JRadioButtonMenuItem(new SetMeanScopeGeneRegAction(guiModel, MeanScopeGeneReg.REGS_GENES_SEPARATE));
		regJoinedItem = new JRadioButtonMenuItem(new SetMeanScopeGeneRegAction(guiModel, MeanScopeGeneReg.REGS_GENES_JOINED));
		regulatorMeanGroup.add(regSeparateItem);
		regulatorMeanGroup.add(regJoinedItem);
		
		switch(guiModel.getMeanScopeGeneReg()){
		case REGS_GENES_JOINED:
			regulatorMeanGroup.setSelected(regJoinedItem.getModel(), true);
			break;
		case REGS_GENES_SEPARATE:
			regulatorMeanGroup.setSelected(regSeparateItem.getModel(), true);
			break;
		}
		
		sigmaMeanMenu.add(moduleWideItem);
		sigmaMeanMenu.add(networkWideItem);
		sigmaMeanMenu.addSeparator();
		sigmaMeanMenu.add(regSeparateItem);
		sigmaMeanMenu.add(regJoinedItem);
		
		
		
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem outputDirItem = new JMenuItem(new ChangeOutputDirAction(guiModel));
		settingsMenu.add(outputDirItem);
		
		JMenu outputFormatMenu = new JMenu("Output format");
		ButtonGroup outputFormatGroup = new ButtonGroup();
		JMenuItem epsItem = new JRadioButtonMenuItem(new SetOutputFormatAction(guiModel, OutputFormat.EPS));
		JMenuItem pdfItem = new JRadioButtonMenuItem(new SetOutputFormatAction(guiModel, OutputFormat.PDF));
		outputFormatGroup.add(epsItem);
		outputFormatGroup.add(pdfItem);
		switch(guiModel.getOutputFormat()){
		case EPS:
			outputFormatGroup.setSelected(epsItem.getModel(), true);			
			break;
		case PDF:
			outputFormatGroup.setSelected(pdfItem.getModel(), true);			
			break;
		case PNG:
			break;
		}
		outputFormatMenu.add(epsItem);
		outputFormatMenu.add(pdfItem);
		settingsMenu.add(outputFormatMenu);

		JMenu helpMenu = new JMenu("Help");
		
		debugBoxItem = new JCheckBoxMenuItem(new ToggleDebugModeAction(guiModel));
		debugBoxItem.setSelected(guiModel.isDebugMode());
		JMenuItem aboutItem = new JMenuItem("About...");
		
		final String version = model.getVersion();
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), "ModuleViewer "+version+". Written by thpar");
			}
		});
		
		helpMenu.add(debugBoxItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
		
		
		add(fileMenu);
		add(viewMenu);
		add(settingsMenu);
		add(helpMenu);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.saveSessionAsItem.setEnabled(model.isEssentialsLoaded());
		this.treeStructureBoxItem.setSelected(guiModel.isDrawTreeStructure());
		this.conditionLabelBoxItem.setSelected(guiModel.isDrawConditionLabels());
		this.debugBoxItem.setSelected(guiModel.isDebugMode());
		
		//switch from genes and regs joined to separate if guiModel says so
		switch(guiModel.getMeanScopeGeneReg()){
		case REGS_GENES_JOINED:
			this.regulatorMeanGroup.setSelected(this.regJoinedItem.getModel(), true);
			break;
		case REGS_GENES_SEPARATE:
			this.regulatorMeanGroup.setSelected(this.regSeparateItem.getModel(), true);
			break;
		}
		//those options should only be available if data points are calculate over a module
		regSeparateItem.setEnabled(guiModel.getMeanScopeModNet() == MeanScopeModNet.MODULE_WIDE);
		regJoinedItem.setEnabled(guiModel.getMeanScopeModNet() == MeanScopeModNet.MODULE_WIDE);
		
		recentSessionMenu.removeAll();
		for (File recentSession : guiModel.getRecentSessions()){
			recentSessionMenu.add(new LoadRecentSessionAction(model, guiModel, recentSession));
		}
		if (guiModel.getRecentSessions().size()==0){
			recentSessionMenu.setEnabled(false);
		} else {
			recentSessionMenu.setEnabled(true);
		}
	}
}
