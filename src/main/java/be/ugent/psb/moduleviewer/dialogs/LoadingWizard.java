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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import be.ugent.psb.moduleviewer.actions.FileNameRegexFilter;
import be.ugent.psb.moduleviewer.model.GUIModel;

/**
 * This modal dialogue presents the user with the list of required and optional loading options.
 * 
 * 
 * @author Thomas Van Parys
 *
 */
public class LoadingWizard extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	//button actions
	private final static String NEXT = "next"; 
	private final static String BACK = "back"; 
	private final static String CANCEL = "cancel"; 
	private final static String FINISH = "finish"; 

	//exit states
	public final static int CANCELLED = 0;
	public final static int APPROVED = 1;
	
	
	private int returnValue = CANCELLED;
	
	List<WizardPage> pages = new ArrayList<WizardPage>();

	private int currentPageNumber = 0;

	private JPanel mainPanel;

	private GUIModel guiModel;

	private JButton finishButton;

	private JButton nextButton;

	private JButton backButton;
	
	/**
	 * Constructu the wizard dialog. 
	 * 
	 * @param guiModel the GUIModel.
	 */
	public LoadingWizard(GUIModel guiModel){
		this.guiModel = guiModel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);
		
		createPages();
		
		this.setTitle(pages.get(currentPageNumber).getTitle());
		mainPanel.add(pages.get(currentPageNumber),BorderLayout.NORTH);
		mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.setModal(true);
		this.validate();
		this.pack();

	}
	
	/**
	 * Generates the separate wizard pages and add them to a list.
	 */
	private void createPages() {
		pages.add(new WizardPage(this, "Load expression data", 
				"<html>Select the file with expression data. "
				+ "This tab delimited matrix should have all conditions on the first line. Each subsquent line contains the gene id and its "
				+ "expression values.</html>"));
		pages.add(new WizardPage(this, "Load gene clusters", 
				"<html>The clustering of genes into modules. Each line has a module number and a pipe delimited list of gene ids.</html>"));
		pages.add(new WizardPage(this, "Load gene id mapping",
				"<html>A two column file with gene ids in the first and symbolic names in the second column.</html>", true));
		pages.add(new WizardPage(this, "Load condition clusters",
				"<html>Subclustering of conditions with similar expression patterns.</html>", new FileNameRegexFilter("XML files", ".*\\.xml"), true));
		pages.add(new WizardPage(this, "Load regulator clusters", 
				"<html>The list of regulators for each module. Each line needs a module id "
				+ "and a pipe delimited list of regulator gene ids.</html>", true));
		pages.add(new WizardPage(this, "Load annotations", 
				"<html>All integrated data that needs to be mapped onto the expression data in .mvf format. "
				+ "the .mvf file can contain multiple annotation blocks. Each block should"
				+ "start with a header defining its behaviour. The content of a block "
				+ "consists of lines with a module id, a pipe delimited list of gene ids and an optional label.</html>",
				new FileNameRegexFilter("MVF files", ".*\\.mvf"), true));
	}

	/**
	 * Create the panel containing the navigation and submit buttons.
	 * 
	 * @return the panel containing the navigation and submit buttons.
	 */
	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(CANCEL);
		cancelButton.addActionListener(this);
		
		nextButton = new JButton("Next >>");
		nextButton.setActionCommand(NEXT);
		nextButton.addActionListener(this);
		nextButton.setEnabled(this.currentPageNumber<pages.size()-1);
		
		backButton = new JButton("<< Back");
		backButton.setActionCommand(BACK);
		backButton.addActionListener(this);
		backButton.setEnabled(this.currentPageNumber>0);
		
		finishButton = new JButton("Finish");
		finishButton.setActionCommand(FINISH);
		finishButton.addActionListener(this);
		finishButton.setEnabled(this.ready());
		
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(backButton);
		buttonPanel.add(nextButton);
		buttonPanel.add(finishButton);
		return buttonPanel;
	}
	
	
	/**
	 * 
	 * @return true when the required files have been selected. 
	 */
	private boolean ready() {
		for (WizardPage page : pages){
			if (!page.isOptional() && page.getFile()==null){
				return false;
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(NEXT)){
			nextPage();
		} else if (action.equals(BACK)){
			previousPage();
		} else if (action.equals(CANCEL)){
			this.dispose();
		} else if (action.equals(FINISH)){
			this.returnValue = APPROVED;
			this.dispose();
		}
	}
	

	@Override
	public void setTitle(String title){
		super.setTitle("Load data wizard - "+title);
	}

	/**
	 * Go to the previous page.
	 */
	private void previousPage() {
		switchPage(currentPageNumber-1);		
	}
	
	/**
	 * Go to the next page.
	 */
	private void nextPage() {
		switchPage(currentPageNumber+1);		
	}

	/**
	 * Go to the given page. The old page will be removed from the layout
	 * and replaced with the new page. The state of the navigation buttons will
	 * be refreshed and the dialog will be revalidated and repainted.
	 * 
	 * @param newPage number of the page to switch to.
	 */
	private void switchPage(int newPage) {
		int oldPageNumber = currentPageNumber;
		currentPageNumber = newPage;
		if (currentPageNumber<0){
			currentPageNumber = 0;
		} else if (currentPageNumber>=pages.size()){
			currentPageNumber = pages.size()-1;
		}
		if (currentPageNumber != oldPageNumber){
			WizardPage oldPage = pages.get(oldPageNumber);
			WizardPage currentPage = pages.get(currentPageNumber);
			this.setTitle(currentPage.getTitle());
			this.mainPanel.remove(oldPage);
			this.mainPanel.add(currentPage, BorderLayout.NORTH);
			
			this.refreshButtons();
			
			this.revalidate();
			this.repaint();
		}
			
	}
	
	/**
	 * 
	 * @return the GUIModel.
	 */
	public GUIModel getGuiModel() {
		return guiModel;
	}

	/**
	 * Set the state of the navigation buttons depending on the currentpage
	 * and the files that have already been loaded.
	 */
	public void refreshButtons(){
		nextButton.setEnabled(this.currentPageNumber<pages.size()-1);
		backButton.setEnabled(this.currentPageNumber>0);
		finishButton.setEnabled(this.ready());		
	}
	
	/**
	 * Return the exit state of the dialog. Constants can be CANCELLED or APPROVED.
	 * @return the exit state of the dialog.
	 */
	public int getReturnValue(){
		return returnValue;
	}
	
	/**
	 * a list of files, selected throught the wizard.
	 * 
	 * @return a list of files, selected throught the wizard.
	 */
	public List<File> getSelectedFiles(){
		List<File> selectedFiles = new ArrayList<File>();
		for (WizardPage page: pages){
			selectedFiles.add(page.getFile());
		}
		return selectedFiles;
	}

}
