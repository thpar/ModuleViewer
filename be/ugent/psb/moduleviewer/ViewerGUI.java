package be.ugent.psb.moduleviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


public class ViewerGUI {
	
	private JFrame window;
	
	
	public ViewerGUI(){
		
	}

	
	public void startGUI() {
		//create and load the module network
		
		Model model = new Model();
		ModuleNetwork modnet = model.getModnet();
		GUIModel guiModel = new GUIModel();
		guiModel.setDrawConditionAnnotationLegend(true);
		guiModel.setDrawConditionAnnotations(true);

		this.window = new JFrame("ModuleViewer");
		guiModel.setTopContainer(window);
		
		ModuleLabel modLabel = new ModuleLabel(modnet, guiModel);
		
		JScrollPane scroll = new JScrollPane(modLabel);
		
		JPanel modulePanel = new JPanel();
		modulePanel.setLayout(new BorderLayout());
		modulePanel.add(scroll, BorderLayout.CENTER);
		
		JPanel toolBars = new JPanel();
		toolBars.setLayout(new BoxLayout(toolBars, BoxLayout.LINE_AXIS));	
		toolBars.add(new NavigationToolBar(model, guiModel));
		modulePanel.add(toolBars, BorderLayout.NORTH);

		modulePanel.add(new StatusBar(guiModel), BorderLayout.SOUTH);
		
		window.setJMenuBar(new MainMenu(model, guiModel));
		window.setContentPane(modulePanel);
		window.setMinimumSize(new Dimension(window.getSize().width, 300));
		
		window.pack();
		modLabel.initCanvas();
		window.pack();
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
		
	
	

}