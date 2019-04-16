package cs321project;

import java.util.ArrayList;

public class Schedule {
	private ArrayList<Semester> semesters;
	public Schedule()
	{
		semesters = new ArrayList<>();
	}
	/*
	 * Generate a schedule based on what is needed in the degree
	 */
	public void genSchedule(Degree d)
	{
		/*Methodology:
		 * 	step through the degree's requirements, adding classes to semesters and removing from
		 *  the arraylist until none are left. remove classes if they're already taken, or
		 *  if they're successfully added to a semester. Throw to the end of the list otherwise.
		 *  Add to a semester only if no classes exist in the arraylist that are unfufilled and
		 *  prereqs for the class. If the semester fails to add a class because it's full, add a new
		 *  semester
		 */
		ArrayList<Requirement> reqs = new ArrayList<>();
		for(Requirement k:d.getAdditionalSeniorCS())
			reqs.add(k);
		for(Requirement k:d.getComputerScienceDepartment())
			reqs.add(k);
		for(Requirement k:d.getCSRelatedCourses())
			reqs.add(k);
		for(Requirement k:d.getMajorInComputerScience())
			reqs.add(k);
		for(Requirement k:d.getSeniorCSOneElective())
			reqs.add(k);
		for(Requirement k:d.getUniversityCore())
			reqs.add(k);
		int semesterCntr=0;
		while(!reqs.isEmpty())
		{
			if(semesters.isEmpty())
				semesters.add(new Semester());
			//get the next class
			Requirement reqNext = reqs.get(0);
			boolean canRemove=false;
			//check if this class needs to be taken
			if(!reqNext.isFulfilled())
			{
				boolean canAdd=true;
				//check if there is a class that is a requirement for this class in requirements
				if(reqNext.getPrerequisiteFor()!=null)
				{
					for(Requirement chk:reqNext.getPrerequisiteFor())
					{
							for(Requirement compare:reqs)
							{
								if(compare.isEqual(chk))
								{
									canAdd=false;
								}
							}
					}
				}
				//if no requirements are found in the "to be added" arraylist, try to add
				if(canAdd)
				{
					boolean added = false;
					//error checking, this shouldn't happen. if it does I messed up
					if(semesters.size()<=semesterCntr)
						throw new IndexOutOfBoundsException("There aren't enough semesters to add to, I messed up the logic here.");
					else
					{
						//check if prereqs exist in the semester
						boolean addable = true;
						for(Requirement semesterChk:semesters.get(semesterCntr).getCourses())
						{
							if(reqNext.getPrerequisiteFor()!=null)
							{
								for(Requirement semesterCompare:reqNext.getPrerequisiteFor())
								{
									if(semesterChk.isEqual(semesterCompare))
										addable=false;
								}
							}
						}
						if(addable)
						{
							//try to add the course to the current semester
							added = semesters.get(semesterCntr).addCourse(reqNext);
							if(added)
								canRemove=true;
							else
							{
								//if the semester is full and returns false, make a new semester and add to that one
								semesters.add(new Semester());
								semesterCntr++;
								added = semesters.get(semesterCntr).addCourse(reqNext);
								if(!added)
									throw new IndexOutOfBoundsException("wow the logic here is wrong");
								canRemove=true;
							}
						}
						else
						{
							semesters.add(new Semester());
							semesterCntr++;
							added = semesters.get(semesterCntr).addCourse(reqNext);
							if(!added)
								throw new IndexOutOfBoundsException("wow the logic here is wrong x2");
							canRemove=true;
						}
					}
				}
			}
			else
			{
				canRemove=true;
			}
			if(canRemove)
			{
				reqs.remove(0);
			}
			else
			{
				Requirement putAtEnd = reqs.remove(0);
				reqs.add(putAtEnd);
			}
		}
	}
	/*
	 * generate a schedule based off of an arraylist of classes (for testing)
	 */
	public void genSchedule(ArrayList<Requirement>c)
	{
		ArrayList<Requirement> reqs = new ArrayList<>();
		for(Requirement r: c)
			reqs.add(r);
		int semesterCntr=0;
		while(!reqs.isEmpty())
		{
			if(semesters.isEmpty())
				semesters.add(new Semester());
			//get the next class
			Requirement reqNext = reqs.get(0);
			boolean canRemove=false;
			//check if this class needs to be taken
			if(!reqNext.isFulfilled())
			{
				boolean canAdd=true;
				//check if there is a class that is a requirement for this class in requirements
				if(reqNext.getPrerequisiteFor()!=null)
				{
					for(Requirement chk:reqNext.getPrerequisiteFor())
					{
							for(Requirement compare:reqs)
							{
								if(compare.isEqual(chk))
								{
									canAdd=false;
								}
							}
					}
				}
				//if no requirements are found in the "to be added" arraylist, try to add
				if(canAdd)
				{
					boolean added = false;
					//error checking, this shouldn't happen. if it does I messed up
					if(semesters.size()<=semesterCntr)
						throw new IndexOutOfBoundsException("There aren't enough semesters to add to, I messed up the logic here.");
					else
					{
						//check if prereqs exist in the semester
						boolean addable = true;
						for(Requirement semesterChk:semesters.get(semesterCntr).getCourses())
						{
							if(reqNext.getPrerequisiteFor()!=null)
							{
								for(Requirement semesterCompare:reqNext.getPrerequisiteFor())
								{
									if(semesterChk.isEqual(semesterCompare))
										addable=false;
								}
							}
						}
						if(addable)
						{
							//try to add the course to the current semester
							added = semesters.get(semesterCntr).addCourse(reqNext);
							if(added)
								canRemove=true;
							else
							{
								//if the semester is full and returns false, make a new semester and add to that one
								semesters.add(new Semester());
								semesterCntr++;
								added = semesters.get(semesterCntr).addCourse(reqNext);
								if(!added)
									throw new IndexOutOfBoundsException("wow the logic here is wrong");
								canRemove=true;
							}
						}
						else
						{
							semesters.add(new Semester());
							semesterCntr++;
							added = semesters.get(semesterCntr).addCourse(reqNext);
							if(!added)
								throw new IndexOutOfBoundsException("wow the logic here is wrong x2");
							canRemove=true;
						}
					}
				}
			}
			else
			{
				canRemove=true;
			}
			if(canRemove)
			{
				reqs.remove(0);
			}
			else
			{
				Requirement putAtEnd = reqs.remove(0);
				reqs.add(putAtEnd);
			}
		}
	}
		
