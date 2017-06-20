import java.io.File;
import java.util.Random;

public class EngineeringEducatorQuestions {
	/*
	 * FOLDER RANDOM SELECTION Method to randomly select a question folder
	 * Input: 1. EngineeringEducator class object 2. Path of the directory
	 * containing question folders
	 */
	public String FolderRandomSelection(String folderPath) {
		String questionPath = "";
		
		try{
			File dir = new File(folderPath);
			String[] quesfolders = dir.list();
			Random rand = new Random();
			String folder = quesfolders[rand.nextInt(quesfolders.length)];
			questionPath = folderPath + folder;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return questionPath;
	}
}
