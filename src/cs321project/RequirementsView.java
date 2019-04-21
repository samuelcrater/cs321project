package cs321project;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RequirementsView extends JPanel {

	private static final long serialVersionUID = -8184697493125918694L;
	private JLabel degreeLabel;
	private JScrollPane interiorScroller;
	private JPanel interior;
	private JButton generateButton;
	
	private ScheduleController sc;
	
	public RequirementsView() {
		this.setPreferredSize(new Dimension(250,550));
		this.setMaximumSize(new Dimension(250,550));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		degreeLabel = new JLabel();
		degreeLabel.setFont(new Font(degreeLabel.getFont().getName(), Font.PLAIN, 30));
		degreeLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(degreeLabel);
		
		JLabel reqLabel = new JLabel("Requirements");
		reqLabel.setFont(new Font(reqLabel.getFont().getName(), Font.PLAIN, 30));
		reqLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.add(reqLabel);
		
		interior = new JPanel();
		interiorScroller = new JScrollPane(interior);
		interiorScroller.getVerticalScrollBar().setUnitIncrement(20);
		this.add(interiorScroller);
		
		generateButton = new JButton("Generate Schedule");
		generateButton.setAlignmentX(CENTER_ALIGNMENT);
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
    		JLabel section = new JLabel(s);
    		section.setFont(new Font(section.getFont().getName(), Font.PLAIN, 15));
    		this.interior.add(section);
    		
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
	
	public void setDegreeLabel(String text) {
		degreeLabel.setText(text);
	}
}
