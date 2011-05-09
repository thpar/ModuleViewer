package be.ugent.psb.moduleviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import be.ugent.psb.moduleviewer.actions.ChangeEpsOutputDirAction;
import be.ugent.psb.moduleviewer.actions.ExportToEPSAction;
import be.ugent.psb.moduleviewer.actions.LoadDataAction;
import be.ugent.psb.moduleviewer.actions.LoadModulesAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;

public class MainMenu extends JMenuBar {

	
	private static final long serialVersionUID = 1L;
	
	public MainMenu(Model model, final GUIModel guiModel){

		JMenu fileMenu = new JMenu("File");
		JMenuItem loadDataItem = new JMenuItem(new LoadDataAction(model, guiModel));
		JMenuItem loadModnetItem = new JMenuItem(new LoadModulesAction(model, guiModel));


		JMenuItem exportItem = new JMenuItem(new ExportToEPSAction(model, guiModel));
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(loadDataItem);
		fileMenu.add(loadModnetItem);

		
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
