package cs321project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScheduleView extends JPanel {

	private static final long serialVersionUID = -6246170280637883314L;
	private ScheduleController sc;
	private JPanel interior;
	private JPanel destination;
	private boolean dragging, sourceProtection;
	
	public ScheduleView() {
		this.setPreferredSize(new Dimension(750,550)); //550, 550
		this.setMaximumSize(new Dimension(750,550));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		JLabel title = new JLabel("Schedule");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 30));
		title.setAlignmentX(CENTER_ALIGNMENT);
		this.add(title);
		this.interior = new JPanel();
		this.add(interior);
		dragging = false;
		sourceProtection = false;
	}
	
	public void populate(ArrayList<Semester> semesters) {
		this.interior.removeAll();
		int semesterCount = 1;
		
		for (Semester s : semesters) {
			JPanelSemester semester = new JPanelSemester(semesterCount - 1);
			semester.setLayout(new BoxLayout(semester, BoxLayout.PAGE_AXIS));
			semester.setMinimumSize(new Dimension(175,250));
			semester.setPreferredSize(new Dimension(175,250));
			semester.setAlignmentX(CENTER_ALIGNMENT);
			semester.setBorder(BorderFactory.createEmptyBorder(5,5,3,5));
			semester.setBackground(Color.LIGHT_GRAY);
			this.setSemesterMouseListener(semester);
			
			JLabel text = new JLabel("Semester " + semesterCount);
			text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 15));
			text.setAlignmentX(CENTER_ALIGNMENT);
			semester.add(text);
			
			int courseCount = 0;
			for (Requirement r : s.getCourses()) {
				JPanelCourse course = new JPanelCourse(courseCount, semesterCount - 1);
				course.setBackground(Color.GRAY);
				JLabel label = new JLabel(r.getLabel());
				label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 12));
				course.add(label);
				this.setCourseMouseListener(course);
				semester.add(course);
				semester.add(new Box.Filler(new Dimension(0, 2), new Dimension(0, 2), new Dimension(0, 2)));
				courseCount++;
			}
			
			this.interior.add(semester);
			semesterCount++;
		}
		
		this.interior.validate();
	}
	
	private void setSemesterMouseListener(JPanelSemester semester) {
		semester.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (dragging) {
					semester.setBackground(Color.RED);
					destination = semester;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (dragging) {
					semester.setBackground(Color.LIGHT_GRAY);
				}
			}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (dragging) {
					semester.setBackground(Color.LIGHT_GRAY);
					destination.setBackground(Color.LIGHT_GRAY);
					dragging = false;
					
				}
			}
		});
	}
	
	private void setCourseMouseListener(JPanelCourse course) {
		course.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (dragging) {
					course.setBackground(Color.RED);
					destination = course;
				}
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				if (dragging && !sourceProtection) {
					course.setBackground(Color.GRAY);
				}
				sourceProtection = false;
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				destination = course; //in case pressed and released on same item
				course.setBackground(Color.RED);
				dragging = true;
				sourceProtection = true;
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				course.setBackground(Color.GRAY);
				if (destination instanceof JPanelCourse) {
					destination.setBackground(Color.GRAY);
					sc.swapCourse(course.getCourseNumber(), course.getSemesterNumber(), 
							((JPanelCourse) destination).getCourseNumber(), ((JPanelCourse) destination).getSemesterNumber());
				} else {
					destination.setBackground(Color.LIGHT_GRAY);
					sc.moveCourse(course.getCourseNumber(), course.getSemesterNumber(), ((JPanelSemester) destination).getSemesterNumber());
				}
				dragging = false;
			}
		});
	}
	
	public void setScheduleController(ScheduleController sc) {
		this.sc = sc;
	}
}
