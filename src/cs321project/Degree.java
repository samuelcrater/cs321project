package cs321project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Degree {

	private ArrayList<Requirement> requirements;
	private HashMap<String, ArrayList<Requirement>> allRequirements;
	FileHandler handler;
	
	public Degree() {
		this.requirements = new ArrayList<>();
	}
	public Degree(String inputFile, String outputFile){
		handler = new FileHandler(inputFile, outputFile);
		//not going to do a hard copy.  be careful not to accidentally lose the object or dump the contents.
		try {
			allRequirements = handler.getCourseReqs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList<Requirement> getUniversityCore() {
		return allRequirements.get("University Core");
	}
	public ArrayList<Requirement> getSocialSciencesAndHumanities() {
		return allRequirements.get("Social Sciences and Humanities");
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