	/*
	 * returns a copy of generated schedules
	 */
	public ArrayList<Semester>getScheudle()
	{
		
		ArrayList<Semester>ret = new ArrayList<>();
		for(Semester s : semesters)
		{
			Semester ad = new Semester();
			for(Requirement r:s.getCourses())
				ad.addCourse(r);
			ret.add(ad);
		}
		return ret;
	}
	/*
	 * Moves classes between semesters
	 */
	public int moveClass(int startIndex,int stopIndex, int classIndex)
	{
		int ret = 0;
		//Make sure indices are valid
		if(startIndex>=0&&startIndex<semesters.size()&&stopIndex<semesters.size()&&stopIndex>=0&&classIndex>=0&&classIndex<semesters.get(startIndex).getCourses().size())
		{
			//Make sure the destination semester isn't full
			if(semesters.get(stopIndex).getNumClasses()<5)
			{
				Requirement toMove = semesters.get(startIndex).getCourses().get(classIndex);
				Boolean canAdd = true;
				if(startIndex>stopIndex)
				{	
					/*
					 * Go through from the destination to the end and make sure that it's not placed
					 * before/with one of it's prerequisites
					 */
					for(int i=stopIndex;i<semesters.size();i++)
					{
						Semester se = semesters.get(i);
						for(Requirement r:se.getCourses())
						{
							if(toMove.getPrerequisiteFor()!=null)
							{
								for(Requirement check:toMove.getPrerequisiteFor())
								{
									if(check.isEqual(r))
									{
										canAdd=false;
										//tried to add a class to a semester before/with a prereq
										ret=3;
									}
								}
							}				
						}
					}
				}
				else if(startIndex<stopIndex)
				{
					/*
					 * Go through from the start to the end and check that the class isn't placed
					 * after/with one of the classes it's a prerequisite for
					 */
					for(int i=startIndex;i<stopIndex;i++)
					{
						Semester se = semesters.get(i);
						
						for(Requirement r:se.getCourses())
						{
							if(r.getPrerequisiteFor()!=null)
							{
								for(Requirement check:r.getPrerequisiteFor())
								{
									if(check.isEqual(toMove))
									{
										canAdd=false;
										//tried to place a class with/past it's a prereq for
										ret=4;
									}
								}				
							}
						}
					}
				}
				//adding class to the same semester it's in
				else
				{
					canAdd=false;
					ret=5;
				}
				if(canAdd)
				{
					Requirement test1 = semesters.get(startIndex).removeCourse(classIndex);
					boolean test2 = semesters.get(stopIndex).addCourse(toMove);
					
					if(test1!=null)
						System.out.println("Removed Sucessfully");
					if(test2)
						System.out.println("added sucessfully");
				}
			}
			//Destination semester full
			else
				ret=2;
		}
		//Indices out of bounds
		else
			ret=1;
		return ret;
	}
	
