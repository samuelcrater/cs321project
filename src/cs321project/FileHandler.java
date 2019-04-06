package cs321project;
import java.io.*;
import java.util.ArrayList;
public class FileHandler {
	Course course;
	BufferedReader br;
	String inputFile;
	String outputFile;
    public FileHandler(String inputFile, String outputFile) {
    	this.inputFile = inputFile;
    	this.outputFile = outputFile; //will need this later for writing to a file
    	File file = new File(inputFile);
    	try {
			br = new BufferedReader(new FileReader(file)); //get our buffered reader ready
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public ArrayList<Course> readAllCourses() {
    	String currentCourse; //we're using this to read through our txt file full of courses line by line
    	ArrayList<Course> courses = new ArrayList<Course>();
    	try {
    		//this while loop specifically reads cs classes in our DegreeRequirements txt file.
			while((currentCourse = br.readLine()) != null) {
				String courseParse[] = currentCourse.split("_"); //important info is separated by _ in the txt file
				//courseParse[2] is the full name of the course
				//courseParse[0] is the subject. ex "MATH" "CS" "ECE"
				//courseParse[1] is the number associated with the subject, ie, the "310" in "CS 310"
				course = new Course(courseParse[2],courseParse[0],Integer.parseInt(courseParse[1]));
				//looking for prereqs, coreqs are temporarily being ignored.
				//courseParse[4] will have the subject, courseParse[5] will have the number
				//courseParse[6] will have the subject, courseParse[7] will have the number, etc.
				if(courseParse.length >= 4) {
					//need this to track through the courseParse and get our prereqs
					int courseParseCount = 4;
					int totalPreReqCount = Integer.parseInt(courseParse[3]); //a number i added in to tell me how many prereqs there are
					int preReqCount = 0; //will track how many prereqs have been added
					//need to search through our current courses array for the prereqs
					for(int i = 0; i < courses.size(); i++) {
						Course temp = courses.get(i);
						//so at this point in courseParse we've gotten past the full name of the course, the subject,
						//and the number associated with the current course.  Now, we're on the prereqs, which are separated out
						//by subject "CS" and number "310".  Hence the courseParseCount and courseParseCount+1 bits.
						if(temp.getSubject().equals(courseParse[courseParseCount])
								&& temp.getNumber() == Integer.parseInt(courseParse[courseParseCount+1])) {
							course.addPrerequisite(temp); //we've found a prereq, adding it to the current courses required prereqs
							courseParseCount = courseParseCount + 2; //do not need to compare subject to the number.  this will skip to the next prereq, if there is one
							preReqCount++;
							if(preReqCount >= totalPreReqCount) {
								break;//we have all of our prereqs, exit.
							}
						}
						
						if(courseParseCount >= courseParse.length) {
							courseParseCount = 4;//we've hit the end of our prereqs, need to reset
						}
					}
				}
				courses.add(course);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return courses;
    }
    public static void main(String[] args) {
    	//this file path will need to change on your computer
		FileHandler test = new FileHandler("D:\\Users\\Isabella\\cs321project\\src\\cs321project\\DegreeRequirements.txt", "somecrap");
		test.readAllCourses();
	}
}
