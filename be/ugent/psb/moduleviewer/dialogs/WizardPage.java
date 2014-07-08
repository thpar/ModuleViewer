package be.ugent.psb.moduleviewer.dialogs;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WizardPage extends JPanel{

	private static final long serialVersionUID = 1L;
	private String title;
	
	public WizardPage(String title, String descr){
		this.title = title;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(new JLabel(descr));
		
		
	}

	public String getTitle() {
		return this.title;
	}
	
	
}
