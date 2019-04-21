package cs321project;

import javax.swing.JPanel;

public class JPanelSemester extends JPanel{
	private int semesterNumber;
	
	public JPanelSemester(int semesterNumber) {
		this.semesterNumber = semesterNumber;
	}
	
	public int getSemesterNumber() {
		return this.semesterNumber;
	}
}
