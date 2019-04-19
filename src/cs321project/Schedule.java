package cs321project;

import java.util.ArrayList;
import java.util.Collections;
public class Schedule {
	private ArrayList<Semester> semesters;
	public Schedule()
	{
		semesters = new ArrayList<>();
		semesters.add(new Semester());
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
		int addedAt=-1;
		while(!reqs.isEmpty())
		{
			boolean addable=true;
			boolean noListBlock=true;
			boolean added=false;
			for(int listCntr=0;listCntr<reqs.size();listCntr++)
			{
				addable=true;
				noListBlock=true;
				added=false;
				Requirement reqNext = reqs.get(listCntr);
				if(reqNext.getPrerequisiteFor()!=null)
				{
					for(Requirement r:reqNext.getPrerequisiteFor())
					{
						for(Requirement listchk:reqs)
						{
							if(r.isEqual(listchk))
							{
								noListBlock=false;
								break;
							}
						}
						if(!noListBlock)
							break;
					}
				}
				if(noListBlock)
				{
					if(reqNext.getPrerequisiteFor()!=null)
					{
						for(Requirement r :reqNext.getPrerequisiteFor())
						{
							for(Requirement semChk : semesters.get(semesterCntr).getCourses())
							{
								if(r.isEqual(semChk))
								{
									addable=false;
									break;
								}
							}

							if(!addable)
								break;
						}
					}
					if(addable)
					{
						added = semesters.get(semesterCntr).addCourse(reqNext);
						if(added)
						{
							addedAt=listCntr;
							break;
						}
					}
				}
				
			}
			if(added)
			{
				reqs.remove(addedAt);
			}
			else 
			{
				semesters.add(new Semester());
				semesterCntr++;
			}
			
		}
		
		
	}
	/*
	 * generate a schedule based off of an arraylist of classes (for testing)
	 */
	public void genSchedule(ArrayList<Requirement>c)
	{
		ArrayList<Requirement> reqs = new ArrayList<>();
		for(Requirement r:c)
			reqs.add(r);
		int semesterCntr=0;
		int addedAt=-1;
		while(!reqs.isEmpty())
		{
			boolean addable=true;
			boolean noListBlock=true;
			boolean added=false;
			for(int listCntr=0;listCntr<reqs.size();listCntr++)
			{
				addable=true;
				noListBlock=true;
				added=false;
				Requirement reqNext = reqs.get(listCntr);
				if(reqNext.getPrerequisiteFor()!=null)
				{
					for(Requirement r:reqNext.getPrerequisiteFor())
					{
						for(Requirement listchk:reqs)
						{
							if(r.isEqual(listchk))
							{
								noListBlock=false;
								break;
							}
						}
						if(!noListBlock)
							break;
					}
				}
				if(noListBlock)
				{
					if(reqNext.getPrerequisiteFor()!=null)
					{
						for(Requirement r :reqNext.getPrerequisiteFor())
						{
							for(Requirement semChk : semesters.get(semesterCntr).getCourses())
							{
								if(r.isEqual(semChk))
								{
									addable=false;
									break;
								}
							}

							if(!addable)
								break;
						}
					}
					if(addable)
					{
						added = semesters.get(semesterCntr).addCourse(reqNext);
						if(added)
						{
							addedAt=listCntr;
							break;
						}
					}
				}
				
			}
			if(added)
			{
				reqs.remove(addedAt);
			}
			else 
			{
				semesters.add(new Semester());
				semesterCntr++;
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
	 * Swaps 2 courses between semesters
	 * returns:
	 * 0- successful swap
	 * 1- semester 1 is out of bounds
	 * 2- semester 2 is out of bounds
	 * 3- course1 is out of bounds
	 * 4- course2 is out of bounds
	 * 5- trying to move a course back to/before a prerequisite
	 * 6- trying to move a prerequisite to/past a class that needs it
	 */
	public int swapCourse(int course1,int semester1, int course2, int semester2)
	{
		int ret = 0;
		//Semester 1 is out of bounds
		if(semester1<0||semester1>=semesters.size())
			ret=1;
		else
		{
			//Semester 2 is out of bounds
			if(semester2<0||semester2>=semesters.size())
				ret=2;
			else
			{
				//Course 1 is out of bounds
				if(course1<0 || course1>=semesters.get(semester1).getCourses().size())
					ret=3;
				else
				{
					//Course 2 is out of bounds
					if(course2<0||course2 >=semesters.get(semester2).getCourses().size())
						ret=4;
					else
					{
						//Check for the same semester
						if(semester1==semester2) 
						{
							Collections.swap(semesters.get(semester2).getCourses(), course1, course2);
						}
						//Branch check based on which semester comes first
						else 
						{
							boolean canMove = true;
							Requirement move1 = semesters.get(semester1).getCourses().get(course1);
							Requirement move2 = semesters.get(semester2).getCourses().get(course2);
							if(semester1>semester2)
							{
								if(move1.getPrerequisiteFor()!=null)
								{
									for(int i = semester2;i<=semester1;i++)
									{
										for(Requirement chk:semesters.get(i).getCourses())
										{
											for(Requirement mvChk:move1.getPrerequisiteFor())
											{
												if(chk.isEqual(mvChk))
												{
													canMove=false;
													ret = 5;
												}
											}
										}
									}
								}
								
								for(int i = semester2;i<= semester1;i++)
								{
									for(Requirement r:semesters.get(i).getCourses())
									{
										if(r.getPrerequisiteFor()!=null)
										{
											for(Requirement chk:r.getPrerequisiteFor())
											{
												if(chk.isEqual(move2))
												{
													canMove=false;
													ret=6;
												}
											}
										}
									}
								}
								
							}
							else
							{
								
								for(int i = semester1;i<=semester2;i++)
								{
									for(Requirement r:semesters.get(i).getCourses())
									{
										if(r.getPrerequisiteFor()!=null)
										{
											for(Requirement mvChk:r.getPrerequisiteFor())
											{
												if(mvChk.isEqual(move1))
												{
													canMove=false;
													ret = 6;
												}
											}
										}
									}
								}
								if(move2.getPrerequisiteFor()!=null)
								{
									for(int i=semester1;i<semester2;i++)
									{
										for(Requirement r:semesters.get(i).getCourses())
										{
											for(Requirement mvChk:move2.getPrerequisiteFor())
											{
												if(r.isEqual(mvChk))
												{
													canMove=false;
													ret=5;
												}
											}
										}
									}
								}
							}
							if(canMove)
							{
								 semesters.get(semester1).getCourses().remove(course1);
								 semesters.get(semester2).getCourses().remove(course2);
								 boolean test1 = semesters.get(semester1).addCourse(move2);
								 boolean test2 = semesters.get(semester2).addCourse(move1);
								 if(!test1)
									 System.out.println("Determined that move was feasable but failed move2");
								 if(!test2)
									 System.out.println("Determined that move was feasable but failed move1");
							}
						}
						
					}
				}
			}
		}
		return ret;
	}
	
	/*
	 * testing main
	 */
	public static void main(String [] args)
	{
		Schedule sched = new Schedule();
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
		j.addPrerequisiteFor(h);
		j.addPrerequisiteFor(g);
		ConcreteRequirement k = new ConcreteRequirement("k",11,"kk",1111,false);
		ConcreteRequirement l = new ConcreteRequirement("l",12,"ll",1212,false);
		ConcreteRequirement m = new ConcreteRequirement("m",13,"mm",1313,false);
		ConcreteRequirement n = new ConcreteRequirement("n",14,"nn",1414,false);
		ConcreteRequirement o = new ConcreteRequirement("o",15,"oo",1515,false);
		ConcreteRequirement p = new ConcreteRequirement("p",16,"pp",1616,false);
		ConcreteRequirement q = new ConcreteRequirement("q",17,"qq",1717,false);
		ConcreteRequirement r = new ConcreteRequirement("r",18,"rr",1818,false);
		ConcreteRequirement s = new ConcreteRequirement("s",19,"ss",1919,false);
		ConcreteRequirement t = new ConcreteRequirement("t",20,"tt",2020,false);
		ConcreteRequirement u = new ConcreteRequirement("u",21,"uu",2121,false);
		ConcreteRequirement v = new ConcreteRequirement("v",22,"vv",2222,false);
		ConcreteRequirement w = new ConcreteRequirement("w",23,"ww",2323,false);
		ConcreteRequirement x = new ConcreteRequirement("x",24,"xx",2424,false);
		ConcreteRequirement y = new ConcreteRequirement("y",25,"yy",2525,false);
		ConcreteRequirement z = new ConcreteRequirement("z",26,"zz",2626,false);
		ArrayList<Requirement>required = new ArrayList<>();
		required.add(a);
		required.add(b);
		required.add(c);
		required.add(d);
		required.add(e);
		required.add(f);
		required.add(g);
		required.add(h);
		required.add(i);
		required.add(j);
		required.add(k);
		required.add(l);
		required.add(m);
		required.add(n);
		required.add(o);
		required.add(p);
		required.add(q);
		required.add(r);
		required.add(s);
		required.add(t);
		required.add(u);
		required.add(v);
		required.add(w);
		required.add(x);
		required.add(y);
		required.add(z);
		sched.genSchedule(required);
		ArrayList<Semester> output = sched.getScheudle();
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
		results[0]=sched.moveClass(8, 8, 0);
		System.out.println(results[0]);
		results[1]=sched.moveClass(0, 1, 0);
		System.out.println(results[1]);
		results[2]=sched.moveClass(0, 8, 0);
		System.out.println(results[2]);
		results[3]=sched.moveClass(8, 7, 0);
		System.out.println(results[3]);
		results[4]=sched.moveClass(8, 0, 0);
		System.out.println(results[4]);
		results[5]=sched.moveClass(0, 8, 4);
		System.out.println(results[5]);
		results[6]=sched.moveClass(8, 0, 1);
		System.out.println(results[6]);
		output = sched.getScheudle();
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
