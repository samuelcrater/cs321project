package cs321project;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {
	private HashMap<String, ArrayList<Requirement>> categories;
	private BufferedReader br;
	private String inputFile;
	private String outputFile;

	public FileHandler(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile; // will need this later for writing to a file
		File file = new File(inputFile);
		try {
			br = new BufferedReader(new FileReader(file)); // get our buffered reader ready
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, ArrayList<Requirement>> getCourseReqs() throws IOException {
		// e.g. when you hit "University Foundation" in the input file, do
		// hashMap.add("University Foundation", new ArrayList<Requirement>());
		// and for each requirement, do
		// hashMap.get("University Foundation").add(new Abstract/Concrete(...));
		categories = new HashMap<String, ArrayList<Requirement>>();
		String currentCategory = "University Core";
		String nextCategory = "Social Sciences and Humanities";

		// set up very first category
		String readLine = br.readLine();
		categories.put(readLine, new ArrayList<Requirement>());
		// didnt want a bunch of while loops, using function calls instead
		setCategories(currentCategory, nextCategory);
		currentCategory = "Social Sciences and Humanities";
		nextCategory = "Computer Science Department";
		setCategories(currentCategory, nextCategory);
		currentCategory = "Computer Science Department";
		nextCategory = "Major in Computer Science";
		setCategories(currentCategory, nextCategory);
		currentCategory = "Major in Computer Science";
		nextCategory = "Senior CS, Elective One of:";
		readAllCourses(currentCategory, nextCategory);//reading majority of concrete requirements
		currentCategory = "Senior CS, Elective One of:";
		nextCategory = "Senior CS, Additional";
		readAllCourses(currentCategory, nextCategory);//another three concrete requirements
		currentCategory = "Senior CS, Additional";
		nextCategory = "CS-Related Courses";
		setCategories(currentCategory, nextCategory);
		

		return categories;
	}

	private void setCategories(String currentCategory, String nextCategory) {
		String readLine;
		String parseLine[] = null;
		try {
			ArrayList<Requirement> getCurrentCategory = categories.get(currentCategory);
			// while loop for first set of reqs
			while ((readLine = br.readLine()) != null) {
				parseLine = readLine.split("_"); // important info is separated by _ in the txt file
				if (parseLine[0].equals(nextCategory)) {
					break;
				}
				// going to add com100 as a concrete requirement thats part of the Computer
				// Science Department category
				if (parseLine[0].equals("COMM")) {
					getCurrentCategory.add(new ConcreteRequirement(parseLine[0], Integer.parseInt(parseLine[1]),
							parseLine[2], Integer.parseInt(parseLine[3]), Boolean.parseBoolean(parseLine[4])));

				} else {// we have a regular abstract requirement
					getCurrentCategory.add(new AbstractRequirement(parseLine[0], Boolean.parseBoolean(parseLine[1])));
				}

			}
			// setting up next category
			categories.put(parseLine[0], new ArrayList<Requirement>());
			// we have a category that just has credits associated with it and nothing else,
			// ie Social Sciences and Humanities. Going to
			// add this as both its own category and an abstract requirement
			if (parseLine.length >= 3) {
				categories.get(parseLine[0]).add(new AbstractRequirement(parseLine[0],
						Boolean.parseBoolean(parseLine[1]), Integer.parseInt(parseLine[2])));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// repurposed!
	private void readAllCourses(String currentCategory, String nextCategory) {
		String currentCourse; // we're using this to read through our txt file full of courses line by line
		try {
			ArrayList<Requirement> majorInCompSci = categories.get(currentCategory);
			String courseParse[] = null;
			// this while loop specifically reads cs classes in our DegreeRequirements txt
			// file.
			while ((currentCourse = br.readLine()) != null) {
				courseParse = currentCourse.split("_"); // important info is separated by _ in the txt file
				
				if(courseParse[0].equals(nextCategory)) {
					break;
				}
				// courseParse[0] is the subject. ex "MATH" "CS" "ECE"
				// courseParse[1] is the number associated with the subject, ie, the "310" in
				// "CS 310"
				// courseParse[2] is the full name of the course
				// courseParse[3] is the number of credits the course is worth
				// courseParse[4] is if the course has been fulfilled
				ConcreteRequirement course = new ConcreteRequirement(courseParse[0], Integer.parseInt(courseParse[1]),
						courseParse[2], Integer.parseInt(courseParse[3]),Boolean.parseBoolean(courseParse[4]));
				majorInCompSci.add(course);
				// looking for prereqs
				// courseParse[5] is the number of prereqs for a course
				// courseParse[6] will have the subject, courseParse[7] will have the number
				// courseParse[8] will have the subject, courseParse[9] will have the number,
				// etc.
				if (courseParse.length >= 6) {
					// need this to track through the courseParse and get our prereqs
					int courseParseCount = 6;
					int totalPreReqCount = Integer.parseInt(courseParse[5]); // a number i added in to tell me how many
																				// prereqs there are for this course
					int preReqCount = 0; // will track how many prereqs have been added

					// need to search through our current courses array for the prereqs
					for (int i = 0; i < majorInCompSci.size(); i++) {
						Requirement temp = majorInCompSci.get(i);
						// so at this point in courseParse we've gotten past the full name of the
						// course, the subject, the number of credits,
						// and the number associated with the current course. Now, we're on the prereqs,
						// which are separated out
						// by subject "CS" and number "310". Hence the courseParseCount and
						// courseParseCount+1 bits.
						if (temp.getSubject().equals(courseParse[courseParseCount])
								&& temp.getNumber() == Integer.parseInt(courseParse[courseParseCount + 1])) {
							course.addPrerequisiteFor(temp); // we've found a prereq, adding it to the current courses
																// required prereqs
							courseParseCount = courseParseCount + 2; // do not need to compare subject to the number.
																		// this will skip to the next prereq, if there
																		// is one
							preReqCount++;
							if (preReqCount >= totalPreReqCount) {
								break;// we have all of our prereqs, exit.
							}
						}
					}
				}
			}
			// setting up next category
			categories.put(courseParse[0], new ArrayList<Requirement>());
			// we have a category that just has credits associated with it and nothing else,
			// ie Social Sciences and Humanities. Going to
			// add this as both its own category and an abstract requirement
			if (courseParse.length >= 3) {
				categories.get(courseParse[0]).add(new AbstractRequirement(courseParse[0],
						Boolean.parseBoolean(courseParse[1]), Integer.parseInt(courseParse[2])));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// this file path will need to change on your computer
		FileHandler test = new FileHandler(
				"D:\\Users\\Isabella\\cs321project\\src\\cs321project\\DegreeRequirements2.txt", "somecrap");
		try {
			test.getCourseReqs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
