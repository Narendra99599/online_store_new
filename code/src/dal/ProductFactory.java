package dal;

public class ProductFactory {
	private static DAL d = null;

	private ProductFactory() {

	}

	public static DAL getProductsDALImpl() {
		if (d == null) {
			d = new ProductsDAL();
		}
		return d;
	}
}
