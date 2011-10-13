package be.ugent.psb.moduleviewer;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import be.ugent.psb.moduleviewer.actions.ExportToFigureAction;
import be.ugent.psb.moduleviewer.actions.NavModuleAction;
import be.ugent.psb.moduleviewer.actions.SetPointModeAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;
import be.ugent.psb.moduleviewer.model.GUIModel.PointMode;

/**
 * Toolbar with navigation and export buttons.
 * 
 * @author thpar
 *
 */
public class NavigationToolBar extends JToolBar implements Observer, FocusListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JLabel totalLabel;
	private GUIModel guiModel;
	private JTextField locationField;
	private Model model;
	private JButton exportButton;	
	JButton nextButton = new JButton();
	JButton prevButton = new JButton();
	JToggleButton pointButton = new JToggleButton();
	JToggleButton panButton = new JToggleButton();
	private ButtonGroup pointModes;
	
	public NavigationToolBar(Model model, GUIModel guiModel){
		super("ModuleViewer Navigation");

		this.model = model;
		this.guiModel = guiModel;
		model.addObserver(this);
		guiModel.addObserver(this);
		
		locationField = new JTextField();
		locationField.setColumns(4);
		locationField.setMaximumSize(new Dimension(30, 50));
		locationField.addFocusListener(this);
		locationField.addKeyListener(this);
		totalLabel = new JLabel();
		
		exportButton = new JButton();
		
		nextButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/next.png")),+1, guiModel));
		prevButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/prev.png")),-1, guiModel));
		exportButton.setAction(new ExportToFigureAction(model, guiModel, false));
		exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/"+guiModel.getOutputFormat()+"_icon.jpg")));
		
		pointModes = new ButtonGroup();
		pointModes.add(pointButton);
		pointModes.add(panButton);
		this.setPointMode(guiModel.getPointMode());		
				
		pointButton.setAction(new SetPointModeAction(guiModel, PointMode.POINT));
		panButton.setAction(new SetPointModeAction(guiModel, PointMode.PAN));
		
		pointButton.setText(null);
		panButton.setText(null);
		
		//TODO disabling the point button as long as panning is the only useful thing to do
		pointButton.setEnabled(false);
		
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(locationField);
		add(totalLabel);
		add(prevButton);
		add(nextButton);
		
		add(new JToolBar.Separator());
		add(exportButton);
		add(new JToolBar.Separator());
		add(pointButton);
		add(panButton);
		
		this.update(null, null);
	}


	@Override
	public void update(Observable o, Object arg) {
		int modnr = guiModel.getDisplayedModule();
		ModuleNetwork modnet = model.getModnet();
		prevButton.setEnabled(!modnet.isFirstModule(modnr));
		nextButton.setEnabled(!modnet.isLastModule(modnr));
		locationField.setText(String.valueOf(modnr));
		int totalModnr = 0;
		String totalString;
		if (modnet.getModules() != null && modnet.getModules().size()>0){
			totalModnr=	modnet.getModules().size()-1;
			totalString = new String("/"+totalModnr);
		} else {
			totalString = new String("--");
		}
		totalLabel.setText(totalString);
		setPointMode(guiModel.getPointMode());
		
		exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/"+guiModel.getOutputFormat()+"_icon.jpg")));
	}


	private void setPointMode(PointMode pointMode) {	
		switch(pointMode){
		case POINT:
			pointModes.setSelected(pointButton.getModel(), true);
			break;
		case PAN:
			pointModes.setSelected(panButton.getModel(), true);
			break;
		}
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		goToModule();
	}

	/**
	 * Jumps to the module given in the navigation text field.
	 */
	private void goToModule(){
		String content = locationField.getText();
		try {
			int contNr = Integer.parseInt(content);
			guiModel.setDisplayedModule(contNr);
		} catch (NumberFormatException e1) {
			locationField.setText(String.valueOf(guiModel.getDisplayedModule()));
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar()=='\n'){
			goToModule();
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
