package be.ugent.psb.moduleviewer.actions;

/*
 * #%L
 * ModuleViewer
 * %%
 * Copyright (C) 2015 VIB/PSB/UGent - Thomas Van Parys
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */

import java.awt.event.ActionEvent;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.SwingWorker;

import be.ugent.psb.moduleviewer.model.GUIModel;
import be.ugent.psb.moduleviewer.model.Model;
import be.ugent.psb.moduleviewer.model.Module;

/**
 * Runs over all modules and sorts conditions within their tree structure, according to mean expression in the module (low to high)
 * 
 * @author Thomas Van Parys
 *
 */
public class SortConditionsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private GUIModel guiModel;
	private Model model;

	
	public SortConditionsAction(Model model, GUIModel guiModel){
		super("Sort conditions");
		this.guiModel = guiModel;
		this.model = model;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		LoadTask worker = new LoadTask();
		worker.addPropertyChangeListener(guiModel);
		worker.execute();
			
	}
	
	public class LoadTask extends SwingWorker<Void, Void>{
		
		
		@Override
		protected Void doInBackground() throws Exception {			
			guiModel.showProgressBar(true);
			guiModel.setStateString("Sorting conditions");
			
			setProgress(0);
			Collection<Module> modules = model.getModnet().getModules();
			double prog = 0;
			double step = 100d/(double)modules.size();
			
			for (Module module : modules){
				module.sortConditionsByMeanExpression();
				
				prog+=step;
				setProgress((int)Math.round(prog));
			}
			
			setProgress(100);
			return null;
		}
		
		@Override
		protected void done() {
			guiModel.clearStateString();
			guiModel.showProgressBar(false);
			guiModel.refresh();
		}
	}
	

}
