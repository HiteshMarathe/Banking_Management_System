

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deposite
 */
public class deposite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deposite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String depo=request.getParameter("deposite");
		int deposite=Integer.parseInt(depo);
		try {
			Connection con=DBConnection.connect();
			int AccNo=properties.getId();
			
			PreparedStatement pstmt;
			pstmt = con.prepareStatement("select name,balance from user where accountnumber=?");
			pstmt.setInt(1, AccNo);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next())
			{
				if(deposite<=0)
				{
					response.sendRedirect("invalidamount.html");
				}
				else{
				int oldBalance=rs.getInt(2);
				int newBalance=deposite+oldBalance;
				PreparedStatement pstmt2;
				pstmt2=con.prepareStatement("update user set balance=? where accountnumber=?");
				pstmt2.setInt(1, newBalance);
				pstmt2.setInt(2, AccNo);
				pstmt2.executeUpdate();
				response.sendRedirect("depositsuc.html");
				}
			}
			else{
				response.sendRedirect("depositfail.html");

			}
			
		} catch (Exception e) {
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
