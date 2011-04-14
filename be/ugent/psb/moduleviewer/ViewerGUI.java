package be.ugent.psb.moduleviewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


public class ViewerGUI {

//	private static final String SLASH = System.getProperty("file.separator");
	
	private JFrame window;
//	private GraphicalModuleModel gModel;
	
	
	public ViewerGUI(String xmlInput, String outputDir, String nameMap, 
			String topRegs, String conditionMap,
			String geneAssoc,
			String geneOnto,
			String bingoOut,
			String topCondOut) {
	}
	
	public ViewerGUI(){
		
	}

	
	
	public void startGUI() {
		//create and load the module network
		ModuleNetwork modnet = new ModuleNetwork();
		
		/*
		ModuleNetworkParser.loadWithSAXParser(modnet, new File(xmlInput));
		GeneNameMappingParser.load(modnet, geneMapFile);
		modnet.calculateModuleMeans();
		
		//load the top regulators from a file
		TopRegulatorsParser.load(modnet, topRegs);
		
		//load the condition classes
		//use the condition names from this file to reset the condition names in the modnet
		try {
			ConditionClassification condMap = ConditionMapParser.load(modnet, conditionMapFile);
			condMap.addFixedColorMapping("CTRL", Color.WHITE);
			condMap.addFixedColorMapping("ctrl", Color.WHITE);			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		

		//load and calculate BiNGO stuff
//		BiNGO b = new BiNGO(modnet, geneAssoc, geneOnto, "0.05", false, false, null, "biological_process"); 
//		//for now, we just use an empty allNodes set
//		HashSet<String> allNodes = new HashSet<String>();
//		b.GOmodules(allNodes, this.bingoOut);

		
		/*
		//get the 10% most up and down regulated conditions for each module and write them to a file
		try {
			PrintWriter condOut = new PrintWriter(new BufferedWriter(new FileWriter(topCondOut)));
			List<String> classes = modnet.getConditionClassification().getClasses();
			condOut.print("Condition classes: ");
			for (String clas : classes){
				condOut.print("\t");
				condOut.print(clas);
			}
			condOut.println();
			for (Module mod : modnet.moduleSet){
				List<Experiment> expUpList = mod.getTopUpregulatedConditions(10, 1, 10);
				List<Experiment> expDownList = mod.getTopDownregulatedConditions(10, 1, 10);
				if (expUpList.size()!=0 || expDownList.size()!=0){
					condOut.println("Module "+mod.number);
					
					if (expUpList.size()!=0){
						condOut.println("Upregulated:");
						for (Experiment exp : expUpList){
							condOut.print(exp.name);
							for (String clas : classes){
								condOut.print("\t");
								String prop = modnet.getConditionClassification().getProperty(exp.name, clas);
								condOut.print(prop);
							}
							condOut.println();
						}
					}
					if (expDownList.size()!=0){
						condOut.println("Downregulated:");
						for (Experiment exp : expDownList){
							condOut.print(exp.name);
							for (String clas : classes){
								condOut.print("\t");
								String prop = modnet.getConditionClassification().getProperty(exp.name, clas);
								condOut.print(prop);
							}
							condOut.println();
						}
						
					}	
					condOut.println();
				}
			}
			condOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		//draw all modules
//		for (Module mod : modnet.moduleSet) {
//			int c=0;
//			TreeNode n = mod.hierarchicalTrees.get(0);
//			mod.hierarchicalTree = n;
//			for (TreeNode node : mod.hierarchicalTree.getInternalNodes()){
//				Collections.sort(node.testSplits);
//				node.regulationSplit = node.testSplits.get(0);
//			}
//			if (mod.hierarchicalTree.nodeStatus.equals("internal")) {
//				String file = outputDir+SLASH+String.format("module_%03d-%d", mod.number, c);
//				String old_file = oldOutputDir+SLASH+String.format("module_%03d-%d", mod.number, c);
//				System.out.println(file);
//				mod.drawMyWay(file);
//			}
//			c++;
//		}
		
		
		//do the drawing...
		
		GUIModel guiModel = new GUIModel();
		guiModel.setDrawConditionAnnotationLegend(true);
		guiModel.setDrawConditionAnnotations(true);

		this.window = new JFrame("ModuleViewer");
		guiModel.setTopContainer(window);
		
		ModuleLabel modLabel = new ModuleLabel(modnet, guiModel);
		
		JScrollPane scroll = new JScrollPane(modLabel);
		
		JPanel modulePanel = new JPanel();
		modulePanel.setLayout(new BorderLayout());
		modulePanel.add(scroll, BorderLayout.CENTER);
		
		JPanel toolBars = new JPanel();
		toolBars.setLayout(new BoxLayout(toolBars, BoxLayout.LINE_AXIS));	
		toolBars.add(new NavigationToolBar(modnet, guiModel));
		modulePanel.add(toolBars, BorderLayout.NORTH);

		modulePanel.add(new StatusBar(guiModel), BorderLayout.SOUTH);
		
		window.setJMenuBar(new MainMenu(modnet, guiModel));
		window.setContentPane(modulePanel);
		window.setMinimumSize(new Dimension(window.getSize().width, 300));
		
		window.pack();
		modLabel.initCanvas();
		window.pack();
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
		
	
	

}
