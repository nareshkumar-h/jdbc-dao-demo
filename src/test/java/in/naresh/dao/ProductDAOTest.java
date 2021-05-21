package in.naresh.dao;

import java.util.ArrayList;
import java.util.List;

import in.naresh.model.Product;

public class ProductDAOTest {

	public static void main(String[] args) throws Exception {

		// I want to add product name in database

		List<Product> products = new ArrayList<Product>();

		Product product1 = new Product("Beans", 20);
		Product product2 = new Product("Carrot", 10);

		products.add(product1);
		//products.add(product2);

		// Add single product
		// ProductDAO.save(product1);

		// Add all products to db
		ProductDAO.save(products);

	}

}
