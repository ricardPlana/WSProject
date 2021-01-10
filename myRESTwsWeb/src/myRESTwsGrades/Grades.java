package myRESTwsGrades;

import java.util.ArrayList;

import myRESTwsExam.Exam;
import myRESTwsStudent.Student;

public class Grades {
	
	private ArrayList<Student> listGrades = new ArrayList<Student>();
	
	public void addStudent(Student _student)
	{
		this.listGrades.add(_student);
		
	}
	
	public ArrayList<Student> getStudents()
	{
		return this.listGrades;
	}
	
	

}
