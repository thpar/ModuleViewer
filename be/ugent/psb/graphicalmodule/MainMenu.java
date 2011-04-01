package be.ugent.psb.graphicalmodule;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.graphicalmodule.actions.ChangeEpsOutputDirAction;
import be.ugent.psb.graphicalmodule.actions.ExportToEPSAction;
import be.ugent.psb.graphicalmodule.actions.LoadAracycAction;
import be.ugent.psb.graphicalmodule.actions.LoadBiNGOAction;
import be.ugent.psb.graphicalmodule.actions.LoadConditionAnnotationAction;
import be.ugent.psb.graphicalmodule.actions.LoadGeneCheckListAction;
import be.ugent.psb.graphicalmodule.actions.LoadGeneLinkAction;
import be.ugent.psb.graphicalmodule.actions.LoadGeneMapAction;
import be.ugent.psb.graphicalmodule.actions.LoadModulesAction;
import be.ugent.psb.graphicalmodule.actions.LoadMotifFileAction;
import be.ugent.psb.graphicalmodule.actions.LoadTopRegulatorsAction;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

public class MainMenu extends JMenuBar {

	
	private static final long serialVersionUID = 1L;
	
	public MainMenu(ModuleNetwork modnet, final GraphicalModuleModel guiModel){
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadModnetItem = new JMenuItem(new LoadModulesAction(modnet, guiModel));
		JMenuItem loadTopRegItem = new JMenuItem(new LoadTopRegulatorsAction(modnet, guiModel));
		JMenuItem loadGeneMapItem = new JMenuItem(new LoadGeneMapAction(modnet, guiModel));
		JMenuItem loadBiNGOItem = new JMenuItem(new LoadBiNGOAction(modnet, guiModel));
		JMenuItem loadGeneLinkItem = new JMenuItem(new LoadGeneLinkAction(modnet, guiModel));
		JMenuItem loadGeneCheckListItem = new JMenuItem(new LoadGeneCheckListAction(modnet, guiModel));
		JMenuItem loadAracycItem = new JMenuItem(new LoadAracycAction(modnet, guiModel));
		JMenuItem loadMotifFileItem = new JMenuItem(new LoadMotifFileAction((Frame)this.getTopLevelAncestor(), modnet, guiModel));
		JMenuItem loadCondMapItem = new JMenuItem(new LoadConditionAnnotationAction(modnet, guiModel));

		JMenuItem exportItem = new JMenuItem(new ExportToEPSAction(modnet, guiModel));
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(loadModnetItem);
		fileMenu.add(loadTopRegItem);
		fileMenu.add(loadGeneMapItem);
		fileMenu.add(loadBiNGOItem);
		fileMenu.add(loadGeneLinkItem);
		fileMenu.add(loadGeneCheckListItem);
		fileMenu.add(loadAracycItem);
		fileMenu.add(loadMotifFileItem);
		
		fileMenu.add(loadCondMapItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(exportItem);
		fileMenu.addSeparator();
		
		fileMenu.add(exitItem);
		
		
		JMenu settingsMenu = new JMenu("Settings");
		JMenuItem epsOutputDirItem = new JMenuItem(new ChangeEpsOutputDirAction(guiModel));
		settingsMenu.add(epsOutputDirItem);
		
		

		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About...");
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), "LeMoNe Viewer. Written by thpar");
			}
		});

		helpMenu.add(aboutItem);
		
		
		add(fileMenu);
		add(settingsMenu);
		add(helpMenu);
	}
}
