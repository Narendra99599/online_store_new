package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dal.DAL;
import dal.ProductFactory;
import models.ProductsList;

/**
 * Servlet implementation class CheckServlet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pincode = request.getParameter("pincode");

		DAL d = ProductFactory.getProductsDALImpl();
		ProductsList lst = d.getProductsByPin(pincode);
		request.setAttribute("notPresent", lst);
		System.out.println("at request");
		System.out.println(request.getAttribute("notPresent"));
		RequestDispatcher rd = request.getRequestDispatcher("notpresent.jsp");
		rd.forward(request, response);
	}
}
