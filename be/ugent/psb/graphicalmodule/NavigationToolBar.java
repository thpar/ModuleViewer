package be.ugent.psb.graphicalmodule;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import be.ugent.psb.ModuleNetwork.ModuleNetwork;
import be.ugent.psb.graphicalmodule.actions.ExportToEPSAction;
import be.ugent.psb.graphicalmodule.actions.NavModuleAction;
import be.ugent.psb.graphicalmodule.model.GraphicalModuleModel;

public class NavigationToolBar extends JToolBar implements Observer, FocusListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private JLabel totalLabel;
	private ModuleNetwork modnet;
	private GraphicalModuleModel guiModel;
	private JTextField locationField;	
	
	public NavigationToolBar(ModuleNetwork modnet, GraphicalModuleModel guiModel){
		super("LeMoNe Navigation");

		this.modnet = modnet;
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
		JButton exportButton = new JButton();
		
		nextButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/next.png")),+1, guiModel));
		prevButton.setAction(new NavModuleAction(new ImageIcon(getClass().getResource("/icons/prev.png")),-1, guiModel));
		exportButton.setAction(new ExportToEPSAction(new ImageIcon(getClass().getResource("/icons/eps_icon.jpg")),modnet, guiModel));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(locationField);
		add(totalLabel);
		add(prevButton);
		add(nextButton);
		
		add(new JToolBar.Separator());
		add(exportButton);
//		add(Box.createHorizontalGlue());
		
		this.update(null, null);
	}


	@Override
	public void update(Observable o, Object arg) {
		int modnr = guiModel.getDisplayedModule();
		locationField.setText(String.valueOf(modnr));
		int totalModnr = 0;
		String totalString;
		if (modnet.moduleSet != null){
			totalModnr=	modnet.moduleSet.size();
			totalString = new String("/"+totalModnr);
		} else {
			totalString = new String("--");
		}
		totalLabel.setText(totalString);
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
