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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.ugent.psb.moduleviewer.actions.FileNameRegexFilter;
import be.ugent.psb.moduleviewer.model.GUIModel;

/**
 * A page from the data {@link LoadingWizard}.
 * 
 * @author Thomas Van Parys
 *
 */
public class WizardPage extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String title;
	private JTextField fileTextField;
	private GUIModel guiModel;
	private FileNameRegexFilter filter;
	private boolean optional = false;
	private LoadingWizard wizard;
	
	/**
	 * Create a new wizard page.
	 * 
	 * @param wizard the parent dialog {@link LoadingWizard}
	 * @param title title for this page, the content and the {@link JFileChooser}
	 * @param descr description of the file content.
	 */
	public WizardPage(LoadingWizard wizard, String title, String descr){
		this(wizard, title, descr, null, false);
	}
	
	/**
	 * Create a new wizard page.
	 * 
	 * @param wizard the parent dialog {@link LoadingWizard}
	 * @param title title for this page, the content and the {@link JFileChooser}
	 * @param descr description of the file content.
	 * @param optional states if the file loaded on this page is optional (default: false)
	 */
	public WizardPage(LoadingWizard wizard, String title, String descr, boolean optional){
		this(wizard, title, descr, null, optional);
	}
	
	/**
	 * Create a new wizard page.
	 * 
	 * @param wizard the parent dialog {@link LoadingWizard}
	 * @param title title for this page, the content and the {@link JFileChooser}
	 * @param descr description of the file content.
	 * @param extensions regular expression and description of the file extensions.
	 * @param optional states if the file loaded on this page is optional (default: false)
	 */
	public WizardPage(LoadingWizard wizard, String title, String descr, FileNameRegexFilter extensions, boolean optional){
		this.wizard = wizard;
		this.guiModel = wizard.getGuiModel();
		this.title = title;
		this.filter = extensions;
		this.optional = optional;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLoweredBevelBorder(),
				BorderFactory.createEmptyBorder(10, 10, 10, 10) 
			));
		
		JPanel descrPanel = new JPanel();
		descrPanel.setAlignmentX(LEFT_ALIGNMENT);
		descrPanel.setLayout(new BoxLayout(descrPanel, BoxLayout.PAGE_AXIS));
		String titleLabelText = optional? title+" (optional)":title;
		descrPanel.add(new JLabel(titleLabelText));
		descrPanel.add(Box.createVerticalStrut(5));
		descrPanel.add(new JLabel(descr));
		descrPanel.setPreferredSize(new Dimension(400,200));
		this.add(descrPanel);
		
		JPanel browsePanel = new JPanel();
		browsePanel.setLayout(new BoxLayout(browsePanel,BoxLayout.LINE_AXIS));
		browsePanel.setAlignmentX(LEFT_ALIGNMENT);
		fileTextField = new JTextField(40);
		browsePanel.add(fileTextField);
		JButton browseButton = new JButton("Browse...");
		browseButton.addActionListener(this);
		browsePanel.add(browseButton);
		this.add(browsePanel);
		
	}
	
	
	/**
	 * 
	 * @return true if the file loaded on this page is optional
	 */
	public boolean isOptional() {
		return optional;
	}
	
	/**
	 * 
	 * @param optional true if the file loaded on this page is optional
	 */
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	/**
	 * 
	 * @return the title of this page
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * @return the file selected on this page. Returns null if the file doesn't exist.
	 */
	public File getFile() {
		File file = new File(this.fileTextField.getText());
		if (file.exists()){
			return file;
		} else {
			return null;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle(title);
		if (filter != null){
			fc.setFileFilter(filter);			
		}
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		if (answer == JFileChooser.APPROVE_OPTION){
			this.fileTextField.setText(fc.getSelectedFile().getAbsolutePath());
		}
		wizard.refreshButtons();
	}
	
	
}
