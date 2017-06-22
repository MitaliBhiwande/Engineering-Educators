import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

public class SecWindow {
	/**
	 * Launch the application.
	 */

	// Global declaration of local variables
	boolean isSubmitted = false;
	int score = 0;
	static String folderPath = "", questionPath = "";

	// Global declaration of GUI elements
	private JFrame frame;
	static JPanel panel;

	// Global declaration of class objects

	static EngineeringEducatorQuestions quesObject;
	static EngineeringEducatorAssumption assumObject;
	static EngineeringEducatorReason reasonObject;
	static EngineeringEducatorModelImage modelImgObject;
	static EngineeringEducatorFBDImage fbdImgObject;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateObjects();
					InitializeContents();
					SecWindow window = new SecWindow();
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
	public SecWindow() {
		initialize();
	}

	public static void CreateObjects() {
		quesObject = new EngineeringEducatorQuestions();
		assumObject = new EngineeringEducatorAssumption();
		reasonObject = new EngineeringEducatorReason();
		modelImgObject = new EngineeringEducatorModelImage();
		fbdImgObject = new EngineeringEducatorFBDImage();
	}

	public static void InitializeContents() {
		folderPath = "Questions/";
		/* Calling method to select random question folder */
		questionPath = quesObject.FolderRandomSelection(folderPath);
		System.out.println("new path=" + questionPath);
		/* Calling method to fetch data from question folder */
		// eduLogicObj.DataPreProcessing(eduObject);

		File dir1 = new File(questionPath);
		File[] files = dir1.listFiles();

		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().contains("fbd.")) {
				fbdImgObject.ReadImage(new File(files[i].getPath()));
			} else if (files[i].getName().contains("model.")) {
				modelImgObject.ReadImage(new File(files[i].getPath()));
			} else if (files[i].getName().contains("assumptions.")) {
				assumObject.path = files[i].getPath();
				System.out.println(assumObject.path + " assumptions");
			}
		}
		reasonObject.path = dir1.getPath() + "/reasons";
		System.out.println(reasonObject.path + " reasons");

	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize() {
		/* Local variable initialization */
		JLabel lblTitle, lblScore, lblQuestion;
		isSubmitted = false;
		score = 0;
		/* Designing frame */
		if (frame == null)
			frame = new JFrame();
		else {
			frame.getContentPane().removeAll();
		}
		frame.setBounds(0, 0, 1366, 768);
		frame.setForeground(Color.BLACK);
		frame.setBackground(Color.RED);
		frame.getContentPane().setBackground(new Color(51, 153, 204));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Designing panel */
		if (panel == null)
			panel = new JPanel();
		else
			panel.removeAll();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(new Color(46, 42, 42));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30)); //Padding around panel


		/* Labels to display title and score at the top of the panel */
		lblScore = new JLabel("Score = 0");
		lblTitle = new JLabel("ENGINEERING EDUCATORS ");
		//lblTitle.setIcon(new ImageIcon (Toolkit.getDefaultToolkit().getImage((getClass().getResource("/images/logo.png")))));
		lblTitle.setVerticalAlignment(SwingConstants.TOP);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		lblScore.setForeground(Color.WHITE);
		panel.add(lblTitle);
		panel.add(lblScore);
		JSeparator sep = new JSeparator();
		sep.setMaximumSize(new Dimension(0, 30));
		sep.setOpaque(false);
		panel.add(sep);

		/*
		 * Designing split panel to accommodate Model image and Free Body
		 * Diagram
		 */
		JPanel innerPanel1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) innerPanel1.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setAlignment(FlowLayout.CENTER);

		JPanel innerPanel2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) innerPanel2.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setAlignment(FlowLayout.CENTER);

		innerPanel1.setBackground(new Color(46, 42, 42));
		innerPanel2.setBackground(new Color(46, 42, 42));

		// Label for model image
		innerPanel1.add(modelImgObject.SetImage(), "22, 2, fill, default");

		// Label for FBD image
		innerPanel2.add(fbdImgObject.SetImage(), "22, 6, fill, default");

		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, innerPanel1, innerPanel2);
		splitpane.setAlignmentY(0.5f);
		splitpane.setResizeWeight(0.5);
		splitpane.setDividerSize(0);
		splitpane.setMaximumSize(new Dimension(2000, 450));
		splitpane.setBorder(null);
		panel.add(splitpane);

		/* Gap between splitpane, and assumptions and reasons */
		JSeparator sep1 = new JSeparator();
		sep1.setMaximumSize(new Dimension(0, 30));
		sep1.setOpaque(false);
		panel.add(sep1);

		/* Question String */
	    lblQuestion = new JLabel("Q: Which of the following assumptions are correct?");
	    lblQuestion.setForeground(Color.WHITE);
		lblQuestion.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblQuestion);

		/* Designing check boxes for assumptions */
		assumObject.ReadAssumptions(assumObject.path);
		GUISettingAssumptionsList();

		// Calling method to set Assumption list on GUI
		// GUISettingAssumptionsList(tempAssumptions);

		// Next Button
		// Retake button
		JButton nxtButton = new JButton("Next Question");
		nxtButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CreateObjects();
				InitializeContents();
				initialize();
			}
		});
		nxtButton.setVisible(false);

		// Submit button
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isSubmitted) {
					// Calling method to set Reasons list on GUI
					score = reasonObject.ScoreCalculation(score);
					System.out.println("Inside is Submitted true score:" + score);
					lblScore.setText("Score = " + score);
					submitButton.setVisible(false);
					nxtButton.setVisible(true);
				} else {
					score = assumObject.ScoreCalculation(score, reasonObject);
					if (assumObject.cumAnswerFlag) {
						lblScore.setText("Score = " + score);
						submitButton.setVisible(false);
					}
					assumObject.DisableCheckBox();
					isSubmitted = true;
				}
				nxtButton.setVisible(true);
			}
		});
		/* Gap between options and button */
		JSeparator sep2 = new JSeparator();
		sep2.setMaximumSize(new Dimension(0, 30));
		sep2.setOpaque(false);
		panel.add(sep2);
		panel.add(submitButton);
		panel.add(nxtButton);
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
		final String xAxisLocation = "22,";
		// Split all assumptions in answer and statements, create check boxes
		// Traverse through check boxes and add them on panel
		for (int i = 0; i < assumObject.assumptions.size(); i++) {
			panel.add(assumObject.assumptionChkbxList.get(i),
					xAxisLocation + ((i * 2) + 10) + ", fill, default");
			JLabel lblIncorrect = assumObject.TypeOfAssumption(reasonObject, assumObject.answers.get(i));

			// Incorrect assumption
			if (lblIncorrect != null) {
				lblIncorrect.setForeground(Color.WHITE);
				lblIncorrect.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
				panel.add(lblIncorrect);
				// Calling method to add reasons for incorrect assumption
				GUISettingReasonsList(i);
			}
		}
	}

	/*
	 * GUI SETTING REASONS LIST Method to display all reasons on the GUI based
	 * on the assumption selected
	 */

	public void GUISettingReasonsList(int assumptionIdx) {
		// Read reason file for incorrect assumption
		ArrayList<String> tempReasons = new ArrayList<String>();
		ArrayList<JRadioButton> rdbList = new ArrayList<JRadioButton>();
		System.out.println("Assumption index:" + assumptionIdx);
		tempReasons = reasonObject.GetReasonsForIncorrectAssumption(assumptionIdx, reasonObject.path);
		System.out.println("After reading file:");
		for (int j = 0; j < tempReasons.size(); j++) {
			System.out.println(tempReasons.get(j));
		}

		if (tempReasons != null) {
			// Create radio buttons for all reasons
			rdbList = reasonObject.CreateReasonList(tempReasons);
			for (int rdbIndex = 0; rdbIndex < rdbList.size(); rdbIndex++) {
				// Adding radio buttons onto panel
				panel.add(rdbList.get(rdbIndex));
			}
			reasonObject.listOfRdbtnListForReasons.add(rdbList);
		}
	}
}