package be.ugent.psb.moduleviewer.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class LoadingWizard extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private final static String NEXT = "next"; 
	private final static String BACK = "back"; 
	
	private final static String CANCEL = "cancel"; 

	
	
	List<WizardPage> pages = new ArrayList<WizardPage>();

	private int currentPageNumber = 0;

	private JPanel mainPanel;
	
	public LoadingWizard(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.setContentPane(mainPanel);
		
		createPages();
		
		this.setTitle(pages.get(currentPageNumber).getTitle());
		mainPanel.add(pages.get(currentPageNumber), BorderLayout.CENTER);
		mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

	}
	
	private void createPages() {
		pages.add(new WizardPage("A", "Load A"));
		pages.add(new WizardPage("B", "Load B"));
	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(CANCEL);
		
		
		JButton nextButton = new JButton("Next >>");
		nextButton.setActionCommand(NEXT);
		nextButton.addActionListener(this);
		JButton backButton = new JButton("<< Back");
		backButton.setActionCommand(BACK);
		backButton.addActionListener(this);
		
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalStrut(5));
		buttonPanel.add(backButton);
		buttonPanel.add(nextButton);
		return buttonPanel;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(NEXT)){
			nextPage();
		} else if (action.equals(BACK)){
			previousPage();
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
			this.revalidate();
			this.repaint();
		}
			
	}

}
