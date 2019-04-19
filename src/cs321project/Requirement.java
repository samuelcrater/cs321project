package cs321project;

import java.util.ArrayList;

public abstract class Requirement {

	private boolean fulfilled;
	private String label;//full course name, or name of abstract requirement ie. "Literature"
	private String subject;
	private int number;
	private int credits;
	private ArrayList<Requirement> prerequisitesNeeded;
	
	public Requirement(String label) {
		this(label, false);
	}
	public Requirement(String label, boolean fulfilled) {
		this.label = label;
		this.fulfilled = fulfilled;
		this.prerequisitesNeeded = new ArrayList<>();
	}
	//used for abstract requirements
	public Requirement(String label, boolean fulfilled, int credits) {
		this.label = label;
		this.fulfilled = fulfilled;
		this.credits = credits;
	}
	//used for concrete requirements
	public Requirement(String label, String subject, int number, boolean fulfilled, int credits) {
		this.label = label;
		this.subject = subject;
		this.number = number;
		this.fulfilled = fulfilled;
		this.credits = credits;
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
	//need this for writing ConcreteRequirements to a file in the FileHandler, 
	//since getLabel is being overwritten in ConcreteRequirements
	public String getLabelForFileHandler() {
		return this.label;
	}
	public String getSubject() {
		return this.subject;
	}
	public int getNumber() {
		return this.number;
	}
	public int getCredits() {
		return this.credits;
	}
	public boolean isEqual(Requirement r)
	{
		boolean ret=false;

		if(r.getSubject()!=null&&r.getSubject().equals(subject))
		{
			if(r.getNumber()==this.getNumber())
				ret = true;
		}
		else
			if(r.getSubject()==null && subject==null)
				ret=true;
		return ret;
	}
}
