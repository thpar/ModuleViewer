package be.ugent.psb.moduleviewer.parsers;

import java.io.File;
import java.io.IOException;

import be.ugent.psb.moduleviewer.actions.ProgressListener;
import be.ugent.psb.moduleviewer.model.Model;

public abstract class Parser {

	abstract public void parse(Model model, File inputFile) throws IOException;

	public Parser(){
		this.progressListener = new ProgressListener(){
			@Override
			public void setMyProgress(int percent) {
				//a progress listener that doesn't do anything
			}
		};
	}
	
	protected ProgressListener progressListener;
	
	/**
	 * this progress listener should be implemented inside a {@link SwingWorker} 
	 * so it can refer to its setProgress method.
	 *  
	 * @param progListener
	 */
	public Parser(ProgressListener progListener){
		this.progressListener = progListener;
	}

	
//	public void setProgressListener(ProgressListener progListener) {
//		progressListener = progListener;		
//	}
	
	
}
