package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import models.Product;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson;

	public CartServlet() {
		gson = new Gson();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		BufferedReader reader = req.getReader();
		StringBuilder jsonBody = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			jsonBody.append(line);
		}
		Product product = gson.fromJson(jsonBody.toString(), Product.class);
		int pid = product.getPid();
		
		HashMap<Integer,Product> productSet;
		HttpSession session = req.getSession(false);
		productSet = (HashMap<Integer,Product>) session.getAttribute("productSet");
		HashMap<Integer, Integer> productCount=( HashMap<Integer, Integer>) session.getAttribute("productCount");;
		productSet.put(product.getPid(),product);
		System.out.println(productSet.size());
		
		productCount.put(pid, 1);
		session.setAttribute("productSet", productSet);
		session.setAttribute("productCount", productCount);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer pid = Integer.parseInt(req.getParameter("pid"));
		String action = req.getParameter("action");
		HttpSession session = req.getSession(false);
		HashMap<Integer, Integer> productCount = (HashMap<Integer, Integer>) session.getAttribute("productCount");
		Integer curcount = productCount.get(pid);
		if (action.equals("add")) {
	        System.out.println ("add is called");

			productCount.put(pid, curcount + 1);
		} else if (action.equals("sub")) {
			productCount.put(pid, curcount - 1);
	        System.out.println ("sub is called");

		}
		session.setAttribute("productCount", productCount);
	}
}
