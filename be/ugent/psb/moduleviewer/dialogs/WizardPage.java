package be.ugent.psb.moduleviewer.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
	private FileNameRegexFilter filter;
	private boolean optional = false;
	
	public WizardPage(GUIModel guiModel, String title, String descr){
		this(guiModel, title, descr, null, false);
	}
	public WizardPage(GUIModel guiModel, String title, String descr, boolean optional){
		this(guiModel, title, descr, null, optional);
	}
	public WizardPage(GUIModel guiModel, String title, String descr, FileNameRegexFilter extensions, boolean optional){
		this.guiModel = guiModel;
		this.title = title;
		this.filter = extensions;
		this.optional = optional;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(new JLabel(descr));
		if (optional){
			this.add(new JLabel("(Optional)"));			
		}
		
		fileTextField = new JTextField();
		this.add(fileTextField);
		JButton browseButton = new JButton("Browse...");
		browseButton.addActionListener(this);
		this.add(browseButton);
		
	}
	
	

	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public String getTitle() {
		return this.title;
	}

	
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
		
	}
	
	
}
