package be.ugent.psb.moduleviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import be.ugent.psb.moduleviewer.actions.ChangeEpsOutputDirAction;
import be.ugent.psb.moduleviewer.actions.ExportToEPSAction;
import be.ugent.psb.moduleviewer.actions.LoadAnnotationAction;
import be.ugent.psb.moduleviewer.actions.LoadConditionTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadDataAction;
import be.ugent.psb.moduleviewer.actions.LoadGeneTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadRegulatorTreeAction;
import be.ugent.psb.moduleviewer.actions.LoadSessionAction;
import be.ugent.psb.moduleviewer.actions.SaveSessionAsAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

public class MainMenu extends JMenuBar implements Observer{

	
	private static final long serialVersionUID = 1L;
	private JMenuItem saveSessionAsItem;
	private Model model;
	private GUIModel guiModel;
	
	public MainMenu(Model model, final GUIModel guiModel){

		this.model = model;
		model.addObserver(this);

		this.guiModel = guiModel;
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadDataItem = new JMenuItem(new LoadDataAction(model, guiModel));
		JMenuItem loadGeneTreeItem = new JMenuItem(new LoadGeneTreeAction(model, guiModel));
		JMenuItem loadConditionTreeItem = new JMenuItem(new LoadConditionTreeAction(model, guiModel));
		JMenuItem loadRegTreeItem = new JMenuItem(new LoadRegulatorTreeAction(model, guiModel));
		JMenuItem loadAnnotItem = new JMenuItem(new LoadAnnotationAction(model, guiModel));
		
		saveSessionAsItem = new JMenuItem(new SaveSessionAsAction(model, guiModel));
		saveSessionAsItem.setEnabled(false);
		JMenuItem loadSessionItem = new JMenuItem(new LoadSessionAction(model, guiModel));
		


		JMenuItem exportItem = new JMenuItem(new ExportToEPSAction(model, guiModel));
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(loadDataItem);
		fileMenu.add(loadGeneTreeItem);
		fileMenu.add(loadConditionTreeItem);
		fileMenu.add(loadRegTreeItem);
		fileMenu.add(loadAnnotItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(saveSessionAsItem);
		fileMenu.add(loadSessionItem);
		
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
				String version;
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(
							this.getClass().getResourceAsStream("/VERSION.TXT")));
					version = br.readLine();
					br.close();
				} catch (IOException e1) {
					System.err.println(e1.getMessage());
					version = "??";
				}
				JOptionPane.showMessageDialog(guiModel.getTopContainer(), "ModuleViewer "+version+". Written by thpar");
			}
		});

		helpMenu.add(aboutItem);
		
		
		add(fileMenu);
		add(settingsMenu);
		add(helpMenu);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.saveSessionAsItem.setEnabled(model.isEssentialsLoaded());
	}
}
