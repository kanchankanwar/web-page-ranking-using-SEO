

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import webapp.*;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Connection con = DBConnection.getDBConnection();
       String uname = "";
       String email = "";
       String pwd = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
			System.out.println("Here it is ...");
			uname = request.getParameter("username");
			email = request.getParameter("email");
			pwd = request.getParameter("password");
			String gender = request.getParameter("gender");
			if(gender == "")
			{
				RequestDispatcher rd = request.getRequestDispatcher("/Register.jsp");
			}
			PreparedStatement ps = con.prepareStatement("insert into user(Username,Email,Password,Gender) values('" + uname + "', '" + email + "', '" + pwd + "', '" + gender + "')");
			int res = ps.executeUpdate();
			System.out.println("result "+res);
			response.sendRedirect("Login.jsp");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
