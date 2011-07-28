package be.ugent.psb.moduleviewer;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import be.ugent.psb.moduleviewer.actions.ExportToFigureAction;
import be.ugent.psb.moduleviewer.actions.NavModuleAction;
import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;

public class NavigationToolBar extends JToolBar implements Observer, FocusListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JLabel totalLabel;
	private GUIModel guiModel;
	private JTextField locationField;
	private Model model;
	private JButton exportButton;	
	
	public NavigationToolBar(Model model, GUIModel guiModel){
		super("ModuleViewer Navigation");

		this.model = model;
		this.guiModel = guiModel;
		guiModel.addObserver(this);
		
		locationField = new JTextField();
		locationField.setColumns(4);
		locationField.setMaximumSize(new Dimension(30, 50));
		locationField.addFocusListener(this);
		locationField.addKeyListener(this);
		totalLabel = new JLabel();
		
		JButton nextButton = new JButton();
		JButton prevButton = new JButton();
		exportButton = new JButton();
		
		nextButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/next.png")),+1, guiModel));
		prevButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/prev.png")),-1, guiModel));
		exportButton.setAction(new ExportToFigureAction(model, guiModel, false));
		exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/"+guiModel.getOutputFormat()+"_icon.jpg")));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(locationField);
		add(totalLabel);
		add(prevButton);
		add(nextButton);
		
		add(new JToolBar.Separator());
		add(exportButton);
		
		this.update(null, null);
	}


	@Override
	public void update(Observable o, Object arg) {
		int modnr = guiModel.getDisplayedModule();
		ModuleNetwork modnet = model.getModnet();
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
		
		exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/"+guiModel.getOutputFormat()+"_icon.jpg")));
	}


	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void focusLost(FocusEvent e) {
		goToModule();
	}

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
