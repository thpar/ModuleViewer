package be.ugent.psb.moduleviewer;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

/**
 * Keeps track off non-fatal exceptions to be shown in the log dialog.
 * 
 * @author thpar
 *
 */
public class Logger extends Observable{
	private List<LogEntry> entries = new ArrayList<LogEntry>(); 
	
	public Logger(){
		
	}
	
	public void addEntry(Exception e, String message){
		entries.add(new LogEntry(e, message));
		setChanged();
		notifyObservers();
	}
	
	public void addEntry(String message){
		entries.add(new LogEntry(message));
		setChanged();
		notifyObservers();
	}
	
	public List<String> getLog(){
		List<String> log = new ArrayList<String>();
		for (LogEntry entry : entries){
			if (entry.e != null){
				log.add(entry.timestamp+": "+entry.e.getMessage()+"\n"+entry.message+"\n");				
			} else {
				log.add(entry.timestamp+": "+entry.message+"\n");				
			}
		}
		return log;
	}
	
	private class LogEntry{
		public Exception e;
		public String message;
		public Date timestamp;
		
		public LogEntry(Exception e, String message){
			this.e = e;
			this.message = message;
			this.timestamp = new Date();
		}
		
		public LogEntry(String message){
			this.e = null;
			this.message = message;
			this.timestamp = new Date();
		}
	}
}
