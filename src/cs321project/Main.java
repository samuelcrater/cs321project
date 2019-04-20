package cs321project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static final String INPUT_FILE = "DegreeRequirements2.txt";
	public static final String OUTPUT_FILE = "output.txt";
	
	public static void main(String[] args) {
		System.out.println("Working Directory = " +
	              System.getProperty("user.dir"));
		//setup
		GUI gui = new GUI();
		FileHandler fh = new FileHandler(INPUT_FILE, OUTPUT_FILE);
		Degree degree = new Degree();
		Schedule schedule = new Schedule();
		ScheduleController sc = new ScheduleController(gui, fh);
		gui.setScheduleController(sc);
		try {
			sc.loadFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.display();
		
		/*
		HashMap<String, ArrayList<Requirement>> tester = new HashMap<String, ArrayList<Requirement>>();
		ArrayList<Requirement> temp = new ArrayList<Requirement>();
		temp.add(new AbstractRequirement("Oral Communication", false, 0));
		temp.add(new AbstractRequirement("Quantitative Reasoning", false, 0));
		tester.put("University Foundation", temp);
		temp = new ArrayList<Requirement>();
		temp.add(new AbstractRequirement("Literature", true, 0));
		temp.add(new AbstractRequirement("Arts", false, 0));
		tester.put("University Core", temp);
		gui.updateDegree(tester);
		 */
		
	}
}
