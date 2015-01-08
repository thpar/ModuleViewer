package be.ugent.psb.moduleviewer.dialogs;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoadMotifFileDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

//	private int percentCutoff;
	private String listName;

//	private SpinnerNumberModel spinModel;

	private JTextField nameField;
	
	public LoadMotifFileDialog(Frame owner, String fileName){
		super(owner);
		this.setLocationRelativeTo(owner);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
//		spinModel = new SpinnerNumberModel(0, 0, 100, 5);
//		JSpinner percSpin = new JSpinner(spinModel);
		nameField = new JTextField(fileName);
		
		JPanel inputPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		inputPanel.setLayout(new GridLayout(2,2));
//		inputPanel.add(new JLabel("Percentage cutoff: "));
//		inputPanel.add(percSpin);
		inputPanel.add(new JLabel("List name: "));
		inputPanel.add(nameField);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("Ok");
		buttonPanel.add(okButton);
		okButton.addActionListener(this);
		
		this.add(inputPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		this.percentCutoff = spinModel.getNumber().intValue();
		this.listName = nameField.getText();
		this.dispose();
	}

	public String getListName() {
		return listName;
	}

//	public int getPercentCutoff() {
//		return percentCutoff;
//	}

		
	
}
