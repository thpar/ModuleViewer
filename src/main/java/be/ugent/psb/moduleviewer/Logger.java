package be.ugent.psb.moduleviewer;

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
