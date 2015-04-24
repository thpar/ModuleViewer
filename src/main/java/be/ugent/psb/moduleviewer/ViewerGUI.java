package be.ugent.psb.moduleviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ParseException;

/**
 * Main class that takes care of the ModuleViewer GUI. Manages and launches the window object.
 * @author thpar
 *
 */
public class ViewerGUI implements Observer, WindowListener {
	
	/**
	 * Main window
	 */
	private JFrame window;
	
	private ModuleLabel modLabel;

	private JScrollPane scroll;

	private GUIModel guiModel;

	private Model model;

	private DragMoveListener dragMoveListener;
	
	private PointMode currentPointMode;

	private String[] args;
	
	public ViewerGUI(){
		
	}

	/**
	 * Loads the moduleviewer

	 * @param links
	 */
	public ViewerGUI(String[] args){
		this.args = args;
	}
	
	
	/**
	 * Packs and launches the GUI
	 */
	public void startGUI() {
		//create and load the module network
		
		model = new Model();
		guiModel = new GUIModel(model);
		guiModel.addObserver(this);

		ArgumentLoader loader = new ArgumentLoader(model);
		
		if (args.length != 0){
			try {
				loader.processArguments(args);
			} catch (ParseException e) {
				System.err.println("Failed to parse initial data. Starting GUI without data.");
				e.printStackTrace();
			} catch (IOException e){
				System.err.println("Failed to load initial data files. Starting GUI without data.");
				e.printStackTrace();
			}
		}
		
		
		guiModel.setDrawConditionAnnotationLegend(false);
		guiModel.setDrawConditionAnnotations(false);

		this.window = new JFrame("ModuleViewer - "+model.getVersion());
		guiModel.setTopContainer(window);
		
		modLabel = new ModuleLabel(model, guiModel);
		
		scroll = new JScrollPane(modLabel);
		dragMoveListener = new DragMoveListener(scroll.getViewport(), modLabel);
		if (guiModel.getPointMode()==PointMode.PAN){
			currentPointMode = PointMode.PAN;
			modLabel.addMouseMotionListener(dragMoveListener);
			modLabel.addMouseListener(dragMoveListener);
		} else {
			currentPointMode = PointMode.POINT;
		}
		
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
		
		window.addWindowListener(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (currentPointMode != guiModel.getPointMode()){
			currentPointMode = guiModel.getPointMode();
			switch(guiModel.getPointMode()){
			case PAN:
				modLabel.addMouseMotionListener(dragMoveListener);				
				modLabel.addMouseListener(dragMoveListener);
				break;
			case POINT:
				modLabel.removeMouseMotionListener(dragMoveListener);
				modLabel.removeMouseListener(dragMoveListener);
				break;
			}
		}
			

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			guiModel.saveState();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {		
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
		
	

}
