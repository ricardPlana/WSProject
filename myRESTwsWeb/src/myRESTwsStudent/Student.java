package myRESTwsStudent;

public class Student {
	
	private String studentid;
	private String grade;
	private String examid;
	
	
	public String getstudentid()
	{
		return this.studentid;
	}
	
	public String getgrade()
	{
		return this.grade;
	}
	
	public String getexamid()
	{
		return this.examid;
	}
	
	public void setexamid(String _examid)
	{
		this.examid = _examid;
	}
	
	public void setgrade(String _grade)
	{
		this.grade = _grade;
	}
	
	public void setstudentid(String _studentid)
	{
		this.studentid = _studentid;
	}

}
