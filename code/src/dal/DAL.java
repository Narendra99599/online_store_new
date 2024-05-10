package dal;

import java.sql.Date;
import java.util.ArrayList;

import models.Customer;
import models.ProductsList;

public interface DAL {
	public ProductsList getProducts(String category, String sort, Integer pages);

	public void setOrderDetails(Integer oid, Date sqlDate, Integer total, Integer cid);

	public Integer getCid(String email);

	public String getCname(String email);

	public void setProductDetails(Integer oid, ProductsList arrayList);

	public ArrayList<Integer> getgst(ArrayList<Integer> hsn);

	public boolean verifyUser(String cemail, String cpassword);

	public Boolean registerUser(Customer c);

	public ProductsList getProductsByPin(String pincode);
}
