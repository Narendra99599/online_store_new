package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@WebServlet("/OrderCreation")
public class OrderCreation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in order requeeest");
		int amount = Integer.parseInt(request.getParameter("amount"));
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_oylZTllNukvCHh", "h5C4sfnvqOrfmfqkYetKIwtS");
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount * 100);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "receipt#1");
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1", "Tea, Earl Grey, Hot");
			orderRequest.put("notes", notes);
			Order order = razorpay.Orders.create(orderRequest);
			response.getWriter().println(order.toJson());
		} catch (RazorpayException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_oylZTllNukvCHh", "h5C4sfnvqOrfmfqkYetKIwtS");
			String secret = "EnLs21M47BllR3X8PSFtjtbd";

			String razorpayOrderId = request.getParameter("razorpay_order_id");
			String razorpayPaymentId = request.getParameter("razorpay_payment_id");
			String razorpaySignature = request.getParameter("razorpay_signature");

			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", razorpayOrderId);
			options.put("razorpay_payment_id", razorpayPaymentId);
			options.put("razorpay_signature", razorpaySignature);

			boolean status = Utils.verifyPaymentSignature(options, secret);

			if (status) {
				response.getWriter().println("Payment successful");
			} else {
				response.getWriter().println("Payment failed or signature verification failed");
			}
		} catch (RazorpayException e) {
			e.printStackTrace();
		}

	}

}
