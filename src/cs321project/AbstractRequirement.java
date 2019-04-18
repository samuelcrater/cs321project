package cs321project;

public class AbstractRequirement extends Requirement {
	String name;
	int creditRequirement = 0; //for when there is a specific number of credits needed to complete this requirement
	public AbstractRequirement(String name, boolean fulfilled) {
		super(name);
		this.name = name;
	}
	public AbstractRequirement(String name, boolean fulfilled, int creditRequirement) {
		super(name,fulfilled,creditRequirement);
		this.name = name;
		this.creditRequirement = creditRequirement;
	}
}
