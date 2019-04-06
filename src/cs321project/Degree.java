package cs321project;

import java.util.ArrayList;

public class Degree {

	private ArrayList<Requirement> requirements;
	
	public Degree() {
		this.requirements = new ArrayList<>();
	}
	
	public void fulfillRequirement(int index) {
		this.requirements.get(index).setFulfilled(true);
	}
}
