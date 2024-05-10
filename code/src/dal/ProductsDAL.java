package dal;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import jdbcUtilities.JdbcConnection;
import models.Customer;
import models.Product;
import models.ProductsList;

public class ProductsDAL implements DAL {

	public static Connection con = JdbcConnection.getConnection();

	public ResultSet rs;
	public PreparedStatement st;
	public String query;

	private boolean status;

	public ProductsList getProducts(String category, String sort, Integer pages) {
		// TODO Auto-generated method stub
		ProductsList pl = null;
		System.out.println(category + sort);
		System.out.println("db:" + pages);
		try {
			query = "SELECT * FROM get_products_pages_narendra(?,?,?);";
			st = con.prepareStatement(query);
			st.setString(1, category);
			st.setString(2, sort);
			st.setInt(3, pages);
			rs = st.executeQuery();
			pl = new ProductsList();
			while (rs.next()) {
				pl.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));

			}
			for (Product p : pl) {
				System.out.println(p);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return pl;
	}

	public ProductsList getProductsByPin(String pincode) {
		// TODO Auto-generated method stub
		ProductsList pl = null;
		System.out.println(pincode);
		try {
			query = "select os.* from os_products os, (select product_id from os_products_region where region_id = "
					+ pincode + ") as reg where os.pid = reg.product_id";
			st = con.prepareStatement(query);
			rs = st.executeQuery();
			pl = new ProductsList();
			while (rs.next()) {
				System.out.println(rs.getInt(1) + rs.getString(2));
				pl.add(new Product(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return pl;
	}

	public void setOrderDetails(Integer oid, Date sqlDate, Integer total, Integer cid) {
		// TODO Auto-generated method stub // TODO Auto-generated method stub
		try {
			query = "Insert into os_orders values(?,?,?,?)";
			st = con.prepareStatement(query);
			st.setInt(1, oid);
			st.setDate(2, sqlDate);
			st.setInt(3, total);
			st.setInt(4, cid);
			st.execute();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Integer getCid(String email) {
		Integer x = 0;
		// TODO Auto-generated method stub
		try {
			query = "Select cid from os_customer where cemail= ?";
			st = con.prepareStatement(query);
			st.setString(1, email);
			rs = st.executeQuery();
			rs.next();
			x = rs.getInt(1);
		} catch (Exception e) {
			System.out.println(e);
		}
		return x;
	}

	public String getCname(String email) {
		String x = "";
		// TODO Auto-generated method stub
		try {
			query = "Select cname from os_customer where cemail= ?";
			st = con.prepareStatement(query);
			st.setString(1, email);
			rs = st.executeQuery();
			rs.next();
			x = rs.getString(1);
		} catch (Exception e) {
			System.out.println(e);
		}
		return x;
	}

	public void setProductDetails(Integer oid, ProductsList p) {
		try {
			query = "insert into os_order_products values(?,?)";
			st = con.prepareStatement(query);
			for (Product pr : p) {
				st.setInt(1, oid);
				st.setInt(2, pr.getPid());
				st.addBatch();
			}
			st.executeBatch();
		} catch (Exception e) {
		}
	}

	public ArrayList<Integer> getgst(ArrayList<Integer> hsn) {
		// TODO Auto-generated method stub
		ArrayList<Integer> gstList = new ArrayList<>(); // Mutable list for results

		try {
			// Convert ArrayList to SQL Array (for PostgreSQL, for example)
			Array sqlArray = con.createArrayOf("INTEGER", hsn.toArray());

			// SQL query with array parameter
			String query = "SELECT getGST(?)"; // Example query
			st = con.prepareStatement(query);
			st.setArray(1, sqlArray); // Set the SQL array

			rs = st.executeQuery();

			// Loop through the ResultSet
			if (rs.next()) {
				// Extract the returned array
				Array gstArray = rs.getArray(1); // Get the array from the first column
				Integer[] gstValues = (Integer[]) gstArray.getArray(); // Convert to Integer array

				for (Integer gst : gstValues) {
					gstList.add(gst); // Add elements to the list
				}

				System.out.println("GST Values: " + gstList); // Display the GST values
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return gstList;
	}

	public boolean verifyUser(String cemail, String cpassword) {
		boolean ispwd = false;
		try {
			query = "Select cpassword from os_customer where cemail=?";
			st = con.prepareStatement(query);
			st.setString(1, cemail);
			rs = st.executeQuery();
			if (rs.next()) {
				if (cpassword.equals(rs.getString(1))) {
					ispwd = true;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ispwd;
	}

	public Boolean registerUser(Customer c) {
		query = ("insert into os_customer values(?,?,?,?,?,?);");
		try {

			st = con.prepareStatement(query);
			st.setInt(1, c.getCid());
			st.setString(2, c.getCname());
			st.setString(3, c.getCmobile());
			st.setString(4, c.getCemail());
			st.setString(5, c.getClocation());
			st.setString(6, c.getCpassword());
			status = st.execute();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}

}
