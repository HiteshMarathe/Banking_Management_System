

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class login
 */
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
			String email=request.getParameter("username");
			String pass=request.getParameter("password");
			Connection con=DBConnection.connect();
			PreparedStatement pstmt;
			pstmt = con.prepareStatement("select * from user where email=? and pass=?");
			pstmt.setString(1,email);
			pstmt.setString(2, pass);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				int id=rs.getInt(1);
				String uemail=rs.getString(2);
				properties.setEmail(uemail);
				properties.setId(id);
				
					response.sendRedirect("loginsuc.html");
			}
				else{
					response.sendRedirect("loginfail.html");
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
