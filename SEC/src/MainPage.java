import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class MainPage {
	/**
	 * Launch the application.
	 */

	
	
	// Global declaration of local variables
	boolean isSubmitted = false;
	int score = 0;
	static String folderPath = "Questions/";
	static String questionPath = "";
	

	// Global declaration of GUI elements
	private JFrame frame;
	static JPanel panel;

	// Global declaration of class objects
	static Question questObject;
	static QuestionsHandler qHandleObject  = new QuestionsHandler(folderPath);
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					questionPath = qHandleObject.selectQuestion();
					questObject = new Question(questionPath);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set Look and Feel of the UI
					MainPage window = new MainPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 * 
	 * @param path
	 */
	public MainPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() {
		/* Local variable initialization */
		JLabel lblTitle, lblScore, lblQuestion;
		isSubmitted = false;
		questObject.readQuestion();
		/* Designing frame */
		if (frame == null)
			frame = new JFrame();
		else {
			frame.getContentPane().removeAll();
		}
		frame.setBounds(0, 0, 1366, 768);
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(0, 44, 61));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("eGuru");
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);

		/* Designing panel */
		if (panel == null)
			panel = new JPanel();
		else
			panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(0, 44, 61));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); //Padding around panel

		/* Labels to display title and score at the top of the panel */
		lblScore = new JLabel("Score = "+score);
		lblTitle = new JLabel("");
		lblTitle.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/logoalt.png"))))); //For alternate logo design
		lblScore.setFont(new Font("Georgia", Font.ITALIC+Font.BOLD, 28));
		lblScore.setForeground(Color.WHITE);
		lblScore.setMaximumSize(new Dimension(1366, 100));
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTitle);
		panel.add(lblScore);
		panel.add(Box.createVerticalStrut(20));

		/*
		 * Designing split panel to accommodate Model image and Free Body
		 * Diagram
		 */
		JPanel innerPanel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) innerPanel1.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setAlignment(FlowLayout.CENTER);

		JPanel innerPanel2 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) innerPanel2.getLayout();
		flowLayout_2.setVgap(20);
		flowLayout_2.setAlignment(FlowLayout.CENTER);

		innerPanel1.setBackground(new Color(0, 44, 61));
		innerPanel2.setBackground(new Color(0, 44, 61));

		// Label for model image
		innerPanel1.add(questObject.getModelImage(), "22, 2, fill, default");

		// Label for FBD image
		innerPanel2.add(questObject.getFbdImage(), "22, 6, fill, default");

		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, innerPanel1, innerPanel2);
		splitpane.setAlignmentY(0.5f);
		splitpane.setResizeWeight(0.5);
		splitpane.setDividerSize(0);
		splitpane.setMaximumSize(new Dimension(2000, 450));
		splitpane.setBorder(null);
		panel.add(splitpane);

		/* Gap between splitpane, and assumptions and reasons */
		panel.add(Box.createVerticalStrut(10));
		
		/* Question String */
	    lblQuestion = new JLabel("Q: Which of the following assumptions are needed?");
	    lblQuestion.setForeground(Color.WHITE);
		lblQuestion.setFont(new Font("Georgia", Font.PLAIN, 17));
		panel.add(Box.createVerticalStrut(20));
		panel.add(lblQuestion);
		panel.add(Box.createVerticalStrut(5));

		/* Designing check boxes for assumptions */
		GUISettingAssumptionsList();

		//End Test Button
		JButton endButton = new JButton("End Test");
		endButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				String [] arguments = {""+score};
				EndPage.main(arguments);
			}
		});
		endButton.setVisible(false);
		
		// Next Button
		JButton nxtButton = new JButton("Next Question");
		nxtButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				questionPath = qHandleObject.selectQuestion();
				questObject = new Question(questionPath);
				initialize();
			}
		});
		nxtButton.setVisible(false);

		// Submit button
		//Call Question class to get score for assumption selection
		//For incorrect/complicated assumption, show list of reasons
		//On submitting reason, we need to find a way to check which assumption has that reason
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isSubmitted) {
					score = questObject.ScoreCalculationReason(score);
					lblScore.setText("Score = " + score);
					submitButton.setVisible(false);
					if(qHandleObject.isLastQuestion()) {
						nxtButton.setVisible(false);
						endButton.setVisible(true);
					}	
					else {
						nxtButton.setVisible(true);
					}
						
				} else {
					score = questObject.ScoreCalculation(score);
					lblScore.setText("Score = " + score);
					if(!questObject.anywrong){
						submitButton.setVisible(false);
						if(qHandleObject.isLastQuestion()){	
							nxtButton.setVisible(false);
							endButton.setVisible(true);
						}
						else {
							nxtButton.setVisible(true);
						}
					}
					questObject.disableCheckBoxes();
					isSubmitted = true;
				}
			}
		});
		/* Gap between options and button */
		panel.add(Box.createVerticalStrut(20));
		panel.add(submitButton);
		panel.add(nxtButton);
		panel.add(endButton);
		JScrollPane scrollPane = new JScrollPane(panel);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	/*
	 * GUI SETTING ASSUMPTION LIST Method to display assumption check boxes on
	 * the GUI and initialize reason lists based on the type of assumption
	 * (Correct/Incorrect/Complicated) Input: List of all assumptions
	 */
	public void GUISettingAssumptionsList() {
				
		// Split all assumptions in answer and statements, create check boxes
		// Traverse through check boxes and add them on panel
		//EngineeringEducatorAssumption.getAssumptions();
		//JLabel lblIncorrect = assumObject.AssumptionType(reasonObject);

		int numAssum = questObject.getNumberOfAssumptions();
		for (int i = 0; i < numAssum; i++) {
			JCheckBox chkBox = questObject.getAssumptionCheckbox(i);
			panel.add(chkBox);
			Assumption assumObject = questObject.getAssumObj(chkBox.getText());
			JLabel lblMessage = assumObject.getlblMessage();
//			panel.add(Box.createVerticalGlue());
			panel.add(Box.createRigidArea(new Dimension(0,5)));
			// Incorrect assumption
			if (lblMessage != null) {
				lblMessage.setForeground(Color.WHITE);
				lblMessage.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
				panel.add(lblMessage);
				// Calling method to add reasons for incorrect assumption
				GUISettingReasonsList(chkBox.getText());
			}
			
			
		}
	}

	/*
	 * GUI SETTING REASONS LIST Method to display all reasons on the GUI based
	 * on the assumption selected
	 */

	public void GUISettingReasonsList(String assumption) {
		// Read reason file for incorrect assumption
		ArrayList<JRadioButton> rdbList = new ArrayList<JRadioButton>();
		Assumption assumObject = questObject.getAssumObj(assumption);
		rdbList = assumObject.getReasonRdbList();
		
		if (rdbList != null) {
			// Create radio buttons for all reasons
			for (int rdbIndex = 0; rdbIndex < rdbList.size(); rdbIndex++) {
				// Adding radio buttons onto panel
				panel.add(rdbList.get(rdbIndex));
			}
		}
		assumObject.setRdbListVisibility(false);
	}
}