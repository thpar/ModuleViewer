package be.ugent.psb.moduleviewer.parsers;

import org.xml.sax.helpers.DefaultHandler;

import be.ugent.psb.moduleviewer.actions.LoadModulesAction.ProgressListener;
import be.ugent.psb.moduleviewer.model.ModuleNetwork;


/**
 * Handles the XML that describes a {@link ConditionAnnotation} tree.
 * Invoked by the {@link ConditionTreeParser}
 * 
 * @author thpar
 *
 */
public class TreeXMLHandler extends DefaultHandler{
	/**
	 * The XML tags we're expecting.
	 * Enums are in all capitals, but will match case insensitive.
	 * 
	 * @author thpar
	 *
	 */
	enum XMLTag{
		MODULENETWORK,
		EXPERIMENTS, REGULATORS, REGULATOR, RANDOMREGULATOR,
		MODULE,
		GENES, GENE,
		REGULATIONTREES,
        ROOT, TREENODE,
		NA;
		
		public static XMLTag getValue(String value){
			try{
				return valueOf(value.toUpperCase());
			} catch (Exception e){
				return NA;
			}
		}
		
	}
	
	/**
	 * Did we go left or right? (or just started at the root)
	 * 
	 * @author thpar
	 *
	 */
	enum Dir{
		ROOT, LEFT, RIGHT;
	}

	
	
	private ModuleNetwork modnet;
	private ProgressListener progListener;
	
	/**
	 * Create an XML handler to fill the given modnet with data.
	 * 
	 * @param modnet the ModuleNetwork to fill
	 * @param progressListener 
	 */
	public TreeXMLHandler(ModuleNetwork modnet, ProgressListener progressListener) {
		this.modnet = modnet;
		this.progListener = progressListener;
	}

}
