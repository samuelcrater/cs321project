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

public class RequirementsView extends JPanel implements Observer {

	private ScheduleController sc;
	private Degree degree;
	
	private JPanel interior;
	private JButton generateButton;
	
	public RequirementsView() {
		this.setPreferredSize(new Dimension(250,550));
		JLabel text = new JLabel("Requirements");
		text.setFont(new Font(text.getFont().getName(), Font.PLAIN, 30));
		this.add(text);
		this.setBackground(Color.GRAY);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//panel
		interior = new JPanel();
		this.add(interior);
		
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
    		this.interior.add(new JLabel(s));
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
        		
        		row.add(new Box.Filler(new Dimension(50, 0), new Dimension(50, 0), new Dimension(50, 0)));
        		row.add(new JLabel(r.getLabel()));
        		this.interior.add(row);
    		}
    	}
    	
    	this.interior.validate();
	}
	
	protected void setGenerateButtonState(boolean enabled) {
		this.generateButton.setEnabled(enabled);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	//listen to Degree. On update, pull 
}
