package be.ugent.psb.moduleviewer;

/**
 * Main class and entry point to launch the application.
 * Creates a new ViewerGUI object, passes the attributes and starts the GUI.
 * 
 * @author thpar
 *
 */
public class ModuleViewer {

	public static void main(String[] args) {

		ViewerGUI gui = new ViewerGUI(args);
		gui.startGUI();


	}

}

