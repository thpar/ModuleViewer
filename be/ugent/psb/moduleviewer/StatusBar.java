package be.ugent.psb.moduleviewer;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import be.ugent.psb.moduleviewer.model.GUIModel;

public class StatusBar extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private JLabel state;
	
	private JProgressBar progressBar = new JProgressBar();
	
	
	public StatusBar(GUIModel guiModel){
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.guiModel = guiModel;
		guiModel.addObserver(this);
				
		state = new JLabel();
		
		add(state);
		add(Box.createHorizontalGlue());
		
		add(progressBar);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setMaximumSize(new Dimension(40, 100));
		
		update(null, null);
	}


	@Override
	public void update(Observable o, Object arg) {
		state.setText(guiModel.getStateString());
		if (guiModel.isShowProgressBar()){
			progressBar.setVisible(true);
			progressBar.setValue(guiModel.getProgressBarProgress());
		} else {
			progressBar.setVisible(false);
		}
		
	}

	
}
