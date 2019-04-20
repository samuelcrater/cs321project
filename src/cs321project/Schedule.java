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
	 * returns:
	 * 0-successful move
	 * 1- start index <0
	 * 2- start index greater than number of semesters
	 * 3- stop index <0
	 * 4- stop index greater than number of semesters
	 * 5- class index <0
	 * 6- class index greater than number of classes in it's semester
	 * 7- destination semester full
	 * 8- tried to add class to semester it's in
	 * 9- trying to move a course back to/before a prerequisite
	 * 10-trying to move a prerequisite to/past a class that needs it
	 */
	public int moveClass(int startIndex,int stopIndex, int classIndex)
	{
		int ret = 0;
		//Make sure indices are valid
		//start semester is out of bounds
		if(startIndex<0)
			ret=1;
		else if(startIndex>=semesters.size())
			ret=2;
		else
		{
			//stop semester is out of bounds
			if(stopIndex<0)
				ret=3;
			else if (stopIndex>=semesters.size())
				ret=4;
			else
			{
				//class is out of bounds
				if(classIndex<0)
					ret=5;
				else if(classIndex>=semesters.get(startIndex).getCourses().size())
					ret=6;
				else
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
												ret=9;
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
							for(int i=startIndex;i<=stopIndex;i++)
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
												ret=10;
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
							ret=8;
						}
						if(canAdd)
						{
							Requirement test1 = semesters.get(startIndex).removeCourse(classIndex);
							boolean test2 = semesters.get(stopIndex).addCourse(toMove);
							
							if(test1==null)
								System.out.println("Remove Failed");
							if(!test2)
								System.out.println("add failed");
						}
					}
					//Destination semester full
					else
						ret=7;
				}
			}
		}
		return ret;
	}
	/*
	 * Swaps 2 courses between semesters
	 * returns:
	 * 0- successful swap
	 * 1- semester 1 is less than 0
	 * 2- semester 1 is greater than the number of semesters
	 * 3- semester 2 is less than 0
	 * 4- semester 2 is greater than the number of semesters
	 * 5- course 1 is less than 0
	 * 6- course 1 is greater than the number of classes in it's semester
	 * 7- course 2 is less than 0
	 * 8- course 2 is greater than the number of classes in it's semester
	 * 9- trying to move a course back to/before a prerequisite
	 * 10- trying to move a prerequisite to/past a class that needs it
	 */
	public int swapClass(int course1,int semester1, int course2, int semester2)
	{
		int ret = 0;
		//Semester 1 is out of bounds
		if(semester1<0)
			ret=1;
		else if(semester1>=semesters.size())
			ret=2;
		else
		{
			//Semester 2 is out of bounds
			if(semester2<0)
				ret=3;
			else if (semester2>=semesters.size())
				ret=4;
			else
			{
				//Course 1 is out of bounds
				if(course1<0)
					ret=5;
				else if(course1>=semesters.get(semester1).getCourses().size())
					ret=6;
				else
				{
					//Course 2 is out of bounds
					if(course2<0)
						ret=7;
					else if(course2 >=semesters.get(semester2).getCourses().size())
						ret = 8;
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
													ret = 9;
												}
											}
										}
									}
								}
								if(canMove)
								{	
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
														ret=10;
													}
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
													ret = 10;
												}
											}
										}
									}
								}
								
								if(canMove)
								{
									if(move2.getPrerequisiteFor()!=null)
									{
										for(int i=semester1;i<=semester2;i++)
										{
											for(Requirement r:semesters.get(i).getCourses())
											{
												for(Requirement mvChk:move2.getPrerequisiteFor())
												{
													if(r.isEqual(mvChk))
													{
														canMove=false;
														ret=9;
													}
												}
											}
										}
									}
								}
								
							}
							if(canMove)
							{
								 semesters.get(semester1).removeCourse(course1);
								 semesters.get(semester2).removeCourse(course2);
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
		ArrayList<Integer> results = new ArrayList<>();
		results.add(sched.moveClass(0, 7, 1));
		results.add(sched.moveClass(-1, 0, 0));
		results.add(sched.moveClass(8, 8, 0));
		results.add(sched.moveClass(7, -1, 0));
		results.add(sched.moveClass(7, 8, 0));
		results.add(sched.moveClass(0, 7, -1));
		results.add(sched.moveClass(0, 7, 6));
		results.add(sched.moveClass(0, 1, 1));
		results.add(sched.moveClass(0, 0, 0));
		//test 9 and 10 twice
		results.add(sched.moveClass(7, 6, 0));
		results.add(sched.moveClass(7, 5, 0));
		
		results.add(sched.moveClass(5, 7, 0));
		results.add(sched.moveClass(6, 7, 0));
		
		for(Integer moveRet: results)
		{
			System.out.println(moveRet);
		}
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
		results.clear();
		results.add(sched.swapClass(1,0,1,7));
		results.add(sched.swapClass(1,-1,1,7));
		results.add(sched.swapClass(1,8,1,7));
		results.add(sched.swapClass(1,0,1,-1));
		results.add(sched.swapClass(1,0,1,8));
		results.add(sched.swapClass(-1,0,1,7));
		results.add(sched.swapClass(5,0,1,7));
		results.add(sched.swapClass(1,0,-1,7));
		results.add(sched.swapClass(1,0,3,7));
		//test 9&10 twice
		results.add(sched.swapClass(0,7,1,0));
		results.add(sched.swapClass(0,7,0,6));
		
		results.add(sched.swapClass(0,0,1,7));
		results.add(sched.swapClass(0,0,1,1));
		for(Integer swapRet:results)
			System.out.println(swapRet);
		
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
