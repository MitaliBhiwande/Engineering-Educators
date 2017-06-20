import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class EngineeringEducatorReason {
	public ArrayList<String> allReasons = new ArrayList<String>();
	public ArrayList<String> reasons = new ArrayList<String>();
	public ArrayList<Integer> answers = new ArrayList<Integer>();
	public ArrayList<ArrayList<JRadioButton>> listOfRdbtnListForReasons = new ArrayList<ArrayList<JRadioButton>>();
	public ArrayList<JLabel> reasonMsgLabelList = new ArrayList<JLabel>();
	ArrayList<JRadioButton> reasonList = new ArrayList<JRadioButton>();
	public String path;

	/*
	 * READ REASONS Method to read text file line by line and store the result
	 * in an array list Input: Path of the text file Output: Array list of
	 * String containing textual data of file
	 */
	public ArrayList<String> ReadReasons(String path) {
		FileInputStream fstream2;
		try {
			fstream2 = new FileInputStream(path);
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
			String strLine2;
			// Read File Line By Line
			while ((strLine2 = br2.readLine()) != null) {
				allReasons.add(strLine2);
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
		return allReasons;
	}

	public JRadioButton Splitter(int reasonIndex, ArrayList<String> tempReasons) {
		String[] reasonSplit = tempReasons.get(reasonIndex).split("\\|");
		reasons.add(reasonSplit[0]);
		Integer reasonAnswer = Integer.parseInt(reasonSplit[1]);
		if (reasonAnswer == 1) {
			answers.add(reasonIndex);
		}
		JRadioButton rdbReason = new JRadioButton(reasons.get(reasonIndex));
		reasonList.add(rdbReason);
		rdbReason.setVisible(false);
		return rdbReason;
	}

	public ArrayList<String> GetReasonsForIncorrectAssumptions(int assumptionIdx) {
		allReasons = ReadReasons(path + "_" + new Integer(assumptionIdx + 1).toString() + ".txt");
		return allReasons;
	}

	/*
	 * DISABLE RADIOBUTTONS Method to disable all the radio buttons Input:
	 * Arraylist containing radio buttons
	 */
	public void DisableRadioButton() {
		for (int i = 0; i < listOfRdbtnListForReasons.get(1).size(); i++) {
			if (listOfRdbtnListForReasons.get(1).get(i).isEnabled())
				listOfRdbtnListForReasons.get(1).get(i).setEnabled(false);
		}
	}
}
