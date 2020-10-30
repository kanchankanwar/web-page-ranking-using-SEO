

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.DBConnection;
import webapp.UserCredentials;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con = DBConnection.getDBConnection();
	String uname = "";
	String pwd = "";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		try {
			uname = request.getParameter("username");
			pwd = request.getParameter("password");
			
			PreparedStatement ps = con.prepareStatement("select UserId,Email from user where Username = '" + uname + "' and Password = '" + pwd + "'");
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				UserCredentials.setUsername(uname);
				UserCredentials.setPassword(pwd);
				UserCredentials.setEmailId(""+rs.getString("Email"));
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		        rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
		        rd.forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
