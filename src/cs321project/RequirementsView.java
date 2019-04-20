package cs321project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RequirementsView extends JPanel {

	private ScheduleController sc;
	private Degree degree;
	
	private JScrollPane interiorScroller;
	private JPanel interior;
	private JButton generateButton;
	
	public RequirementsView() {
		this.setPreferredSize(new Dimension(250,550));
		this.setMaximumSize(new Dimension(250,550));
		JLabel title = new JLabel("Requirements");
		title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 30));
		this.add(title);
		//this.setBackground(Color.GRAY);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//panel
		interior = new JPanel();
		interiorScroller = new JScrollPane(interior);
		this.add(interiorScroller);
		
		//button
		generateButton = new JButton("Generate Schedule");
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sc.generateSchedule();
			}
		});
		this.add(generateButton);
	}
	
	public void populate(HashMap<String, ArrayList<Requirement>> requirements) {
		this.interior.removeAll();
		this.interior.setLayout(new BoxLayout(interior, BoxLayout.PAGE_AXIS));
		
		Set<String> keys = requirements.keySet();
    	for (String s : keys) {
    		//JPanel row = new JPanel();
    		//row.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
    		JLabel temp = new JLabel(s);
    		temp.setFont(new Font(temp.getFont().getName(), Font.PLAIN, 15));
    		this.interior.add(temp);
    		for (Requirement r : requirements.get(s)) {
    			JPanel row = new JPanel();
        		row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
        		row.setAlignmentX(LEFT_ALIGNMENT);
        		
        		JCheckBox checkBox = new JCheckBox();
        		checkBox.setSelected(r.isFulfilled());
        		checkBox.addActionListener(new ActionListener() {
        			@Override
        			public void actionPerformed(ActionEvent e) {
        				r.setFulfilled(checkBox.isSelected());
        			}
        		});
        		row.add(checkBox);
        		
        		row.add(new Box.Filler(new Dimension(30, 0), new Dimension(30, 0), new Dimension(30, 0)));
        		row.add(new JLabel(r.getLabel()));
        		this.interior.add(row);
    		}
    	}
    	
    	this.interior.validate();
	}
	
	protected void setGenerateButtonState(boolean enabled) {
		this.generateButton.setEnabled(enabled);
	}
	
	public void setScheduleController(ScheduleController sc) {
    	this.sc = sc;
    }
}
