package myRESTwsExam;

public class Exam {

	private String idexam;
	private String description;
	private String examdate;
	private String examtime;
	private String location;    
	
	public String getidexam()
	{
		return this.idexam;
	}
	
	public String getdescription()
	{
		return this.description;
	}
	
	public String getexamdate()
	{
		return this.examdate;
	}
	
	public String getexamtime()
	{
		return this.examtime;
	}
	
	public String getlocation()
	{
		return this.location;
	}
	
	public void setidexam(String _idexam)
	{
		this.idexam = _idexam;
	}
	
	public void setdescription(String _description)
	{
		this.description = _description;
	}
	
	public void setexamdate(String _examdate)
	{
		this.examdate = _examdate;
	}
	
	public void setexamtime(String _examtime)
	{
		this.examtime = _examtime;
	}
	
	public void setlocation(String _location)
	{
		this.location = _location;
	}
	
	public void setvalues(String[] values)
	{
		this.description = values[0];
		this.examdate = values[1];
		this.examtime = values[2];
		this.location = values[3];
	}
    
}
