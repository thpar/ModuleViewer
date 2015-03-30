package be.ugent.psb.moduleviewer.dialogs;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import be.ugent.psb.moduleviewer.Logger;

public class LogDialog extends JDialog implements Observer{


	private static final long serialVersionUID = 1L;
	private Logger logger;
	private JTextArea log;
	
	public LogDialog(Logger logger){
		this.logger = logger;
		this.logger.addObserver(this);
		
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(500,300));
		
		log = new JTextArea();
		log.setColumns(80);
		log.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(log);
		
		this.add(scrollPane, BorderLayout.CENTER);
		updateLogField();
	}
	
	private void updateLogField(){
		List<String> logEntries = this.logger.getLog();
		StringBuffer fullLog = new StringBuffer();
		for (String entry : logEntries){
			fullLog.append(entry);
		}
		log.setText(fullLog.toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		updateLogField();
	}
	
}
