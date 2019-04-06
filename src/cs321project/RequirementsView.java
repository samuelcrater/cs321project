package cs321project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RequirementsView extends JPanel implements Observer {

	private ArrayList<JCheckBox> checkboxes;
	private ScheduleController sc;
	private Degree degree;
	
	private JPanel interior;
	private JButton generateButton;
	
	public RequirementsView() {
		this.setPreferredSize(new Dimension(250,550));
		JLabel text = new JLabel("Requirements");
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
				for (int i = 0; i < checkboxes.size(); i++) {
					if (checkboxes.get(i).isSelected()) {
						//sc.fufillRequirement(r);
					}
				}
				sc.generateSchedule();
			}
			
		});
		this.add(generateButton);
	}
	
	private void createRow(Requirement r) {
		JCheckBox checkBox = new JCheckBox();
		/*
		if (r instanceof ConcreteRequirement) {
			add button
			add actionlistener
		}
		label = new JLabel(r.getLabel());
		*/
		
		checkBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				r.setFulfilled(checkBox.isSelected());
			}
			
		});
	}
	
	protected void setGenerateButtonState(boolean enabled) {
		this.generateButton.setEnabled(enabled);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}
	
	//listen to Degree. On update, pull 
}
