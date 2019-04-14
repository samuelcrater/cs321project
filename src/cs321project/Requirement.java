package cs321project;

import java.util.ArrayList;

public abstract class Requirement {

	private boolean fulfilled;
	private String label;
	private String subject;
	private int number;
	private ArrayList<Requirement> prerequisitesNeeded;
	
	public Requirement(String label) {
		this(label, false);
	}
	public Requirement(String label, boolean fulfilled) {
		this.label = label;
		this.fulfilled = fulfilled;
		this.prerequisitesNeeded = new ArrayList<>();
	}
	public Requirement(String label, String subject, int number, boolean fulfilled) {
		this.label = label;
		this.subject = subject;
		this.number = number;
		this.fulfilled = fulfilled;
	}
	public boolean isFulfilled() {
		return this.fulfilled;
	}
	
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	
	public void addPrerequisiteFor(Requirement r) {
		if(prerequisitesNeeded == null) {
			this.prerequisitesNeeded = new ArrayList<>();
		}
		this.prerequisitesNeeded.add(r);
	}
	
	public ArrayList<Requirement> getPrerequisiteFor() {
		return this.prerequisitesNeeded;
	}
	public String getLabel() {
		return this.label;
	}
	public String getSubject() {
		return this.subject;
	}
	public int getNumber() {
		return this.number;
	}
}
