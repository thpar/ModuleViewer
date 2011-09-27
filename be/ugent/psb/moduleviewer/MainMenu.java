package be.ugent.psb.moduleviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import be.ugent.psb.moduleviewer.actions.NewSessionAction;
import be.ugent.psb.moduleviewer.actions.SaveSessionAsAction;
import be.ugent.psb.moduleviewer.actions.SetOutputFormatAction;
import be.ugent.psb.moduleviewer.actions.ToggleDebugModeAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
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
	
	public MainMenu(Model model, final GUIModel guiModel){

		this.model = model;
		model.addObserver(this);

		
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadDataItem = new JMenuItem(new LoadDataAction(model, guiModel));
		JMenuItem loadGeneTreeItem = new JMenuItem(new LoadGeneTreeAction(model, guiModel));
		JMenuItem loadConditionTreeItem = new JMenuItem(new LoadConditionTreeAction(model, guiModel));
		JMenuItem loadRegTreeItem = new JMenuItem(new LoadRegulatorTreeAction(model, guiModel));
		JMenuItem loadAnnotItem = new JMenuItem(new LoadAnnotationAction(model, guiModel));
		
		saveSessionAsItem = new JMenuItem(new SaveSessionAsAction(model, guiModel));
		saveSessionAsItem.setEnabled(false);
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
		fileMenu.add(loadConditionTreeItem);
		fileMenu.add(loadRegTreeItem);
		fileMenu.add(loadAnnotItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(newSessionItem);
		fileMenu.add(saveSessionAsItem);
		fileMenu.add(loadSessionItem);
		
		fileMenu.addSeparator();
		
		fileMenu.add(exportItem);
		fileMenu.add(exportAllItem);
		fileMenu.addSeparator();
		
		fileMenu.add(exitItem);
		
		
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
		
		JCheckBoxMenuItem debugBoxItem = new JCheckBoxMenuItem(new ToggleDebugModeAction(guiModel));
		debugBoxItem.setState(guiModel.isDebugMode());
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
		add(settingsMenu);
		add(helpMenu);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.saveSessionAsItem.setEnabled(model.isEssentialsLoaded());
	}
}
