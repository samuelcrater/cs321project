package cs321project;

import java.util.ArrayList;

public abstract class Requirement {

	private boolean fulfilled;
	private String label;
	private ArrayList<Requirement> prerequisiteFor;
	
	public Requirement(String label) {
		this(label, false);
	}
	
	public Requirement(String label, boolean fulfilled) {
		this.label = label;
		this.fulfilled = fulfilled;
		this.prerequisiteFor = new ArrayList<>();
	}
	
	public boolean isFulfilled() {
		return this.fulfilled;
	}
	
	public void setFulfilled(boolean fulfilled) {
		this.fulfilled = fulfilled;
	}
	
	public void addPrerequisiteFor(Requirement r) {
		this.prerequisiteFor.add(r);
	}
	
	public ArrayList<Requirement> getPrerequisiteFor() {
		return this.prerequisiteFor;
	}
	
	public String getLabel() {
		return this.label;
	}
}
