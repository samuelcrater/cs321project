package cs321project;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleController {

	private FileHandler fileHandler;
	private Degree degree;
	private Schedule schedule;
	
	protected ScheduleController(String inputFilePath, String outputFilePath)
	{
		//TODO FileHandler constructor
		//TODO Degree constructor
		fileHandler = new FileHandler(inputFilePath,outputFilePath);
	}
	
	protected void loadFile() throws IOException {
		degree = fileHandler.getDegree();
	}
	
	protected void saveSchedule() {
		fileHandler.saveSchedule();
	}
	
	protected ArrayList<Semester> generateSchedule() {
		ArrayList<Semester> ret = null;
		schedule.genSchedule(degree);
		ret = schedule.getScheudle();
		return ret;
	}
	
	protected void fufillRequirement(Requirement r) {
		r.setFulfilled(true);
	}
	
	//maybe use ints instead of objects
	protected int moveCourse(int courseIndex, int semesterStart, int semesterDest) {
		int ret;
		ret = schedule.moveClass(semesterStart, semesterDest, courseIndex);
		return ret;
	}
	
	//maybe use ints instead of objects
	protected int swapCourse(int c1, int s1, int c2, int s2) {
		int ret = 0;
		schedule.swapCourse(c1,s1,c2,s2);
		return ret;
	}
}
