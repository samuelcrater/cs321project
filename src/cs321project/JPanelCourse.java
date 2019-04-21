package cs321project;

import javax.swing.JPanel;

public class JPanelCourse extends JPanel{
	private int courseNumber;
	private int semesterNumber;
	
	public JPanelCourse(int courseNumber, int semesterNumber) {
		this.courseNumber = courseNumber;
		this.semesterNumber = semesterNumber;
	}
	
	public int getCourseNumber() {
		return this.courseNumber;
	}
	
	public int getSemesterNumber() {
		return this.semesterNumber;
	}
}
