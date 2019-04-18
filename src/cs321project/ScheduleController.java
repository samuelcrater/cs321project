package cs321project;

import java.io.IOException;

public class ScheduleController {

	private FileHandler fileHandler;
	private Degree degree;
	private Schedule schedule;
	
	public ScheduleController(String inputFilePath, String outputFilePath) {
		fileHandler = new FileHandler(inputFilePath,outputFilePath);
	}
	
	protected void loadFile() throws IOException {
		degree = fileHandler.getDegree();
	}
	
	protected void saveSchedule() {
		fileHandler.saveSchedule();
	}
	
	protected void generateSchedule() {
		
	}
	
	protected void fufillRequirement(Requirement r) {
		r.setFulfilled(true);
	}
	
	//maybe use ints instead of objects
	protected void moveCourse(Course c, Semester s) {
		
	}
	
	//maybe use ints instead of objects
	protected void swapCourse(Course c1, Semester s1, Course c2, Semester s2) {
		
	}
}
