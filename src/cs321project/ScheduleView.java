package cs321project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScheduleView extends JPanel {

	private JPanel interior;
	
	public ScheduleView() {
		this.setPreferredSize(new Dimension(550,550));
		JLabel text = new JLabel("Schedule");
		text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 30));
		this.add(text);
		//this.setBackground(Color.GRAY);
	}
	
	public void populate(Schedule schedule) {
		this.interior.removeAll();
		
		int i = 1;
		for (Semester s : schedule.getScheudle()) {
			JPanel semester = new JPanel();
			semester.setLayout(new BoxLayout(semester, BoxLayout.PAGE_AXIS));
			semester.setMinimumSize(new Dimension(150,250));
			semester.setPreferredSize(new Dimension(150,250));
			
			JLabel text = new JLabel("Semester " + i);
			text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 20));
			semester.add(text);
			
			for (Requirement r : s.getCourses()) {
				JPanel course = new JPanel();
				JLabel label = new JLabel(r.getLabel());
				label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 15));
				course.add(label);
				//course.addA
				semester.add(course);
			}
			
			this.interior.add(semester);
			i++;
		}
		
		this.interior.validate();
	}
}
