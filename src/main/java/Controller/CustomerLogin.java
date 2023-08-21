package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MyDao;
import DTO.Customer;

@WebServlet("/login")
public class CustomerLogin extends HttpServlet 
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// Receives Values From Frontend
		String email = req.getParameter("email");
		String password = req.getParameter("pass");

		// Verify If Email Exists
		MyDao dao = new MyDao();
		if (email.equals("admin@jsp.com") && password.equals("admin")) {
			resp.getWriter().print("<h1 style='color: green'>Login Success</h1>");
			// This Is Logic To Send To Next Page
			req.getRequestDispatcher("AdminHome.html").include(req, resp);
		} 
		else 
		{
			Customer customer = dao.fetchByEmail(email);
			if (customer == null) {
				resp.getWriter().print("<h1 style='color: red'>Invalid Email<h1>");
				req.getRequestDispatcher("Login.html").include(req, resp);
			} 
			else 
			{
				if (password.equals(customer.getPassword()))
				{
					resp.getWriter().print("<h1 style='color: green'>Login Success</h1>");
					// This Is Logic To Send To Next Page
					req.getRequestDispatcher("CustomerHome.html").include(req, resp);
				} 
				else 
				{
					// If Response Should Be Both Text and Html
					// resp.setContentType("text/html");

					resp.getWriter().print("<h1 style='color: red'>Invalid Password</h1>");
					req.getRequestDispatcher("Login.html").include(req, resp);
				}
			}
		}
	}
}