package models;

public class Product {
	int pid;
	String pname;
	int price;
	String image;
	int hsn;

	public Product(int pid, String pname, int price, String image, int hsn) {

		this.pid = pid;
		this.pname = pname;
		this.price = price;
		this.image = image;
		this.hsn = hsn;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", price=" + price + ", image=" + image + ", hsn=" + hsn
				+ "]";
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getHsn() {
		return hsn;
	}

	public void setHsn(int hsn) {
		this.hsn = hsn;
	}
}
