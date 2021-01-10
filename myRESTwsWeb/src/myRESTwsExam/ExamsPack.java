package myRESTwsExam;

import java.util.ArrayList;

public class ExamsPack {
	ArrayList<Exam> values = new ArrayList<Exam>();
	
	public void addExam(Exam _exam)
	{
		this.values.add(_exam);
				
	}
	
	public ArrayList<Exam> getExams()
	{
		return this.values;
	}
}
