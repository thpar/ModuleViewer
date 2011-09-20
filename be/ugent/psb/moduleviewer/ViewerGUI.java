package be.ugent.psb.moduleviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.parsers.ConditionTreeParser;
import be.ugent.psb.moduleviewer.parsers.DataMatrixParser;
import be.ugent.psb.moduleviewer.parsers.GeneTreeParser;
import be.ugent.psb.moduleviewer.parsers.MVFParser;
import be.ugent.psb.moduleviewer.parsers.RegulatorTreeParser;

/**
 * Main class that takes care of the ModuleViewer GUI. Manages and launches the window object.
 * @author thpar
 *
 */
public class ViewerGUI {
	
	/**
	 * Main window
	 */
	private JFrame window;
	
	private List<URL> urls;
	
	public ViewerGUI(){
		
	}

	/**
	 * Loads the moduleviewer with online data.
	 * Not very robust! It expects the links in this order
	 * 
	 *  - data
	 *  - genes
	 *  - conditions
	 *  - regulators (optional)
	 *  - mvf files (optional)
	 *  
	 * @param links
	 */
	public ViewerGUI(String[] links){
		if (links != null && links.length>0){
			urls = new ArrayList<URL>();
			for (String link : links){
				try {
					URL url = new URL(link);
					this.urls.add(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void processURLS(Model model, GUIModel guiModel, List<URL> urls) throws IOException{
		DataMatrixParser dmp = new DataMatrixParser();
		dmp.parse(model, urls.get(0).openStream());
		model.setDataFile(urls.get(0).getFile());

		GeneTreeParser gtp   = new GeneTreeParser();
		gtp.parse(model, urls.get(1).openStream());
		model.setGeneFile(urls.get(1).getFile());

		ConditionTreeParser ctp = new ConditionTreeParser();
		ctp.parse(model, urls.get(2).openStream());
		model.setConditionFile(urls.get(2).getFile());

		if (urls.size()>=4){
			int remLinks = 3;
			if (!(urls.get(remLinks).getFile().endsWith(".mvf") ||
					checkMagicWord(urls.get(remLinks).openStream(), "MVF"))){
				RegulatorTreeParser rtp = new RegulatorTreeParser();
				rtp.parse(model, urls.get(3).openStream());
				model.setRegulatorFile(urls.get(3).getFile());
				remLinks++;
			}
			while (remLinks<urls.size()){
				MVFParser mvfp = new MVFParser();
				mvfp.parse(model, urls.get(remLinks).openStream());
				model.addAnnotationFile(urls.get(remLinks).getFile());
				remLinks++;
			}
			
		}

		guiModel.refresh();

	}
	
	/**
	 * Checks if the first line of the given InputStream contains the magic word, indicating 
	 * the file type without depending on a file extension.
	 * 
	 * @param stream
	 * @param magic
	 * @return
	 * @throws IOException
	 */
	private boolean checkMagicWord(InputStream stream, String magic) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		String line = in.readLine();
		String magicLine = line.substring(1);
		in.close();
		return magicLine.equalsIgnoreCase(magic);
	}
	
	/**
	 * Packs and launches the GUI
	 */
	public void startGUI() {
		//create and load the module network
		
		Model model = new Model();
		GUIModel guiModel = new GUIModel(model);
		
		if (urls != null){
			try {
				processURLS(model, guiModel, urls);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Problem loading data over http. Opening client whitout data.");
			}
		}
		
		guiModel.setDrawConditionAnnotationLegend(false);
		guiModel.setDrawConditionAnnotations(false);

		this.window = new JFrame("ModuleViewer - "+model.getVersion());
		guiModel.setTopContainer(window);
		
		ModuleLabel modLabel = new ModuleLabel(model, guiModel);
		
		JScrollPane scroll = new JScrollPane(modLabel);
		
		JPanel modulePanel = new JPanel();
		modulePanel.setLayout(new BorderLayout());
		modulePanel.add(scroll, BorderLayout.CENTER);
		
		JPanel toolBars = new JPanel();
		toolBars.setLayout(new BoxLayout(toolBars, BoxLayout.LINE_AXIS));	
		toolBars.add(new NavigationToolBar(model, guiModel));
		modulePanel.add(toolBars, BorderLayout.NORTH);

		modulePanel.add(new StatusBar(guiModel), BorderLayout.SOUTH);
		
		window.setJMenuBar(new MainMenu(model, guiModel));
		window.setContentPane(modulePanel);
		window.setMinimumSize(new Dimension(window.getSize().width, 300));
		
		window.pack();
		modLabel.initCanvas();
		window.pack();
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
		
	
	

}
