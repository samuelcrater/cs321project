package cs321project;

import java.util.ArrayList;
public class Semester {

	private ArrayList<Requirement> courses;
	private int numClasses;
	private final int MAXCLASSES = 5;
	
	public Semester() {
		courses = new ArrayList<>();
		numClasses = 0;
	}
	/*
	 * Adds a class, returns true if successful, false if the semester is full
	 */
	public boolean addCourse(Requirement r)
	{
		boolean ret=false;
		if(numClasses<MAXCLASSES)
		{
			courses.add(r);
			numClasses++;
			ret=true;
		}
		return ret;
	}
	/*
	 * Removes a requirement, returns requirement if successful, and null otherwise
	 */
	public Requirement removeCourse(Requirement r)
	{
		Requirement ret = null;
		for(Requirement chk : courses)
		{
			if(r.isEqual(chk))
			{
				ret = chk;
				boolean rm = courses.remove(chk);
				if(!rm)
					System.out.println("Whoops, something went wrong with removing the requirement");
				else
					numClasses--;
			}
		}
		return ret;
	}
	/*
	 * Remove a requirement by index, returns requirement if successful, null otherwise
	 */
	public Requirement removeCourse(int i)
	{
		Requirement ret = null;
		if(i>0&&i<numClasses)
		{
			ret = courses.remove(i);
			numClasses--;
		}
		return ret;
	}
	/*
	 * return the number of classes held by this semester
	 */
	public int getNumClasses()
	{
		return numClasses;
	}
	/*
	 * Return a copy of the list of classes in this semester 
	 */
	public ArrayList<Requirement> getCourses()
	{
		ArrayList<Requirement> ret = new ArrayList<>();
		for(Requirement r : courses)
			ret.add(r);
		return ret;
	}
}
