package be.ugent.psb.moduleviewer.dialogs;

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

public class LoadingWizard extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private final static String NEXT = "next"; 
	private final static String BACK = "back"; 
	
	private final static String CANCEL = "cancel"; 
	private final static String FINISH = "finish"; 

	
	List<WizardPage> pages = new ArrayList<WizardPage>();

	private int currentPageNumber = 0;

	private JPanel mainPanel;

	private GUIModel guiModel;

	private JButton finishButton;

	private JButton nextButton;

	private JButton backButton;
	
	public LoadingWizard(GUIModel guiModel){
		this.guiModel = guiModel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);
		
		createPages();
		
		this.setTitle(pages.get(currentPageNumber).getTitle());
		mainPanel.add(pages.get(currentPageNumber), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.validate();
		this.pack();

	}
	
	private void createPages() {
		pages.add(new WizardPage(guiModel, "Load expression data", 
				"<html>Select the file with expression data. "
				+ "This tab delimited matrix should have all conditions on the first line. Each subsquent line contains the gene id and its "
				+ "expression values.</html>"));
		pages.add(new WizardPage(guiModel, "Load gene clusters", 
				"<html>The clustering of genes into modules. Each line has a module number and a pipe delimited list of gene ids.</html>"));
		pages.add(new WizardPage(guiModel, "Load gene id mapping",
				"<html>A two column file with gene ids in the first and symbolic names in the second column.</html>", true));
		pages.add(new WizardPage(guiModel, "Load condition clusters",
				"<html>Subclustering of conditions with similar expression patterns.</html>", new FileNameRegexFilter("XML files", ".*\\.xml"), true));
		pages.add(new WizardPage(guiModel, "Load regulator clusters", 
				"<html>The list of regulators for each module. Each line needs a module id "
				+ "and a pipe delimited list of regulator gene ids.</html>", true));
		pages.add(new WizardPage(guiModel, "Load annotations", 
				"<html>All integrated data that needs to be mapped onto the expression data in .mvf format. "
				+ "the .mvf file can contain multiple annotation blocks. Each block should"
				+ "start with a header defining its behaviour. The content of a block "
				+ "consists of lines with a module id, a pipe delimited list of gene ids and an optional label.</html>",
				new FileNameRegexFilter("MVF files", ".*\\.mvf"), true));
	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
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
			this.startLoading();
			this.dispose();
		}
	}
	
	private void startLoading() {
		for (WizardPage page : pages){
			File file = page.getFile();
			System.out.println(file);
		}
		
	}

	@Override
	public void setTitle(String title){
		super.setTitle("Load data wizard - "+title);
	}

	private void previousPage() {
		switchPage(currentPageNumber-1);		
	}
	private void nextPage() {
		switchPage(currentPageNumber+1);		
	}

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
			this.mainPanel.add(currentPage, BorderLayout.CENTER);
			
			nextButton.setEnabled(this.currentPageNumber<pages.size()-1);
			backButton.setEnabled(this.currentPageNumber>0);
			finishButton.setEnabled(this.ready());
			
			this.revalidate();
			this.repaint();
		}
			
	}

}
