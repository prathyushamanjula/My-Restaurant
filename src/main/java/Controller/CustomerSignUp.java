package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import DAO.MyDao;
import DTO.Customer;

// This Is To Map The Action URL To This Class(Should be Same as action - Case sensitive)
@WebServlet("/signup")
// To Receive Image We Need To Use this-enctype

@MultipartConfig
// This Is To Make Class As Servlet Class

public class CustomerSignUp extends HttpServlet 
{
	@Override
	// When There Is Form And We Want Data To Be Secured So doPost
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// Logic To Receive Values From Front End
		String fullName = req.getParameter("fullname");
		String password = req.getParameter("password");
		long mobile = Long.parseLong(req.getParameter("mobile"));
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		String country = req.getParameter("country");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		int age = Period.between(dob, LocalDate.now()).getYears();

		// Logic To Receive Image And Convert To byte[]
		Part pic = req.getPart("picture");
		byte[] picture=null;
		picture = new byte[pic.getInputStream().available()];
		pic.getInputStream().read(picture);
		
		MyDao dao = new MyDao();
		if (dao.fetchByEmail(email) == null && dao.fetchByMobile(mobile) == null) {

			Customer customer = new Customer();
			customer.setAge(age);
			customer.setCountry(country);
			customer.setDob(dob);
			customer.setEmail(email);
			customer.setFullName(fullName);
			customer.setGender(gender);
			customer.setMobile(mobile);
			customer.setPassword(password);
			customer.setPicture(picture);

			dao.save(customer);

			resp.getWriter().print("<h1 style='color:green'>Account Created Successfully</h1>");
			req.getRequestDispatcher("Login.html").include(req, resp);
		} 
		else
        {
			resp.getWriter().print("<h1 style='color:green'>Mobile And Email Should Be Unique</h1>");
			req.getRequestDispatcher("SignUp.html").include(req, resp);
		}	
	}
}