	/*
	 * testing main
	 */
	public static void main(String [] args)
	{
		Schedule s = new Schedule();
		ConcreteRequirement a = new ConcreteRequirement("a",1,"aa",11,false);
		ConcreteRequirement b = new ConcreteRequirement("b",2,"bb",22,false);
		b.addPrerequisiteFor(a);
		ConcreteRequirement c = new ConcreteRequirement("c",3,"cc",33,false);
		c.addPrerequisiteFor(b);
		ConcreteRequirement d = new ConcreteRequirement("d",4,"dd",44,false);
		d.addPrerequisiteFor(c);
		ConcreteRequirement e = new ConcreteRequirement("e",5,"ee",55,false);
		e.addPrerequisiteFor(d);
		ConcreteRequirement f = new ConcreteRequirement("f",6,"ff",66,false);
		f.addPrerequisiteFor(e);
		ConcreteRequirement g = new ConcreteRequirement("g",7,"gg",77,false);
		g.addPrerequisiteFor(f);
		ConcreteRequirement h = new ConcreteRequirement("h",8,"hh",88,false);
		h.addPrerequisiteFor(a);
		ConcreteRequirement i = new ConcreteRequirement("i",9,"ii",99,false);
		i.addPrerequisiteFor(h);
		ConcreteRequirement j = new ConcreteRequirement("j",10,"jj",1010,false);
		ConcreteRequirement k = new ConcreteRequirement("k",11,"kk",1111,false);
		ConcreteRequirement l = new ConcreteRequirement("l",12,"ll",1212,false);
		ConcreteRequirement m = new ConcreteRequirement("m",13,"mm",1313,false);
		ConcreteRequirement n = new ConcreteRequirement("n",14,"nn",1414,false);
		ConcreteRequirement o = new ConcreteRequirement("o",15,"oo",1515,false);
		ConcreteRequirement p = new ConcreteRequirement("p",16,"pp",1616,false);
		ConcreteRequirement q = new ConcreteRequirement("q",17,"qq",1717,false);
		ConcreteRequirement t = new ConcreteRequirement("t",20,"tt",2020,false);
		ConcreteRequirement u = new ConcreteRequirement("u",21,"uu",2121,false);
		ConcreteRequirement v = new ConcreteRequirement("v",22,"vv",2222,false);
		ConcreteRequirement w = new ConcreteRequirement("w",23,"ww",2323,false);
		ConcreteRequirement x = new ConcreteRequirement("x",24,"xx",2424,false);
		ConcreteRequirement y = new ConcreteRequirement("y",25,"yy",2525,false);
		ConcreteRequirement z = new ConcreteRequirement("z",26,"zz",2626,false);
		ArrayList<Requirement>r = new ArrayList<>();
		r.add(a);
		r.add(b);
		r.add(c);
		r.add(d);
		r.add(e);
		r.add(f);
		r.add(g);
		r.add(h);
		r.add(i);
		r.add(j);
		r.add(k);
		r.add(l);
		r.add(m);
		r.add(n);
		r.add(o);
		r.add(p);
		r.add(q);
		r.add(t);
		r.add(u);
		r.add(v);
		r.add(w);
		r.add(x);
		r.add(y);
		r.add(z);
		s.genSchedule(r);
		ArrayList<Semester> output = s.getScheudle();
		int symCntr=0;
		int reqCntr=0;
		for(Semester se:output)
		{
			symCntr++;
			System.out.println("Semester "+symCntr+":\n");
			for(Requirement req:se.getCourses())
			{
				reqCntr++;
				System.out.println("Course "+reqCntr+": "+req.getSubject()+" "+req.getNumber()+" "+req.getLabel()+"\n");
			}
			reqCntr=0;
		}
		output=null;
		int[] results = new int[10];
		results[0]=s.moveClass(8, 8, 0);
		System.out.println(results[0]);
		results[1]=s.moveClass(0, 1, 0);
		System.out.println(results[1]);
		results[2]=s.moveClass(0, 8, 0);
		System.out.println(results[2]);
		results[3]=s.moveClass(8, 7, 0);
		System.out.println(results[3]);
		results[4]=s.moveClass(8, 0, 0);
		System.out.println(results[4]);
		results[5]=s.moveClass(0, 8, 4);
		System.out.println(results[5]);
		results[6]=s.moveClass(8, 0, 1);
		System.out.println(results[6]);
		output = s.getScheudle();
		symCntr=0;
		reqCntr=0;
		for(Semester se:output)
		{
			symCntr++;
			System.out.println("Semester "+symCntr+":\n");
			for(Requirement req:se.getCourses())
			{
				reqCntr++;
				System.out.println("Course "+reqCntr+": "+req.getSubject()+" "+req.getNumber()+" "+req.getLabel()+"\n");
			}
			reqCntr=0;
		}
	}
}
