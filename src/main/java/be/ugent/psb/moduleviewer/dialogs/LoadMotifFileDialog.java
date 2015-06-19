package be.ugent.psb.moduleviewer.dialogs;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

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
