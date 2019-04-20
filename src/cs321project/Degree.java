package cs321project;

import java.util.ArrayList;
import java.util.HashMap;

public class Degree {

	private ArrayList<Requirement> requirements;
	private HashMap<String, ArrayList<Requirement>> allRequirements;
	FileHandler handler;
	
	public Degree() {
		this.requirements = new ArrayList<>();
	}
	//be careful with this!
	public void setHashTable(HashMap<String, ArrayList<Requirement>> table) {
		//note that i'm still not doing a hard copy.
		allRequirements = table;
	}
	public HashMap<String, ArrayList<Requirement>> getAllRequirements() {
		return allRequirements;
	}
	public ArrayList<Requirement> getUniversityCore() {
		return allRequirements.get("University Core");
	}
	public ArrayList<Requirement> getComputerScienceDepartment() {
		return allRequirements.get("Computer Science Department");
	}
	public ArrayList<Requirement> getMajorInComputerScience() {
		return allRequirements.get("Major in Computer Science");
	}
	public ArrayList<Requirement> getSeniorCSOneElective() {
		return allRequirements.get("Senior CS, Elective One of:");
	}
	public ArrayList<Requirement> getAdditionalSeniorCS() {
		return allRequirements.get("Senior CS, Additional");
	}
	public ArrayList<Requirement> getCSRelatedCourses() {
		return allRequirements.get("CS-Related Courses");
	}
	public void fulfillRequirement(int index) {
		this.requirements.get(index).setFulfilled(true);
	}
}
