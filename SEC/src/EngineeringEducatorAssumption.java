import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class EngineeringEducatorAssumption {
	public ArrayList<String> allAssumptions = new ArrayList<String>();
	public ArrayList<String> assumptions = new ArrayList<String>();
	public ArrayList<Integer> answers = new ArrayList<Integer>();
	public ArrayList<JCheckBox> assumptionChkbxList = new ArrayList<JCheckBox>();
	String path;

	/*
	 * READ ASSUMPTIONS Method to read text file line by line and store the
	 * result in an array list Input: Path of the text file Output: Array list
	 * of String containing textual data of file
	 */
	public void ReadAssumptions() {
		FileInputStream fstream2;
		try {
			fstream2 = new FileInputStream(path);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
				allAssumptions.add(strLine2);
				System.out.println(strLine2);
			}
			br2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Splitter() {
		for (int j = 0; j < allAssumptions.size(); j++) {
			String[] splitter = allAssumptions.get(j).split("\\|");
			assumptions.add(splitter[0]);
			Integer assumptionAnswer = Integer.parseInt(splitter[1]);
			answers.add(assumptionAnswer);
			JCheckBox chkbxAssumption = new JCheckBox(assumptions.get(j));
			assumptionChkbxList.add(chkbxAssumption);

		}
	}

	public JLabel TypeOfAssumption(EngineeringEducatorReason reasonObject, int ansType) {
		JLabel lblIncorrect = null;
		if (ansType == 1) {
			reasonObject.listOfRdbtnListForReasons.add(null);
			reasonObject.answers.add(-1);
			reasonObject.reasonMsgLabelList.add(null);
		} else {
			// for incorrect assumptions add reasons below it, but hide them
						// add a label saying this assumption is incorrect/ complicated
			if (ansType == 0) {
				lblIncorrect = new JLabel("This assumption is incorrect, what could be the reason?");
				lblIncorrect.setVisible(false);
				reasonObject.reasonMsgLabelList.add(lblIncorrect);
			} else if (ansType == 2) {
				lblIncorrect = new JLabel(
						"This assumption is a complicating factor, what could be the reason?");
				lblIncorrect.setVisible(false);
				reasonObject.reasonMsgLabelList.add(lblIncorrect);
			}
		}
		return lblIncorrect;
	}

	/*
	 * DISABLE CHECKBOX Method to disable all the checkboxes Input: Arraylist
	 * containing check boxes
	 */
	public void DisableCheckBox() {
		for (int i = 0; i < assumptionChkbxList.size(); i++) {
			assumptionChkbxList.get(i).setEnabled(false);
		}
	}
}
