package cs321project;


import java.io.IOException;
import java.util.ArrayList;

public class ScheduleController {

	private FileHandler fileHandler;
	private Degree degree;
	private Schedule schedule;
	private GUI gui;
	
	protected ScheduleController(GUI gui, FileHandler fileHandler)
	{
		//Degree constructor handled in loadfile() method
		this.gui = gui;
		this.fileHandler = fileHandler;
		//fileHandler = new FileHandler(inputFilePath,outputFilePath);
		schedule = new Schedule();
	}
	//Spencer's constructor,
	/*
	 * protected ScheduleController() { //TODO FileHandler constructor //TODO Degree
	 * constructor schedule = new Schedule(); }
	 */
	protected void loadFile() throws IOException {
		degree = fileHandler.getDegree();
		gui.updateDegree(degree.getAllRequirements());
	}
	
	protected void saveSchedule() {
		fileHandler.saveSchedule();
	}
	
	protected ArrayList<Semester> generateSchedule() {
		ArrayList<Semester> ret = null;
		schedule.genSchedule(degree);
		ret = schedule.getScheudle();
		gui.updateSchedule(schedule.getScheudle());
		return ret;
	}
	
	protected void fufillRequirement(Requirement r) {
		r.setFulfilled(true);
	}
	/*
	 * Moves classes between semesters
	 * returns:
	 * -1-moveClass is broken
	 * 0-successful move
	 * 1- start index <0
	 * 2- start index greater than number of semesters
	 * 3- stop index <0
	 * 4- stop index greater than number of semesters
	 * 5- class index <0
	 * 6- class index greater than number of classes in it's semester
	 * 7- destination semester full
	 * 8- tried to add class to semester it's in
	 * 9- trying to move a course back to/before a prerequisite
	 * 10-trying to move a prerequisite to/past a class that needs it
	 */
	protected int moveCourse(int courseIndex, int semesterStart, int semesterDest) {
		int ret=-1;
		ret = schedule.moveClass(semesterStart, semesterDest, courseIndex);
		gui.updateSchedule(schedule.getScheudle());
		return ret;
	}
	/*
	 * Swaps 2 courses between semesters
	 * returns:
	 * -1-swapClass is broken
	 * 0- successful swap
	 * 1- semester 1 is less than 0
	 * 2- semester 1 is greater than the number of semesters
	 * 3- semester 2 is less than 0
	 * 4- semester 2 is greater than the number of semesters
	 * 5- course 1 is less than 0
	 * 6- course 1 is greater than the number of classes in it's semester
	 * 7- course 2 is less than 0
	 * 8- course 2 is greater than the number of classes in it's semester
	 * 9- trying to move a course back to/before a prerequisite
	 * 10- trying to move a prerequisite to/past a class that needs it
	 */
	protected int swapCourse(int c1, int s1, int c2, int s2) {
		int ret = -1;
		schedule.swapClass(c1,s1,c2,s2);
		gui.updateSchedule(schedule.getScheudle());
		return ret;
	}
}
