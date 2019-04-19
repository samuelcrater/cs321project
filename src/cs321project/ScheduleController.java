package cs321project;

import java.util.ArrayList;

public class ScheduleController {

	private FileHandler fileHandler;
	private Degree degree;
	private Schedule schedule;
	
	protected ScheduleController()
	{
		//TODO FileHandler constructor
		//TODO Degree constructor
	}
	protected void loadFile() {
		
	}
	
	protected void saveSchedule() {
		
	}
	
	protected ArrayList<Semester> generateSchedule() {
		ArrayList<Semester> ret = null;
		schedule.genSchedule(degree);
		ret = schedule.getScheudle();
		return ret;
	}
	
	protected void fufillRequirement(Requirement r) {
		
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
