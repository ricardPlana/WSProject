package myRESTwsWeb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import myRESTwsExam.Exam;
import myRESTwsExam.ExamsPack;
import myRESTwsGrades.Grades;
import myRESTwsStudent.Student;


@Path("")
public class MyResource {
		
	@Path("/readExams")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ExamsPack readExams() throws NamingException, SQLException
	{
		ExamsPack examsPack = new ExamsPack();
			
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		
		if(ds != null) 
		{
			
			
			Connection connection = ds.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.exams");
		    ResultSet resultSet = preparedStatement.executeQuery();
		    while(resultSet.next())
		    {
		    	Exam exam = new Exam();
		    	exam.setidexam(resultSet.getString("idexam"));
		    	exam.setdescription(resultSet.getString("description"));
		    	exam.setexamdate(resultSet.getString("examdate"));
		    	exam.setexamtime(resultSet.getString("examtime"));
		    	exam.setlocation(resultSet.getString("location"));  
		    	examsPack.addExam(exam);
		    }
		    
		    connection.close();
		    preparedStatement.close();

			return examsPack;
		}
		return examsPack;
	}
	
	@Path("/readoneExam")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ExamsPack readOneExam(String[] examtoread) throws NamingException, SQLException
	{
		ExamsPack examsPack = new ExamsPack();
		
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		
		if(examtoread[0].equals("id"))
		{
			Connection connection = ds.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.exams WHERE idexam = "+examtoread[1]);
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
		    while(resultSet.next())
		    {
		    	Exam exam = new Exam();
		    	exam.setidexam(resultSet.getString("idexam"));
		    	exam.setdescription(resultSet.getString("description"));
		    	exam.setexamdate(resultSet.getString("examdate"));
		    	exam.setexamtime(resultSet.getString("examtime"));
		    	exam.setlocation(resultSet.getString("location")); 
		    	examsPack.addExam(exam);
		    }
		    connection.close();
		    preparedStatement.close();

		}
		
		if(examtoread[0].equals("descp"))
		{
			Connection connection = ds.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.exams WHERE description like '%"+examtoread[1]+"%'");
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
		    while(resultSet.next())
		    {
		    	Exam exam = new Exam();
		    	exam.setidexam(resultSet.getString("idexam"));
		    	exam.setdescription(resultSet.getString("description"));
		    	exam.setexamdate(resultSet.getString("examdate"));
		    	exam.setexamtime(resultSet.getString("examtime"));
		    	exam.setlocation(resultSet.getString("location")); 
		    	examsPack.addExam(exam);
		    }
		    connection.close();
		    preparedStatement.close();
		}
		
		if(examtoread[0].equals("descf"))
		{
			Connection connection = ds.getConnection();
		    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.exams WHERE description like '"+examtoread[1]+"'");
		    ResultSet resultSet = preparedStatement.executeQuery();
		    
		    while(resultSet.next())
		    {
		    	Exam exam = new Exam();
		    	exam.setidexam(resultSet.getString("idexam"));
		    	exam.setdescription(resultSet.getString("description"));
		    	exam.setexamdate(resultSet.getString("examdate"));
		    	exam.setexamtime(resultSet.getString("examtime"));
		    	exam.setlocation(resultSet.getString("location")); 
		    	examsPack.addExam(exam);
		    }
		    connection.close();
		    preparedStatement.close();
		}
			
		
		return examsPack;
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/deleteexam")
	public String deleteExam(String[] examtodelete) throws SQLException, NamingException
	{;
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		if(ds != null)  
		{

			Connection connection = ds.getConnection();
			Statement st = connection.createStatement();
			
			try {
						
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.examgrades WHERE idexam = "+examtodelete[0]);
			    ResultSet resultSet = preparedStatement.executeQuery();				    
			    
			    if(!resultSet.next())
			    {
			    	st.executeUpdate("delete from public.exams where idexam="+examtodelete[0]);
			    	return "Exam deleted or already deleted";
			    	
			    }
			    else 
			    {
			    	return "Exam have grades, can't be deleted";
			    }
			    											
				
			}catch(Exception e) {
				connection.close();
				st.close();
				return e.getMessage();
			}

		}
		else 
		{
			return "Error while adding the register";
		}
				
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/modifyexam")
	
	public String modifyexam(String[] examtomodify) throws SQLException, NamingException
	{;
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		if(ds != null)  
		{

			Connection connection = ds.getConnection();
			Statement st = connection.createStatement();
			
			try {
								
				st.executeUpdate("update public.exams set description='"+examtomodify[1]+"' where idexam="+examtomodify[0]);
				connection.close();
				st.close();
				
				return "Exam updated";				
				
			}catch(Exception e) {
				connection.close();
				st.close();
				return e.getMessage();
			}

		}
		else 
		{
			return "Error while updating the register";
		}		
		
	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insertexam")
	public String insertExam(String[] exam) throws SQLException, NamingException
	{
		Exam newExam = new Exam();
		newExam.setvalues(exam);
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		if(ds != null)  
		{

			Connection connection = ds.getConnection();
			Statement st = connection.createStatement();
			
			try {
				
				st.executeUpdate("insert into public.exams (idexam,description,examdate,examtime,location) values (DEFAULT, '"
								+newExam.getdescription()+"', '"
								+newExam.getexamdate()+"', '"
								+newExam.getexamtime()+"', '"
								+newExam.getlocation()+"')");
				
				connection.close();
				st.close();
				
				return "Exam added";
				
			}catch(Exception e) {
				connection.close();
				st.close();
				return e.getMessage();
			}

		}
		else 
		{
			return "Error while adding the register";
		}		
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/insertgrades")
	public String insertgrades(String[] exam) throws SQLException, NamingException
	{		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		
		if(ds != null)  
		{

			Connection connection = ds.getConnection();
			Statement st = connection.createStatement();
			
			try {
				
				int numinserts = (exam.length - 1)/2;
				int idstudentpos = 1;
				int gradepos = 2;
				
			    
				for(int i = 0; i < numinserts ; i++)
				{
					
					PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.examgrades WHERE idstudent = "+exam[idstudentpos]+" and idexam = "+exam[0]);
				    ResultSet resultSet = preparedStatement.executeQuery();				    
				    
				    if(!resultSet.next())
				    {
				    	st.executeUpdate("insert into public.examgrades (gradeid,idstudent,idexam,grade) values (DEFAULT,"
								+exam[idstudentpos]+","
								+exam[0]+", "
								+exam[gradepos]+")");			    	
				    }			
				    
				    preparedStatement.close();
					idstudentpos += 2;
					gradepos += 2;
				}
				
							
				
				connection.close();
				st.close();
				
				return "Grades added / or ignored if already in";
				
			}catch(Exception e) {
				
				connection.close();
				st.close();				
				return e.getMessage();
			}

		}
		else 
		{
			return "Error while adding the register";
		}
		
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/readGrades")
	public String readGrades(String[] exam) throws SQLException, NamingException
	{
		Exam newExam = new Exam();
		newExam.setvalues(exam);
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		if(ds != null)  
		{

			Connection connection = ds.getConnection();
			Statement st = connection.createStatement();
			
			try {
				
				st.executeUpdate("insert into public.exams (idexam,description,examdate,examtime,location) values (DEFAULT, '"
								+newExam.getdescription()+"', '"
								+newExam.getexamdate()+"', '"
								+newExam.getexamtime()+"', '"
								+newExam.getlocation()+"')");
				
				connection.close();
				st.close();
				
				return "Exam added";
				
			}catch(Exception e) {
				connection.close();
				st.close();
				return e.getMessage();
			}

		}
		else 
		{
			return "Error while adding the register";
		}		
		
	}
	
	@Path("/gradesone")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Grades gradesone(String[] student) throws SQLException, NamingException 
	{
		Grades studentGrades = new Grades();
		
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		
		
		Connection connection = ds.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.examgrades where idstudent ="+student[0]);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next())
	    {
	    	Student newStudent  = new Student();
	    	newStudent.setexamid(resultSet.getString("idexam"));
	    	newStudent.setgrade(resultSet.getString("grade"));
	    	newStudent.setstudentid(resultSet.getString("idstudent"));
	    	studentGrades.addStudent(newStudent);
	    	
	    }
	    connection.close();
	    preparedStatement.close();
		
		return studentGrades;
		
	}
	
	@Path("/gradesexam")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Grades gradesexam(String[] exam) throws SQLException, NamingException 
	{
		Grades studentGrades = new Grades();
		
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");
		
		
		Connection connection = ds.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.examgrades where idexam ="+exam[0]);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    while(resultSet.next())
	    {
	    	Student newStudent  = new Student();
	    	newStudent.setexamid(resultSet.getString("idexam"));
	    	newStudent.setgrade(resultSet.getString("grade"));
	    	newStudent.setstudentid(resultSet.getString("idstudent"));
	    	studentGrades.addStudent(newStudent);
	    	
	    }
	    connection.close();
	    preparedStatement.close();
		
		return studentGrades;
		
	}
	
	@Path("/checkuser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String checkuser(String[] exam) throws SQLException, NamingException 
	{
		
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/PostgresXADS");		
		
		Connection connection = ds.getConnection();
	    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM public.users where iduser ="+exam[0]);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    
	    if(resultSet.next())
	    {    
		    if(resultSet.getBoolean("isprofesor"))
		    {
		    	return "true";
		    }
		    else {
		    	return "false";
		    }
	    }	 
	    else
	    {
	    	return "User not found, contact ur administrator";	    	
	    }
	    		
	}
	
}