package cs321project;
import java.util.ArrayList;

public class Course {
	private String coursename; //coursename, full one at that
	private String subject; //ie, "CS" "MATH" "ECE" etc
	private int number; //the "310" in "CS 310".  Could remove this field and simply combo it with the subject.
	private boolean check = false; //have you passed this course?
	private boolean available = true; //is this course available for you to take?
	
	private ArrayList<Course> prereq = new ArrayList<>();
	private ArrayList<Course> coreq = new ArrayList<>();
	
	/**
	 * Course Constructor to include coursename and whether or not it is passed. 
	 * @param name
	 */
	public Course(String name)
	{
		this.setCoursename(name);
	}
	
	public Course(String name, boolean c)
	{
		this.setCoursename(name);
		this.check= c;
	}
	
	public Course(String name, String subject, int number) {
		this.coursename = name;
		this.subject = subject;
		this.number = number;
	}
	
	
	/**
	 * The Usual Getters and Setters for Each Value
	 */
	
	
	/**
	 * The Coursename variable in case the name of the course is needed for any future checks
	 * @return
	 */
	public String getCoursename() 
	{
		return coursename;
	}
	
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	
	public String getSubject() {
		return subject;
	}
	public int getNumber() {
		return number;
	}
	
	/**
	 * Checking and setting for whether you have finished your classes
	 * @return
	 */
	public boolean getCheck()
	{
		return check;
	}
	
	public void setCheck(boolean c)
	{
	this.check = c;
	}
	
	
	/**
	 * asking the important question: "Can you take this course?"
	 * @return
	 */
	public boolean getAvailable() {
		return checkValid();
	}

	
	/**
	 * adding prerequisites to this course.
	 * @param course
	 */
	public void addPrerequisite(Course course)
	{
		prereq.add(course);
	}
	
	/**
	 * Checks if you are able to take the course
	 * @return
	 */
	private boolean checkValid()
	{
		
		for(int i = 0; i < prereq.size();i++) //scrolls through prereqs.return false if req not met.
		{
			if(prereq.get(i).getCheck() == false)
			{
				return false;
			}
			
		}
		return true;
	}
}
