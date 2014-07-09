package be.ugent.psb.moduleviewer.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.ugent.psb.moduleviewer.actions.FileNameRegexFilter;
import be.ugent.psb.moduleviewer.model.GUIModel;

public class WizardPage extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private String title;
	private JTextField fileTextField;
	private GUIModel guiModel;
	
	
	public WizardPage(GUIModel guiModel, String title, String descr, List<String> extentions){
		this.guiModel = guiModel;
		this.title = title;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(new JLabel(descr));
		
		fileTextField = new JTextField();
		this.add(fileTextField);
		JButton browseButton = new JButton("Browse...");
		browseButton.addActionListener(this);
		this.add(browseButton);
		
	}

	public String getTitle() {
		return this.title;
	}

	
	public File getFile() {
		return new File(fileTextField.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser(guiModel.getCurrentDir());
		fc.setDialogTitle(title);
		fc.setFileFilter(new FileNameRegexFilter("condition tree files", ".*\\.xml"));
		int answer = fc.showOpenDialog(guiModel.getTopContainer());
		
	}
	
	
}
