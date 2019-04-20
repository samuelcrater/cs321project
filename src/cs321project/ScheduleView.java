package cs321project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ScheduleView extends JPanel {

	private JPanel interior;
	
	public ScheduleView() {
		this.setPreferredSize(new Dimension(750,550)); //550, 550
		this.setMaximumSize(new Dimension(750,550));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		JLabel title = new JLabel("Schedule");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 30));
		this.add(title);
		//this.setBackground(Color.GRAY);
		this.interior = new JPanel();
		this.add(interior);
	}
	
	public void populate(ArrayList<Semester> semesters) {
		this.interior.removeAll();
		
		int i = 1;
		for (Semester s : semesters) {
			JPanel semester = new JPanel();
			semester.setLayout(new BoxLayout(semester, BoxLayout.PAGE_AXIS));
			semester.setMinimumSize(new Dimension(175,250));
			semester.setPreferredSize(new Dimension(175,250));
			semester.setAlignmentX(CENTER_ALIGNMENT);
			semester.setBorder(BorderFactory.createEmptyBorder(5,5,3,5));
			semester.setBackground(Color.LIGHT_GRAY);
			
			JLabel text = new JLabel("Semester " + i);
			text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 15));
			semester.add(text);
			
			for (Requirement r : s.getCourses()) {
				JPanel course = new JPanel();
				course.setBackground(Color.GRAY);
				JLabel label = new JLabel(r.getLabel());
				label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 12));
				course.add(label);
				//course.addA
				semester.add(course);
				semester.add(new Box.Filler(new Dimension(0, 2), new Dimension(0, 2), new Dimension(0, 2)));
			}
			
			this.interior.add(semester);
			i++;
		}
		
		this.interior.validate();
	}
}
