package cs321project;

public class Main {

	public static final String FILE_INPUT_DIRECTORY = "";
	public static final String FILE_OUTPUT_DIRECTORY = "";
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.display();
		FileHandler fh = new FileHandler(FILE_INPUT_DIRECTORY, FILE_OUTPUT_DIRECTORY);
		Degree degree = new Degree();
		Schedule schedule = new Schedule();
		ScheduleController sc = new ScheduleController(gui, fh);
	}
}
