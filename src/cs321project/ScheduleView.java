package cs321project;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScheduleView extends JPanel {

	public ScheduleView() {
		this.setPreferredSize(new Dimension(550,550));
		JLabel text = new JLabel("Schedule");
		this.add(text);
		//this.setBackground(Color.GRAY);
	}
}
