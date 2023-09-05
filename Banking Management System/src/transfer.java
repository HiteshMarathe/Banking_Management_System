

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class transfer
 */
public class transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transfer() {
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
			Connection con=DBConnection.connect();
			int AccNo1=properties.getId();
			int AccNo2=Integer.parseInt(request.getParameter("reciever"));
			int withdraw=Integer.parseInt(request.getParameter("withdraw"));
			int deposite=withdraw;
			if(withdraw<0)
			{
				response.sendRedirect("invalidamount.html");
			}
			PreparedStatement pstmt;
			pstmt = con.prepareStatement("select balance from user where accountnumber=?");
			pstmt.setInt(1, AccNo1);
			ResultSet rs1=pstmt.executeQuery();
			
			PreparedStatement pstmt3;
			pstmt3 = con.prepareStatement("select balance from user where accountnumber=?");
			pstmt3.setInt(1, AccNo2);
			ResultSet rs2=pstmt3.executeQuery();
			if(rs1.next() && rs2.next())
			{
				int oldBalance1=rs1.getInt(1);
				if(oldBalance1>withdraw)
				{
				int newBalance1=oldBalance1-withdraw;
				PreparedStatement pstmt2;
				pstmt2=con.prepareStatement("update user set balance=? where accountnumber=?");
				pstmt2.setInt(1, newBalance1);
				pstmt2.setInt(2, AccNo1);
				pstmt2.executeUpdate();
				
				
				
		
					int oldBalance2=rs2.getInt(1);
					int newBalance2=deposite+oldBalance2;
					PreparedStatement pstmt4;
					pstmt4=con.prepareStatement("update user set balance=? where accountnumber=?");
					pstmt4.setInt(1, newBalance2);
					pstmt4.setInt(2, AccNo2);
					pstmt4.executeUpdate();
					response.sendRedirect("transfersuc.html");
				
				}
				else{
					response.sendRedirect("transferfail.html");
				}
			}
			else{
				response.sendRedirect("index.html");
//				System.exit(0);
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
