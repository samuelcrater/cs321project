package cs321project;

public class ConcreteRequirement extends Requirement {

	private String subject;
	private int number;
	private int credits;
	
	public ConcreteRequirement(String subject, int number, String label, int credits, boolean fulfilled) {
		super(label,subject,number,fulfilled,credits);
		this.subject = subject;
		this.number = number;
		this.credits = credits;
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
		return super.isFulfilled();
	}
	@Override
	public String getLabel() {
		return this.subject + Integer.toString(this.number);
	}
}
