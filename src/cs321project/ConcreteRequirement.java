package cs321project;

public class ConcreteRequirement extends Requirement {

	private String subject;
	private int number;
	private int credits;
	private boolean fulfilled;
	
	public ConcreteRequirement(String subject, int number, String label, int credits, boolean fulfilled) {
		super(label,subject,number,fulfilled);
		this.subject = subject;
		this.number = number;
		this.credits = credits;
		this.fulfilled = fulfilled;
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
	public boolean isFulfilled() {
		return this.fulfilled;
	}
	@Override
	public String getLabel() {
		return this.subject + Integer.toString(this.number);
	}
}
