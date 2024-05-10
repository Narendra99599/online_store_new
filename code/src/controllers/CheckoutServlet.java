package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dal.DAL;
import dal.ProductFactory;
import models.Product;
import models.ProductsList;

/**
 * Servlet implementation class CheckoutServlet
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession hs = req.getSession(false);
		HashMap<Integer,Integer> productCount=(HashMap<Integer,Integer>) hs.getAttribute("productCount");
		HashMap<Integer,Product> productSet=(HashMap<Integer,Product>) hs.getAttribute("productSet");
		String email = (String) hs.getAttribute("user");
		Integer oid = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
		// String date = new Date().toString();
		long currentTimeMillis = System.currentTimeMillis();

		// Create a java.sql.Date object representing the current date
		Date sqlDate = new Date(currentTimeMillis); // Converts to java.sql.Date
		DAL pd = ProductFactory.getProductsDALImpl();
		Integer total = 0;
		ArrayList<Integer> hsn = new ArrayList<>();
		Integer i = 0;
		for (Product pr : productSet.values()) {
			hsn.add(pr.getHsn());
			i++;
		}
		ArrayList<Integer> gst = pd.getgst(hsn);
		i = 0;
		for (Product pr : productSet.values()) {
			total += (pr.getPrice() + (int) ((double) pr.getPrice() * ((double) gst.get(i) / (double) 100)))*productCount.get(pr.getPid());
			i++;
		}
		ProductsList products = new ProductsList();
	    products.addAll(productSet.values());
		System.out.println("123");
		Integer cid = pd.getCid(email);
		String cname = pd.getCname(email);
		System.out.println("456");
		pd.setOrderDetails(oid, sqlDate, total, cid);
		pd.setProductDetails(oid, products);
		System.out.println("789");
		hs.setAttribute("cart_total", total);
		hs.setAttribute("order_id", oid);
		hs.setAttribute("customer_name", cname);
	}

}
